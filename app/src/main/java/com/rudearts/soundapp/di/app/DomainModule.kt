package com.rudearts.soundapp.di.app

import android.content.Context
import com.rudearts.soundapp.api.RestController
import com.rudearts.soundapp.domain.LoadTracksFromAssets
import com.rudearts.soundapp.domain.LoadTracksFromRest
import com.rudearts.soundapp.domain.LoadTracksUseCase
import com.rudearts.soundapp.domain.TrackLoadable
import com.rudearts.soundapp.util.loader.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    fun providesLoadTracksFromAssets(
            assetLoader: AssetLoader,
            assetCache: AssetCache,
            externalMapper: ExternalMapper):TrackLoadable.Asset = LoadTracksFromAssets(assetLoader, assetCache, externalMapper)


    @Provides
    fun providesLoadTracksFromRest(
            restController: RestController,
            externalMapper: ExternalMapper):TrackLoadable.Rest = LoadTracksFromRest(restController, externalMapper)

    @Provides
    fun providesLoadTracksUseCase(
            loadRest:TrackLoadable.Rest,
            loadAssets:TrackLoadable.Asset):TrackLoadable.UseCase =
            LoadTracksUseCase(loadRest, loadAssets)


}