package com.mitteloupe.solid.activity.handler

import android.app.Activity

interface AnimationHandler {
    /**
     * @see Activity.onEnterAnimationComplete
     */
    fun onEnterAnimationComplete()
}
