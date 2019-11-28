package com.mitteloupe.solid.activity.handler

import android.app.Activity

interface WindowFocusHandler {
    /**
     * @see Activity.onWindowFocusChanged
     */
    fun onWindowFocusChanged(hasFocus: Boolean)
}
