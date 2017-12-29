package com.rudearts.soundapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.rudearts.soundapp.model.LoadingState
import com.rudearts.soundapp.model.filter.SourceType
import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track

interface MainContract {

    interface View {
        fun updateLoadingState(state:LoadingState)
        fun updateTracks(tracks: List<Track>)
        fun showMessage(message: String)
        fun hideKeyboard()
        fun startDetailActivity(extras:Bundle)
        fun retrieveSearchQuery(): String
        fun retrieveSelectedSourceType(): SourceType
    }

    interface Presenter {
        fun loadTracks()
        fun onQuestionClick(track: Track)
        fun onFilterClick()
    }
}