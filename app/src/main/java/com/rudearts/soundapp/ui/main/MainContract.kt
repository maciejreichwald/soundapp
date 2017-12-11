package com.rudearts.soundapp.ui.main

import com.rudearts.soundapp.model.filter.TrackFilter
import com.rudearts.soundapp.model.local.Track

interface MainContract {

    interface View {
        fun updateLoadingState(isLoading:Boolean)
        fun updateTracks(tracks:List<Track>)
        fun showMessage(message:String)
    }

    interface Presenter {
        fun loadTracks(filter:TrackFilter)
    }
}