package com.rudearts.soloader.extentions

import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadUrlThumb(size:Int, placeHolderId:Int, url: String) {
    val placeHolder = ResourcesCompat.getDrawable(resources, placeHolderId, null)
    Picasso.with(context)
            .load(url)
            .resize(size, size)
            .placeholder(placeHolder)
            .error(placeHolder)
            .centerCrop()
            .into(this)
}
