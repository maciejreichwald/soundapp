package com.rudearts.soloader.model.filter

import com.rudearts.soloader.R

enum class SourceType(private val textId:Int) : Titlable {
    BOTH(R.string.source_both),
    REST(R.string.source_rest),
    ASSET(R.string.source_asset);

    override fun getTitleId() = textId
}