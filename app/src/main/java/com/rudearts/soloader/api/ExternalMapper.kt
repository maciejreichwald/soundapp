package com.rudearts.soloader.api

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.soloader.R
import com.rudearts.soloader.model.external.TrackAsset
import com.rudearts.soloader.model.external.TrackRest
import com.rudearts.soloader.model.local.Track
import com.rudearts.soloader.util.DateUtil

class ExternalMapper(base:Context) : ContextWrapper(base) {

    private val dateUtil = DateUtil.instance

    fun track2local(track:TrackRest) = with(track) {
        Track(text2unknown(trackName), text2unknown(artistName), dateUtil.string2date(releaseDate), artworkUrl60, artworkUrl100, trackViewUrl, previewUrl)
    }

    fun track2local(track:TrackAsset) = with(track) {
        Track(text2unknown(name), text2unknown(artist), dateUtil.year2date(releaseDate?.value))
    }

    private fun text2unknown(text:String?) = text ?: getString(R.string.unknown)

}