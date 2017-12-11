package com.rudearts.soundapp

import android.app.Application
import com.rudearts.soundapp.api.RestController

class SongApplication : Application() {

    private val restController = RestController.instance

    override fun onCreate() {
        super.onCreate()

        restController.setup()
    }
}