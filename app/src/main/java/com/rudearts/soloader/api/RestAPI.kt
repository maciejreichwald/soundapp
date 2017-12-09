package com.rudearts.soloader.api

import com.rudearts.soloader.model.external.response.SearchResponse
import com.rudearts.soloader.util.loader.TrackLoader
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface RestAPI {

    @GET("/search?limit=${TrackLoader.QUERY_ITEMS_COUNT}&entity=song&attributeType=musicTrack")
    fun searchForSong(@Query("term") term: String): Single<Response<SearchResponse>>

    @GET("/search?limit=${TrackLoader.QUERY_ITEMS_COUNT}&entity=song&attributeType=musicArtist")
    fun searchForArtist(@Query("term") term: String): Single<Response<SearchResponse>>

}
