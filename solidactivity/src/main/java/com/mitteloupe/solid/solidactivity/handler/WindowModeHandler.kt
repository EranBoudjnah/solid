package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity
import android.content.res.Configuration
import android.view.WindowManager

interface WindowModeHandler {
    /**
     * @see Activity.onMultiWindowModeChanged
     */
    fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration) = Unit

    /**
     * @see Activity.onPictureInPictureModeChanged
     */
    fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) = Unit

    /**
     * @see Activity.onConfigurationChanged
     */
    fun onConfigurationChanged(newConfig: Configuration) = Unit

    /**
     * @see Activity.onWindowAttributesChanged
     */
    fun onWindowAttributesChanged(params: WindowManager.LayoutParams) = Unit
}