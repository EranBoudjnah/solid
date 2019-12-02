package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.Menu

interface MenuOpenedHandler {
    /**
     * Gets called when a menu is opened, but a [Menu] does not yet exist.
     *
     * @return true if menu should open, false otherwise
     *
     * @see Activity.onMenuOpened
     */
    fun onMenuOpening(featureId: Int): Boolean

    /**
     * Gets called when a menu is opened and a [Menu] exists.
     *
     * @return true if menu should open, false otherwise
     *
     * @see Activity.onMenuOpened
     */
    fun onMenuOpened(featureId: Int, menu: Menu): Boolean
}
