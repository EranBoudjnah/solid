package com.mitteloupe.solid.application.handler

import android.app.Application

interface LifecycleHandler {
    /**
     * @see Application.onCreate
     */
    fun onCreate() = Unit

    /**
     * @see Application.onCreate
     */
    fun onTerminate() = Unit
}
