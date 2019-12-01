package com.mitteloupe.solid.fragment.handler

import android.content.res.Configuration
import androidx.fragment.app.Fragment

interface WindowModeHandler {
    /**
     * @see Fragment.onConfigurationChanged
     */
    fun onConfigurationChanged(newConfig: Configuration) = Unit

    /**
     * @see Fragment.onMultiWindowModeChanged
     */
    fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) = Unit

    /**
     * @see Fragment.onPictureInPictureModeChanged
     */
    fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) = Unit
}
