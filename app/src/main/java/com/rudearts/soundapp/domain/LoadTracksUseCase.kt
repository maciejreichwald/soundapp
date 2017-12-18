package com.rudearts.soundapp.domain

import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class LoadTracksUseCase @Inject constructor(
        internal val loadRest:TrackLoadable.Rest,
        internal val loadAsset:TrackLoadable.Asset) :TrackLoadable.UseCase {

    companion object {
        const val QUERY_ITEMS_COUNT = 200
        val DEFAULT_SEARCH_SIGN = "*"
    }

    override fun loadTracks(filter: TrackFilter): Single<List<Track>> {
        return Single.zip(
                loadRest.loadTracks(filter),
                loadAsset.loadTracks(filter),
                BiFunction { restTracks, assetTracks -> onTracksLoaded(restTracks, assetTracks)  })
    }

    internal fun onTracksLoaded(restTracks: List<Track>, assetTracks:List<Track>):List<Track> {
        val tracks = restTracks + assetTracks
        return sortTracks(tracks)
    }

    internal fun sortTracks(tracks: List<Track>) =
            tracks.sortedWith(
                    compareBy<Track> {it.name}
                            .thenByDescending {it.releaseDate.time}
                            .thenBy {it.artist})
}