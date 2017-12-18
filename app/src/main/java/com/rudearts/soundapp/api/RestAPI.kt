package com.rudearts.soundapp.api

import com.rudearts.soundapp.domain.LoadTracksUseCase
import com.rudearts.soundapp.model.external.response.SearchResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface RestAPI {

    @GET("/search?limit=${LoadTracksUseCase.QUERY_ITEMS_COUNT}&entity=song&attributeType=musicTrack")
    fun search(@Query("term") term: String): Single<Response<SearchResponse>>

}
