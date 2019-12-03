package com.mitteloupe.solid.service.handler

import android.app.Service
import android.content.Intent

interface TaskRemovalHandler {
    /**
     * @see Service.onTaskRemoved
     */
    fun onTaskRemoved(rootIntent: Intent) = Unit
}
