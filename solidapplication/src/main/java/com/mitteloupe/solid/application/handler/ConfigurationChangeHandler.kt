package com.mitteloupe.solid.application.handler

import android.app.Application
import android.content.res.Configuration

interface ConfigurationChangeHandler {
    /**
     * @see Application.onConfigurationChanged
     */
    fun onConfigurationChanged(newConfig: Configuration)
}
