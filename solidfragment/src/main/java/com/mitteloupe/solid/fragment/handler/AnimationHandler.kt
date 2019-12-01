package com.mitteloupe.solid.fragment.handler

import android.animation.Animator
import android.view.animation.Animation
import androidx.fragment.app.Fragment

interface AnimationHandler {
    /**
     * @see Fragment.onCreateAnimator
     */
    fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int): Animator? = null

    /**
     * @see Fragment.onCreateAnimation
     */
    fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? = null
}
