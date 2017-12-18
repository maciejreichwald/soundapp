package com.rudearts.soundapp.util.loader

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.soundapp.R
import com.rudearts.soundapp.model.external.TrackAsset
import com.rudearts.soundapp.model.external.TrackRest
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.util.DateUtil
import javax.inject.Inject

class ExternalMapper @Inject constructor(base:Context, internal val dateUtil: DateUtil) : ContextWrapper(base) {

    fun track2local(track:TrackRest) = with(track) {
        Track(text2unknown(trackName), text2unknown(artistName), dateUtil.string2date(releaseDate), artworkUrl60, artworkUrl100, trackViewUrl, previewUrl)
    }

    fun track2local(track:TrackAsset) = with(track) {
        Track(text2unknown(name), text2unknown(artist), dateUtil.year2date(releaseDate?.value))
    }

    internal fun text2unknown(text:String?) = text ?: getString(R.string.unknown)

}