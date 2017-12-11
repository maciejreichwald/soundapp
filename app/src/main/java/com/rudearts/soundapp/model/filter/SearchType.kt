package com.rudearts.soundapp.model.filter

import com.rudearts.soundapp.R

enum class SearchType(private val textId:Int) : Titlable {
    SONG(R.string.song),
    ARTIST(R.string.artist);

    override fun getTitleId() = textId
}