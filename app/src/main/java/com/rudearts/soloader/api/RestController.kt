package com.rudearts.soloader.api

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestController private constructor() {

    private object Holder { val INSTANCE = RestController() }

    companion object {
        val END_POINT = "https://api.stackexchange.com/docs/"
        const val QUERY_ITEMS_COUNT = 50
        val instance: RestController by lazy { Holder.INSTANCE }
    }

    lateinit var restApi: RestAPI
        private set

    fun init() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

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
