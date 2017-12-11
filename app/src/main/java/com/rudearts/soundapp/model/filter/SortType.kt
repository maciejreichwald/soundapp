package com.rudearts.soundapp.model.filter

import com.rudearts.soundapp.R

enum class SortType(private val textId:Int) : Titlable {
    SONG(R.string.song),
    ARTIST(R.string.artist),
    RELEASE_DATE(R.string.release_date);

    override fun getTitleId() = textId
}