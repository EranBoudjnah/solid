package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.MotionEvent

interface GenericMotionHandler {
    /**
     * @see Activity.onUserInteraction
     */
    fun onUserInteraction() = Unit

    /**
     * @see Activity.onTrackballEvent
     */
    fun onTrackballEvent(event: MotionEvent) = false

    /**
     * @see Activity.onGenericMotionEvent
     */
    fun onGenericMotionEvent(event: MotionEvent) = false
}
