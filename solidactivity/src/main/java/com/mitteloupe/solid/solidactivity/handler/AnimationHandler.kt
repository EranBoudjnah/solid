package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity

interface AnimationHandler {
    /**
     * @see Activity.onEnterAnimationComplete
     */
    fun onEnterAnimationComplete()
}