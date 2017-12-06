package com.rudearts.soloader.api

import com.rudearts.soloader.model.external.response.SearchResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface RestAPI {

    @GET("/search?pagesize=${RestController.QUERY_ITEMS_COUNT}&order=desc&sort=activity&site=stackoverflow")
    fun search(@Query("intitle") intitle:String): Single<Response<SearchResponse>>

    @GET("/questions?pagesize=${RestController.QUERY_ITEMS_COUNT}&order=desc&sort=activity&site=stackoverflow")
    fun questions(): Single<Response<SearchResponse>>

}
