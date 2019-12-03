package com.mitteloupe.solid.service.handler

import android.app.Service
import android.content.res.Configuration

interface ConfigurationChangeHandler {
    /**
     * @see Service.onConfigurationChanged
     */
    fun onConfigurationChanged(newConfig: Configuration)
}
