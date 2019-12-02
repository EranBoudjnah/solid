package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.Menu
import android.view.MenuItem

interface OptionsMenuHandler {
    /**
     * @see Activity.onPrepareOptionsMenu
     */
    fun onPrepareOptionsMenu(menu: Menu) = true

    /**
     * @see Activity.onCreateOptionsMenu
     */
    fun onCreateOptionsMenu(menu: Menu) = true

    /**
     * @see Activity.onOptionsItemSelected
     */
    fun onOptionsItemSelected(item: MenuItem) = false

    /**
     * @see Activity.onOptionsMenuClosed
     */
    fun onOptionsMenuClosed(menu: Menu) = Unit
}
