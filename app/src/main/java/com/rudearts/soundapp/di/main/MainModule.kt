package com.rudearts.soundapp.di.main

import com.rudearts.soundapp.di.ActivityScope
import com.rudearts.soundapp.domain.LoadTracksUseCase
import com.rudearts.soundapp.domain.TrackLoadable
import com.rudearts.soundapp.ui.main.MainContract
import com.rudearts.soundapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides


@Module
class MainModule(internal val view:MainContract.View) {

    @Provides
    @ActivityScope
    fun providesMainView() = view

    @Provides
    @ActivityScope
    fun providesMainPresenter(view:MainContract.View, loader:TrackLoadable.UseCase):MainContract.Presenter = MainPresenter(view,loader)

}