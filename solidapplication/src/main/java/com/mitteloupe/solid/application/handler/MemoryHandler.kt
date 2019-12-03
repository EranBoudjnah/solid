package com.mitteloupe.solid.application.handler

import android.app.Application
import android.content.ComponentCallbacks2
import androidx.annotation.IntDef

@IntDef(
    value = [ComponentCallbacks2.TRIM_MEMORY_COMPLETE, ComponentCallbacks2.TRIM_MEMORY_MODERATE, ComponentCallbacks2.TRIM_MEMORY_BACKGROUND, ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN, ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL, ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW, ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE]
)
@Retention(AnnotationRetention.SOURCE)
annotation class TrimMemoryLevel

interface MemoryHandler {
    /**
     * @see Application.onLowMemory
     */
    fun onLowMemory() = Unit

    /**
     * @see Application.onTrimMemory
     */
    fun onTrimMemory(@TrimMemoryLevel level: Int) = Unit
}
