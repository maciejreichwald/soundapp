package com.rudearts.soloader.model.filter

import com.rudearts.soloader.R

enum class SortType(private val textId:Int) : Titlable {
    SONG(R.string.song),
    ARTIST(R.string.artist),
    RELEASE_DATE(R.string.release_date);

    override fun getTitleId() = textId
}