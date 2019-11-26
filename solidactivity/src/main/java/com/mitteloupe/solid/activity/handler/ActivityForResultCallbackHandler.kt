package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.content.Intent

interface ActivityForResultCallbackHandler {
    /**
     * @see Activity.onActivityResult
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) = Unit

    /**
     * @see Activity.onActivityReenter
     */
    fun onActivityReenter(resultCode: Int, data: Intent) = Unit
}