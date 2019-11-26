package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity

interface WindowFocusHandler {
    /**
     * @see Activity.onWindowFocusChanged
     */
    fun onWindowFocusChanged(hasFocus: Boolean)
}