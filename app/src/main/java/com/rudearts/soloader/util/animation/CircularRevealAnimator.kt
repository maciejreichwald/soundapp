package com.rudearts.soloader.util.animation

import android.animation.Animator
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

class CircularRevealAnimator(base:Context) : ContextWrapper(base) {

    companion object {
        private val DEFAULT_RADIUS = 0f
    }

    private var fadeIn: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
    private var fadeOut: Animation = AnimationUtils.loadAnimation(this, R.anim.fade_out)

    fun circuralReveal(animatedView:View, startPointView:View, container:View) = when(isAtLeastLollipop()) {
        true -> startLollipopCircularReveal(animatedView, startPointView, container)
        false -> startDeprecatedCircularReveal(animatedView)
    }

    fun circuralHide(animatedView:View, startPointView:View, container:View) = when(isAtLeastLollipop()) {
        true -> startLollipopCircularHide(animatedView, startPointView, container)
        false -> startDeprecatedCircullarHide(animatedView)
    }

    private fun isAtLeastLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

    private fun startDeprecatedCircularReveal(animatedView: View) {
        animatedView.startAnimation(fadeIn)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startLollipopCircularReveal(animatedView:View, startPointView:View, container:View) {
        val (x,y) = calculateStartPoint(startPointView)
        val animator = ViewAnimationUtils.createCircularReveal(animatedView, x, y, DEFAULT_RADIUS, container.height.toFloat())
        animator.start()
    }

    private fun startDeprecatedCircullarHide(animatedView:View) {
        animatedView.startAnimation(fadeOut)
        fadeOut.setAnimationListener(createFadeOutListener(animatedView))
    }

    private fun createFadeOutListener(animatedView: View) = object : BaseAnimationListener() {
        override fun onAnimationEnd(p0: Animation?) {
            animatedView.visible = false
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun startLollipopCircularHide(animatedView:View, startPointView:View, container:View) {
        val (x,y) = calculateStartPoint(startPointView)
        val animator = ViewAnimationUtils.createCircularReveal(animatedView, x, y, container.height.toFloat(), DEFAULT_RADIUS)
        animator.addListener(createCircuralCollapseListener(animatedView))
        animator.start()
    }

    private fun createCircuralCollapseListener(view:View) = object: BaseAnimatorListener() {
        override fun onAnimationEnd(p0: Animator?) {
            view.visible = false
        }
    }

    private fun calculateStartPoint(startPointView: View) = Pair<Int, Int>(
            calculateStartParameter(startPointView.x, startPointView.width),
            calculateStartParameter(startPointView.y, startPointView.height))

    private fun calculateStartParameter(position: Float, size: Int) = position.toInt()+size/2

}