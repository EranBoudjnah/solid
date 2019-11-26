package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View

interface ViewCreationHandler {
    /**
     * @see Activity.onCreateView
     */
    fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? = null

    /**
     * @see Activity.onCreateView
     */
    fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? = null

    /**
     * @see Activity.onContentChanged
     */
    fun onContentChanged() = Unit
}