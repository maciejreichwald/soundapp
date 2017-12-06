package com.rudearts.soloader

import android.app.Application
import com.rudearts.soloader.api.RestController

class SOLoaderApplication : Application() {

    private val restController = RestController.instance

    override fun onCreate() {
        super.onCreate()

        restController.init()
    }
}