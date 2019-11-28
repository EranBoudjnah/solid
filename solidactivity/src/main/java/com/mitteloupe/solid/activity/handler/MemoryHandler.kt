package com.mitteloupe.solid.activity.handler

import android.app.Activity

interface MemoryHandler {
    /**
     * @see Activity.onLowMemory
     */
    fun onLowMemory() = Unit

    /**
     * @see Activity.onTrimMemory
     */
    fun onTrimMemory(level: Int) = Unit
}
