package com.rudearts.soundapp.util.animation

import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.rudearts.soundapp.R
import com.rudearts.soundapp.extentions.visible

class RotatedFadeAnimator(base:Context, var rotatable:Boolean = true) : ContextWrapper(base) {

    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null

    private fun initAnimation(animation: Animation?, animationResourceId:Int) = when(animation) {
        null -> AnimationUtils.loadAnimation(this, animationResourceId)
        else -> animation
    }

    fun startFadeIn(btn:View) {
        fadeIn = initAnimation(fadeIn, getResourceId4FadeIn())
        startFadeAnimation(btn, fadeIn, createFadeListener(btn, true))
        btn.visible = true
    }

    fun startFadeOut(btn:View) {
        fadeOut = initAnimation(fadeOut, getResourceId4FadeOut())
        startFadeAnimation(btn, fadeOut, createFadeListener(btn, false))
    }

    private fun getResourceId4FadeIn() = when(rotatable) {
        true -> R.anim.rotate_fade_in
        false -> R.anim.filter_fade_in
    }

    private fun getResourceId4FadeOut() = when(rotatable) {
        true -> R.anim.rotate_fade_out
        false -> R.anim.fade_out
    }

    private fun createFadeListener(view: View, visible: Boolean) = object : BaseAnimationListener() {
        override fun onAnimationEnd(p0: Animation?) {
            view.visible = visible
        }
    }

    fun hasStartedAnyFade() = hasAnimationStarted(fadeIn) or hasAnimationStarted(fadeOut)

    private fun hasAnimationStarted(animation: Animation?) = when(animation) {
        null -> false
        else -> animation.hasStarted() and !animation.hasEnded()
    }

    private fun startFadeAnimation(btn: View, animation: Animation?, listener:Animation.AnimationListener) {
        btn.startAnimation(animation)
        animation?.setAnimationListener(listener)
    }

}