package com.mitteloupe.solid.fragment.handler

import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment

interface ContextMenuHandler {
    /**
     * @see Fragment.onCreateContextMenu
     */
    fun onCreateContextMenu(
        menu: ContextMenu,
        view: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) = Unit

    /**
     * @see Fragment.onContextItemSelected
     */
    fun onContextItemSelected(item: MenuItem) = false
}
