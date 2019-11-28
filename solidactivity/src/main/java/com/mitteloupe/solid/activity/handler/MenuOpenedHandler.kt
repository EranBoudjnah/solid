package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.Menu

interface MenuOpenedHandler {
    /**
     * @return true if menu should open, false otherwise
     *
     * @see Activity.onMenuOpened
     */
    fun onMenuOpened(featureId: Int, menu: Menu): Boolean
}
