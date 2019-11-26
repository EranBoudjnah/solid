package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity

interface WindowAttachmentHandler {
    /**
     * @see Activity.onAttachedToWindow
     */
    fun onAttachedToWindow() = Unit

    /**
     * @see Activity.onDetachedFromWindow
     */
    fun onDetachedFromWindow() = Unit
}