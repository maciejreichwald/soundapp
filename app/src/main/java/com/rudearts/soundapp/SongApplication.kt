package com.rudearts.soundapp

import android.app.Application
import com.rudearts.soundapp.di.app.AppComponent
import com.rudearts.soundapp.di.app.DaggerAppComponent
import com.rudearts.soundapp.di.app.DomainModule
import com.rudearts.soundapp.di.app.ExternalModule

class SongApplication : Application() {

    companion object{
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        createComponent()
    }

    internal fun createComponent() {
        appComponent =  DaggerAppComponent.builder()
                .domainModule(DomainModule())
                .externalModule(ExternalModule(this))
                .build()
    }
}