package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View

interface ContextMenuHandler {
    /**
     * @see Activity.onCreateContextMenu
     */
    fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenuInfo) = Unit

    /**
     * @see Activity.onContextItemSelected
     */
    fun onContextItemSelected(item: MenuItem) = false

    /**
     * @see Activity.onContextMenuClosed
     */
    fun onContextMenuClosed(menu: Menu) = Unit
}
