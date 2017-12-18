package com.rudearts.soundapp.di.app

import com.rudearts.soundapp.domain.TrackLoadable
import com.rudearts.soundapp.util.DateUtil
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DomainModule::class, ExternalModule::class))
interface AppComponent {
    val loader:TrackLoadable.UseCase
    val dateUtil:DateUtil
}