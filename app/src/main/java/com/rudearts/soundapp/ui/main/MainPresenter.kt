package com.rudearts.soundapp.ui.main

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.soundapp.SongApplication
import com.rudearts.soundapp.di.BasicModule
import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.util.loader.TrackLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import space.traversal.kapsule.Injects
import space.traversal.kapsule.inject
import space.traversal.kapsule.required

class MainPresenter(base:Context, val view:MainContract.View) : ContextWrapper(base), MainContract.Presenter, Injects<BasicModule> {

    internal val loader by required { trackLoader }

    init {
        inject(SongApplication.module(this))
    }

    override fun loadTracks(filter:TrackFilter) {
        view.updateLoadingState(true)

        loader.loadTracks(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {onItemsLoaded(it)},
                        {onError(it)})
    }

    private fun onError(error: Throwable) {
        error.printStackTrace()

        view.updateLoadingState(false)
        handleMessagesError(error.toString())
    }

    private fun onItemsLoaded(items:List<Track>) = with(view) {
        updateLoadingState(false)
        updateTracks(items)
    }

    private fun handleMessagesError(message: String) = with(view) {
        showMessage(message)
        updateTracks(ArrayList())
    }
}