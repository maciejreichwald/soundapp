package com.rudearts.soundapp.api

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.soundapp.R
import com.rudearts.soundapp.SongApplication
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.model.external.TrackAsset
import com.rudearts.soundapp.model.external.TrackRest
import com.rudearts.soundapp.model.local.Track
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required

class ExternalMapper(base:Context) : ContextWrapper(base), Injects<BasicModule> {

    internal val dateUtil by required { dateUtil }

    init {
        inject(SongApplication.module(this))
    }

    fun track2local(track:TrackRest) = with(track) {
        Track(text2unknown(trackName), text2unknown(artistName), dateUtil.string2date(releaseDate), artworkUrl60, artworkUrl100, trackViewUrl, previewUrl)
    }

    fun track2local(track:TrackAsset) = with(track) {
        Track(text2unknown(name), text2unknown(artist), dateUtil.year2date(releaseDate?.value))
    }

    internal fun text2unknown(text:String?) = text ?: getString(R.string.unknown)

}