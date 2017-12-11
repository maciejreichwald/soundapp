package com.rudearts.soundapp

import android.app.Application
import android.content.Context
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.di.MainModule
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required

class SongApplication : Application(), Injects<BasicModule> {

    private val module = MainModule(this)

    companion object {
        fun module(context:Context) = (context.applicationContext as SongApplication).module
    }

    private val restController by required { restController }

    override fun onCreate() {
        super.onCreate()
        injection()
        setup()
    }

    private fun injection() {
        inject(module)
    }

    private fun setup() {
        restController.setup()
    }
}