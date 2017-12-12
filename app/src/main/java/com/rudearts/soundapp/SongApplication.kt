package com.rudearts.soundapp

import android.app.Application
import android.content.Context
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.di.MainModule
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required

open class SongApplication : Application(), Injects<BasicModule> {

    companion object {
        fun module(context:Context) = (context.applicationContext as SongApplication).module
    }

    private val module = createModule()

    internal val restController by required { restController }

    override fun onCreate() {
        super.onCreate()
        injection()
        setup()
    }

    open protected fun createModule():BasicModule = MainModule(this)

    private fun injection() {
        inject(module)
    }

    private fun setup() {
        restController.setup()
    }
}