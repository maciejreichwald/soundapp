package com.rudearts.soundapp.di.app

import android.content.Context
import com.rudearts.soundapp.util.loader.ExternalMapper
import com.rudearts.soundapp.api.RestController
import com.rudearts.soundapp.util.DateUtil
import com.rudearts.soundapp.util.loader.AssetCache
import com.rudearts.soundapp.util.loader.AssetLoader
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ExternalModule(private val context:Context) {

    @Provides
    fun providesAssetLoader(): AssetLoader = AssetLoader(context)

    @Provides
    @Singleton
    fun providesAssetCache() = AssetCache()

    @Provides
    @Singleton
    fun provideRestController():RestController = RestController()

    @Provides
    fun providesExternalMapper(dateUtil: DateUtil):ExternalMapper = ExternalMapper(context, dateUtil)

    @Provides
    @Singleton
    fun provideDateUtil() = DateUtil()
}