package com.rudearts.soloader.model.filter

import com.rudearts.soloader.R

enum class SearchType(private val textId:Int) : Titlable {
    SONG(R.string.song),
    ARTIST(R.string.artist);

    override fun getTitleId() = textId
}