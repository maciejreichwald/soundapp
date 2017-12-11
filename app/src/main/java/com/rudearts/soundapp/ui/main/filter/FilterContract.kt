package com.rudearts.soundapp.ui.main.filter

import com.rudearts.soundapp.model.filter.TrackFilter

interface FilterContract {

    interface View {
        fun activatedAnimated(query:String)
        fun hideAnimated()
        fun restoreFilter()
        fun hide()
        fun isShown():Boolean
        fun getFilter(query:String):TrackFilter
        fun hasFilterChanged(query:String):Boolean
    }

    interface Presenter {
        fun storeFilter(filter:TrackFilter)
        fun retrieveFilter():TrackFilter?
        fun checkFilterChanged(filter:TrackFilter):Boolean
    }

}