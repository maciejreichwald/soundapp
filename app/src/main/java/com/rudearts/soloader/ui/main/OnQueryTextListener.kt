package com.rudearts.soloader.ui.main

import android.support.v7.widget.SearchView

open class OnQueryTextListener : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}