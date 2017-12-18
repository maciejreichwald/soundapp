package com.rudearts.soundapp.domain

import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track
import io.reactivex.Single

interface TrackLoadable {

    fun loadTracks(filter:TrackFilter):Single<List<Track>>

    /**
     * These are just to distinguish each case for DI purposes
     */
    interface UseCase : TrackLoadable
    interface Asset : TrackLoadable
    interface Rest : TrackLoadable
}