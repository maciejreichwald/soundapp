package com.rudearts.soloader.ui.main.filter

import com.rudearts.soloader.model.filter.TrackFilter

class FilterPresenter : FilterContract.Presenter {

    private var storedFilter:TrackFilter? = null

    override fun storeFilter(filter: TrackFilter) {
        storedFilter = filter
    }

    override fun retrieveFilter(): TrackFilter? = storedFilter

    override fun checkFilterChanged(filter: TrackFilter) = when(storedFilter) {
        null -> false
        else -> storedFilter != filter
    }
}