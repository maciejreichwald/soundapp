package com.rudearts.soundapp.domain

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.rudearts.soundapp.model.external.TrackAsset
import com.rudearts.soundapp.model.external.TrackNumber
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.util.loader.*
import io.reactivex.Single
import io.reactivex.SingleEmitter
import javax.inject.Inject

class LoadTracksFromAssets @Inject constructor(
        internal val assetLoader: AssetLoader,
        internal val assetCache: AssetCache,
        internal val mapper: ExternalMapper) : TrackLoadable.Asset {

    companion object {
        private val ASSET_PATH = "songs.json"
    }

    override fun loadTracks(filter: TrackFilter): Single<List<Track>> = Single.create { subcriber ->
        when(filter.source) {
            SourceType.ASSET,
            SourceType.BOTH -> loadFromAssets(filter, subcriber)
            SourceType.REST -> subcriber.onSuccess(emptyList())
        }
    }

    internal fun loadFromAssets(filter: TrackFilter, subcriber: SingleEmitter<List<Track>>) {
        when (assetCache.hasLoadedTracks())  {
            true -> handleAssets(assetCache.loadedTracks!!, filter, subcriber)
            false -> assetLoader.loadAsset(ASSET_PATH)
                    .subscribe({ asset -> parseAsset(asset, filter, subcriber) },
                            { error -> subcriber.onError(error) })
        }
    }

    internal fun parseAsset(assetJson: String, filter: TrackFilter, subcriber: SingleEmitter<List<Track>>) {
        if (TextUtils.isEmpty(assetJson)) {
            subcriber.onSuccess(emptyList())
            return
        }

        val gson = GsonBuilder()
                .registerTypeAdapter(TrackNumber::class.java, TrackNumberTypeAdapter())
                .create()
        val assetTracks = gson.fromJson(assetJson, TrackAssetList::class.java)
        val tracks = assetTracks.map { mapper.track2local(it) }
        assetCache.loadedTracks = tracks

        handleAssets(tracks, filter, subcriber)
    }

    internal fun handleAssets(tracks: List<Track>, filter: TrackFilter, subcriber: SingleEmitter<List<Track>>) {
        val filteredTracks = filterAssets(tracks, filter)
        subcriber.onSuccess(filteredTracks)
    }

    internal fun filterAssets(tracks: List<Track>, filter: TrackFilter) = tracks.filter { filterAsset(it, filter) }

    internal fun filterAsset(track: Track, filter: TrackFilter) = track.name.contains(filter.query)

    internal class TrackAssetList : ArrayList<TrackAsset>()
}
