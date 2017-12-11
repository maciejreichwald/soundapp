package com.rudearts.soloader.ui.main

import com.rudearts.soloader.model.filter.TrackFilter
import com.rudearts.soloader.model.local.Track

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