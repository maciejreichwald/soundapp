package com.rudearts.soundapp.di.main

import android.content.Context
import com.rudearts.soundapp.di.ActivityScope
import com.rudearts.soundapp.domain.LoadTracksUseCase
import com.rudearts.soundapp.domain.TrackLoadable
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.ui.main.MainContract
import com.rudearts.soundapp.ui.main.MainPresenter
import com.rudearts.soundapp.ui.main.adapters.FilterAdapter
import com.rudearts.soundapp.ui.main.adapters.TrackAdapter
import com.rudearts.soundapp.util.DateUtil
import dagger.Module
import dagger.Provides


@Module
class MainModule(private val context:Context, private val view:MainContract.View) {

    @Provides
    @ActivityScope
    fun providesMainView() = view

    @Provides
    @ActivityScope
    fun providesContext() = context

    @Provides
    @ActivityScope
    fun providesContext() = context

    @Provides
    @ActivityScope
    fun providesMainPresenter(view:MainContract.View, loader:TrackLoadable.UseCase):MainContract.Presenter = MainPresenter(view,loader)

    @Provides
    @ActivityScope
    fun providesTrackAdapter(context:Context, dateUtil: DateUtil):TrackAdapter = TrackAdapter(context, dateUtil)

    @Provides
    @ActivityScope
    fun providesFilterAdapter(context: Context):FilterAdapter = FilterAdapter(context)
}