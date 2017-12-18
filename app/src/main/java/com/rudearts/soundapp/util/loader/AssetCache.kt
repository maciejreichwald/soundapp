package com.rudearts.soundapp.util.loader

import com.rudearts.soundapp.model.local.Track

class AssetCache {

    var loadedTracks:List<Track>? = null

    fun hasLoadedTracks() = loadedTracks !== null
}