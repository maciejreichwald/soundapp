package com.rudearts.soundapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestController private constructor() {

    private object Holder { val INSTANCE = RestController() }

    companion object {
        val END_POINT = "https://itunes.apple.com"
        val instance: RestController by lazy { Holder.INSTANCE }
    }

    lateinit var restApi: RestAPI
        private set

    fun setup() {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(END_POINT)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        restApi = retrofit.create(RestAPI::class.java)
    }
}
