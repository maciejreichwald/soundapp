package com.rudearts.soloader.ui.main.filter

import com.rudearts.soloader.model.filter.TrackFilter

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