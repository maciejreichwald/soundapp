package com.rudearts.soundapp.util.animation

import android.animation.Animator

open class BaseAnimatorListener : Animator.AnimatorListener {

    override fun onAnimationRepeat(p0: Animator?) {}

    override fun onAnimationEnd(p0: Animator?) {}

    override fun onAnimationCancel(p0: Animator?) {}

    override fun onAnimationStart(p0: Animator?) {}
}