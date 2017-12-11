package com.rudearts.soundapp.controller

import com.rudearts.soundapp.model.local.Track

class AssetController private constructor() {

    private object Holder { val INSTANCE = AssetController() }

    companion object {
        val instance: AssetController by lazy { Holder.INSTANCE }
        private val TRACKS_ARE_LOADED = "TrackAreLoaded"
    }

    var loadedTracks:List<Track>? = null

    fun getTracksLoadedMarker() = TRACKS_ARE_LOADED

    fun areTracksLoaded(marker:String) = marker == TRACKS_ARE_LOADED
}