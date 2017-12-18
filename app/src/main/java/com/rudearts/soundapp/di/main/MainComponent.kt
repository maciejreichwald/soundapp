package com.rudearts.soundapp.di.main

import com.rudearts.soundapp.di.ActivityScope
import com.rudearts.soundapp.di.app.AppComponent
import com.rudearts.soundapp.ui.main.MainActivity
import com.rudearts.soundapp.ui.main.adapters.TrackAdapter
import dagger.Component

@ActivityScope
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(MainModule::class))
interface MainComponent {
    fun inject(activity: MainActivity)
    fun inject(adapter: TrackAdapter)
}