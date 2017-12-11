package com.rudearts.soundapp.util.animation

import android.animation.Animator
import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.rudearts.soundapp.R
import com.rudearts.soundapp.extentions.visible

class CircularRevealAnimator(base:Context) : ContextWrapper(base) {

    companion object {
        private val DEFAULT_RADIUS = 0f
    }

    private var fadeIn: Animation? = null
    private var fadeOut: Animation? = null

    fun circularReveal(animatedView:View, startPointView:View, container:View) {
        animatedView.visible = true

        when (isAtLeastLollipop()) {
            true -> startLollipopCircularReveal(animatedView, startPointView, container)
            false -> startDeprecatedCircularReveal(animatedView)
        }
    }

    fun circularHide(animatedView:View, startPointView:View, container:View) = when(isAtLeastLollipop()) {
        true -> startLollipopCircularHide(animatedView, startPointView, container)
        false -> startDeprecatedCircularHide(animatedView)
    }

    private fun isAtLeastLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    /** Normally, there are libs, that allow you to do circular reveal on pre-lollipop versions
     * but I wanted to avoid using too much external libs here. Supporting that animation just for
     * couple old platforms is not the main goal here either.
     */
    private fun startDeprecatedCircularReveal(animatedView: View) {
        fadeIn = initAnimation(fadeIn, R.anim.fade_in)
        animatedView.startAnimation(fadeIn)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startLollipopCircularReveal(animatedView:View, startPointView:View, container:View) {
        val (x,y) = calculateStartPoint(startPointView)
        ViewAnimationUtils.createCircularReveal(animatedView, x, y, DEFAULT_RADIUS, container.height.toFloat()).apply {
            start()
        }
    }

    /** Same reason for this "simplified" solution as for startDeprecatedCircularReveal() function
     */
    private fun startDeprecatedCircularHide(animatedView:View) {
        fadeOut = initAnimation(fadeOut, R.anim.fade_out)

        animatedView.startAnimation(fadeOut)
        fadeOut?.setAnimationListener(createFadeOutListener(animatedView))
    }

    private fun createFadeOutListener(animatedView: View) = object : BaseAnimationListener() {
        override fun onAnimationEnd(p0: Animation?) {
            animatedView.visible = false
        }
    }

    private fun initAnimation(animation: Animation?, animationResourceId:Int) = when(animation) {
        null -> AnimationUtils.loadAnimation(this, animationResourceId)
        else -> animation
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startLollipopCircularHide(animatedView:View, startPointView:View, container:View) {
        val (x,y) = calculateStartPoint(startPointView)
        ViewAnimationUtils.createCircularReveal(animatedView, x, y, container.height.toFloat(), DEFAULT_RADIUS).apply {
            addListener(createCircularHideListener(animatedView))
            start()
        }
    }

    private fun createCircularHideListener(view:View) = object: BaseAnimatorListener() {
        override fun onAnimationEnd(p0: Animator?) {
            view.visible = false
        }
    }

    private fun calculateStartPoint(startPointView: View) = Pair(
            calculateStartParameter(startPointView.x, startPointView.width),
            calculateStartParameter(startPointView.y, startPointView.height))

    private fun calculateStartParameter(position: Float, size: Int) = position.toInt()+size/2

}