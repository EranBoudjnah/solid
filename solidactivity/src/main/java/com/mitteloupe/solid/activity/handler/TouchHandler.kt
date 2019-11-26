package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.MotionEvent

interface TouchHandler {
    /**
     * @see Activity.onTouchEvent
     */
    fun onTouchEvent(event: MotionEvent): Boolean
}