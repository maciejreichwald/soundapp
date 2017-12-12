package com.rudearts.soundapp.di

import android.content.Context
import android.content.ContextWrapper
import com.nhaarman.mockitokotlin2.mock
import com.rudearts.soundapp.api.ExternalMapper
import com.rudearts.soundapp.api.RestController
import com.rudearts.soundapp.controller.AssetController
import com.rudearts.soundapp.ui.main.filter.FilterContract
import com.rudearts.soundapp.ui.main.filter.FilterPresenter
import com.rudearts.soundapp.util.DateUtil
import com.rudearts.soundapp.util.animation.CircularRevealAnimator
import com.rudearts.soundapp.util.animation.RotatedFadeAnimator
import com.rudearts.soundapp.util.loader.AssetLoader
import com.rudearts.soundapp.util.loader.TrackLoader

class TestModule(base:Context) : ContextWrapper(base), BasicModule {

    override val restController get() = mock<RestController> {}
    override val mapper get() = mock<ExternalMapper>{}

    override val circularAnimator get() = mock<CircularRevealAnimator> {}
    override val fadeAnimator get() = mock<RotatedFadeAnimator> {}

    override val dateUtil get() = mock<DateUtil> {}

    override val assetLoader get() = mock<AssetLoader> {}
    override val trackLoader get() = mock<TrackLoader> {}
    override val assetController get() = mock<AssetController> {}

    override val filterPresenter: FilterContract.Presenter
        get() = mock<FilterPresenter> {}
}