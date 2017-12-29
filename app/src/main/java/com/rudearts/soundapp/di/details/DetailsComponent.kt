package com.rudearts.soundapp.di.details

import com.rudearts.soundapp.di.ActivityScope
import com.rudearts.soundapp.di.app.AppComponent
import com.rudearts.soundapp.ui.details.DetailsActivity
import com.rudearts.soundapp.ui.main.MainActivity
import com.rudearts.soundapp.ui.main.adapters.TrackAdapter
import dagger.Component

@ActivityScope
@Component(modules = arrayOf(DetailsModule::class))
interface DetailsComponent {
    fun inject(activity: DetailsActivity)
}