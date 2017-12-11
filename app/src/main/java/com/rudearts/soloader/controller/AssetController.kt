package com.rudearts.soloader.controller

import com.rudearts.soloader.api.RestAPI
import com.rudearts.soloader.model.external.TrackAsset
import com.rudearts.soloader.model.local.Track
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

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