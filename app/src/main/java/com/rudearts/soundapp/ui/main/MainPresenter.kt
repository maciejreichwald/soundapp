package com.rudearts.soundapp.ui.main

import android.os.Bundle
import com.rudearts.soundapp.domain.TrackLoadable
import com.rudearts.soundapp.model.LoadingState
import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track
import com.rudearts.soundapp.ui.details.DetailsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
        internal val view:MainContract.View,
        internal val loader:TrackLoadable.UseCase) : MainContract.Presenter {

    override fun loadTracks() {
        view.updateLoadingState(LoadingState.LOADING)

        val filter = retrieveFilter()
        loader.loadTracks(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {onItemsLoaded(it)},
                        {onError(it)})
    }

    override fun onFilterClick() {
        view.hideKeyboard()
        loadTracks()
    }

    override fun onQuestionClick(track: Track) {
        view.startDetailActivity(createQuestionIntent(track))
    }

    internal fun createQuestionIntent(track: Track) = Bundle().apply {
        putString(DetailsActivity.TITLE, track.name)
        putString(DetailsActivity.LINK, track.trackPreviewUrl)
    }

    internal fun retrieveFilter():TrackFilter {
        val sourceType = view.retrieveSelectedSourceType()
        val query = view.retrieveSearchQuery()
        return TrackFilter(query, sourceType)
    }

    internal fun onError(error: Throwable) {
        error.printStackTrace()

        updateTracksAndLoadingState(emptyList())
        handleMessagesError(error.toString())
    }

    internal fun onItemsLoaded(items:List<Track>) {
        updateTracksAndLoadingState(items)
    }

    internal fun handleMessagesError(message: String) {
        view.showMessage(message)
        updateTracksAndLoadingState(emptyList())
    }

    internal fun updateTracksAndLoadingState(items:List<Track>) {
        view.updateTracks(items)

        when(items.isEmpty()) {
            true -> view.updateLoadingState(LoadingState.NO_RESULTS)
            false -> view.updateLoadingState(LoadingState.SHOW_RESULTS)
        }
    }
}