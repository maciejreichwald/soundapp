package com.rudearts.soundapp.extentions

import android.databinding.BindingAdapter
import android.widget.ImageView

@BindingAdapter("imageResource")
fun loadImage(view: ImageView, resourceId:Int) = view.setImageResource(resourceId)