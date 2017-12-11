package com.rudearts.soloader.util.loader

import android.content.Context
import android.content.ContextWrapper
import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.rudearts.soloader.api.ExternalMapper
import com.rudearts.soloader.api.RestController
import com.rudearts.soloader.controller.AssetController
import com.rudearts.soloader.model.external.TrackAsset
import com.rudearts.soloader.model.external.TrackNumber
import com.rudearts.soloader.model.external.response.SearchResponse
import com.rudearts.soloader.model.filter.SearchType
import com.rudearts.soloader.model.filter.SortType
import com.rudearts.soloader.model.filter.SourceType
import com.rudearts.soloader.model.filter.TrackFilter
import com.rudearts.soloader.model.local.Track
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import retrofit2.Response

class TrackLoader(base:Context) : ContextWrapper(base) {

    companion object {
        const val QUERY_ITEMS_COUNT = 200
        private val DEFAULT_SEARCH_SIGN = "*"
        private val ASSET_PATH = "songs.json"
    }

    private val assetLoader = AssetLoader(this)
    private val assetController = AssetController.instance
    private val restController = RestController.instance
    private val mapper = ExternalMapper(this)

    fun loadTracks(filter:TrackFilter):Single<List<Track>> = Single.zip(
            createSearchRequest(filter),
            createAssetRequest(filter.source),
            BiFunction { restResponse, assetJson -> onTracksResponse(restResponse, assetJson, filter)})

    private fun onTracksResponse(restResponse: Response<SearchResponse>, assetJson: String, filter:TrackFilter):List<Track> {
        val tracks = handleRest(restResponse) + filterAssets(handleAsset(assetJson), filter)
        return sortTracks(tracks, filter)
    }

    private fun sortTracks(tracks: List<Track>, filter:TrackFilter) = when(filter.sort) {
        SortType.SONG -> tracks.sortedWith(
                compareBy<Track> {it.name}.thenByDescending {it.releaseDate.time}.thenBy {it.artist})
        SortType.ARTIST -> tracks.sortedWith(
                compareBy<Track>{it.artist}.thenByDescending {it.releaseDate.time}.thenBy {it.name})
        SortType.RELEASE_DATE -> tracks.sortedWith(
                compareByDescending<Track>{it.releaseDate.time}.thenBy {it.name}.thenBy {it.artist})
    }

    private fun filterAssets(tracks: List<Track>, filter: TrackFilter) = tracks.filter { filterAsset(it, filter) }

    private fun filterAsset(track: Track, filter: TrackFilter) = when(filter.type) {
        SearchType.SONG -> track.name.contains(filter.query)
        SearchType.ARTIST -> track.artist.contains(filter.query)
    }

    private fun handleAsset(assetJson: String): List<Track> {
        if (TextUtils.isEmpty(assetJson)) return ArrayList()

        if (assetController.areTracksLoaded(assetJson)) assetController.loadedTracks?.let {
            return it.toMutableList()
        }

        val gson = GsonBuilder()
                .registerTypeAdapter(TrackNumber::class.java, TrackNumberTypeAdapter())
                .create()
        val assetTracks = gson.fromJson(assetJson, TrackAssetList::class.java)
        val tracks = assetTracks.map { mapper.track2local(it) }
        assetController.loadedTracks = tracks

        return tracks
    }

    private fun handleRest(response: Response<SearchResponse>): List<Track> {
        if (!response.isSuccessful) {
            return ArrayList()
        }

        val itemsExternal = response.body()?.results ?: ArrayList()
        return itemsExternal.map { mapper.track2local(it) }
    }

    private fun createSearchRequest(filter: TrackFilter) : Single<Response<SearchResponse>> {
        val query = emptyQuery2Default(filter.query)
        return createSearchRequestBySource(query, filter)
    }

    private fun createAssetRequest(source: SourceType) = when(source) {
        SourceType.ASSET, SourceType.BOTH -> createAssetRequestOrLoadBackup()
        SourceType.REST -> Single.create { it.onSuccess("") }
    }

    private fun createAssetRequestOrLoadBackup() = when(assetController.loadedTracks) {
        null -> assetLoader.loadAsset(ASSET_PATH)
        else -> Single.create { it.onSuccess(assetController.getTracksLoadedMarker()) }
    }

    private fun createSearchRequestBySource(query:String, filter:TrackFilter) = when(filter.source) {
        SourceType.REST, SourceType.BOTH -> createSearchRequestRestOnly(query, filter.type)
        SourceType.ASSET -> Single.create { it.onSuccess(Response.success(null))}
    }

    private fun createSearchRequestRestOnly(query:String, type:SearchType) = when(type) {
        SearchType.SONG -> restController.restApi.searchForSong(query)
        SearchType.ARTIST -> restController.restApi.searchForArtist(query)
    }

    private fun emptyQuery2Default(query: String) = when(TextUtils.isEmpty(query)) {
        true -> DEFAULT_SEARCH_SIGN
        false -> query
    }

    private class TrackAssetList : ArrayList<TrackAsset>()
}