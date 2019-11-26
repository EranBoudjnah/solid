package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity
import android.view.MotionEvent

abstract class GenericMotionHandler {
    /**
     * @see Activity.onUserInteraction
     */
    open fun onUserInteraction() {
        // Do nothing. Override to implement functionality.
    }

    /**
     * @see Activity.onTrackballEvent
     */
    open fun onTrackballEvent(event: MotionEvent) = false

    /**
     * @see Activity.onGenericMotionEvent
     */
    open fun onGenericMotionEvent(event: MotionEvent) = false
}