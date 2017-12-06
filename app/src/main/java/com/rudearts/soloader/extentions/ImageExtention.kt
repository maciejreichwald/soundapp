package com.rudearts.soloader.extentions

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.loadUrl(url: String) {
    Picasso.with(context).load(url).into(this)
}

fun ImageView.loadUrlThumb(size:Int, placeHolder:Int, url: String) {
    Picasso.with(context)
            .load(url)
            .resize(size, size)
            .placeholder(resources.getDrawable(placeHolder))
            .error(resources.getDrawable(placeHolder))
            .centerCrop()
            .into(this)
}
