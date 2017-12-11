package com.rudearts.soloader.ui.main.filter

import com.rudearts.soloader.model.filter.TrackFilter

interface FilterContract {

    interface View {
        fun showAnimated()
        fun hideAnimated()
        fun hide()
        fun isShown():Boolean
        fun getFilter():TrackFilter
    }

}