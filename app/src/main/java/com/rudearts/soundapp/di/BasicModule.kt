package com.rudearts.soundapp.di

import com.rudearts.soundapp.api.ExternalMapper
import com.rudearts.soundapp.api.RestController
import com.rudearts.soundapp.controller.AssetController
import com.rudearts.soundapp.ui.main.filter.FilterContract
import com.rudearts.soundapp.util.DateUtil
import com.rudearts.soundapp.util.animation.CircularRevealAnimator
import com.rudearts.soundapp.util.animation.RotatedFadeAnimator
import com.rudearts.soundapp.util.loader.AssetLoader
import com.rudearts.soundapp.util.loader.TrackLoader

interface BasicModule {
    val restController:RestController
    val mapper:ExternalMapper

    val circularAnimator:CircularRevealAnimator
    val fadeAnimator:RotatedFadeAnimator

    val dateUtil:DateUtil

    val assetLoader:AssetLoader
    val trackLoader:TrackLoader
    val assetController:AssetController

    val filterPresenter:FilterContract.Presenter
}