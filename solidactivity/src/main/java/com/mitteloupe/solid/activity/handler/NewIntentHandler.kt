package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.content.Intent

interface NewIntentHandler {
    /**
     * @see Activity.onNewIntent
     */
    fun onNewIntent(intent: Intent)
}