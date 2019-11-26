package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View

abstract class ContextMenuHandler {
    /**
     * @see Activity.onCreateContextMenu
     */
    open fun onCreateContextMenu(menu: ContextMenu, view: View, menuInfo: ContextMenuInfo) {
        // Do nothing. Override to implement functionality.
    }

    /**
     * @see Activity.onContextItemSelected
     */
    open fun onContextItemSelected(item: MenuItem) = false

    /**
     * @see Activity.onContextMenuClosed
     */
    open fun onContextMenuClosed(menu: Menu) {
        // Do nothing. Override to implement functionality.
    }
}