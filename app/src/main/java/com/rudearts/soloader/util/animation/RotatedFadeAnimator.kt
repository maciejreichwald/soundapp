package com.rudearts.soloader.util.animation

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.rudearts.soloader.R
import com.rudearts.soloader.extentions.visible

class RotatedFadeAnimator(base:Context) : ContextWrapper(base) {

    private var fadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate_fade_in)
    private var fadeOut: Animation = AnimationUtils.loadAnimation(this, R.anim.rotate_fade_out)

    fun startFadeIn(btn:View, listener: Animation.AnimationListener) =
            startFadeAnimation(btn, fadeIn, listener)

    fun startFadeOut(btn:View, listener: Animation.AnimationListener) =
            startFadeAnimation(btn, fadeOut, listener)

    fun hasStartedAnyFade() = hasStartedFadeIn() or hasStartedFadeOut()

    fun hasStartedFadeIn() = hasAnimationStarted(fadeIn)
    fun hasStartedFadeOut() = hasAnimationStarted(fadeOut)

    private fun hasAnimationStarted(animation: Animation) = with(animation) {
        hasStarted() and !hasEnded()
    }

    private fun startFadeAnimation(btn: View, animation: Animation, listener:Animation.AnimationListener) {
        btn.startAnimation(animation)
        animation.setAnimationListener(listener)
    }

}