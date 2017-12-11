package com.rudearts.soundapp.di

import android.content.Context
import android.content.ContextWrapper
import com.rudearts.soundapp.api.ExternalMapper
import com.rudearts.soundapp.api.RestController
import com.rudearts.soundapp.controller.AssetController
import com.rudearts.soundapp.util.DateUtil
import com.rudearts.soundapp.util.animation.CircularRevealAnimator
import com.rudearts.soundapp.util.animation.RotatedFadeAnimator
import com.rudearts.soundapp.util.loader.AssetLoader
import com.rudearts.soundapp.util.loader.TrackLoader

class MainModule(base:Context) : ContextWrapper(base), BasicModule {

    override val restController get() = RestController.instance
    override val mapper get() = ExternalMapper(this)

    override val circularAnimator get() = CircularRevealAnimator(this)
    override val fadeAnimator get() = RotatedFadeAnimator(this)

    override val dateUtil get() = DateUtil.instance

    override val assetLoader get() = AssetLoader(this)
    override val trackLoader get() = TrackLoader(this)
    override val assetController get() = AssetController.instance
}