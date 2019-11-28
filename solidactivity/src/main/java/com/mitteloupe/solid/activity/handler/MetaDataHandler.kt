package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.app.assist.AssistContent
import android.os.Bundle

interface MetaDataHandler {
    /**
     * @see Activity.onProvideAssistContent
     */
    fun onProvideAssistContent(outContent: AssistContent) = Unit

    /**
     * @see Activity.onProvideAssistData
     */
    fun onProvideAssistData(data: Bundle) = Unit

    /**
     * @see Activity.onCreateDescription
     */
    fun onCreateDescription(): CharSequence? = null
}
