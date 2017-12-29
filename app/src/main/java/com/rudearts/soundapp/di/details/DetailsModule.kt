package com.rudearts.soundapp.di.details

import com.rudearts.soundapp.di.ActivityScope
import com.rudearts.soundapp.domain.LoadTracksUseCase
import com.rudearts.soundapp.domain.TrackLoadable
import com.rudearts.soundapp.ui.details.DetailsContract
import com.rudearts.soundapp.ui.details.DetailsPresenter
import com.rudearts.soundapp.ui.main.MainContract
import com.rudearts.soundapp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides


@Module
class DetailsModule(internal val view:DetailsContract.View) {

    @Provides
    @ActivityScope
    fun providesDetailsView() = view

    @Provides
    @ActivityScope
    fun providesDetailsPresenter(view:DetailsContract.View):DetailsContract.Presenter = DetailsPresenter(view)

}