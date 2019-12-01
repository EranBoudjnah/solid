package com.mitteloupe.solid.fragment.handler

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment

interface OptionsMenuHandler {
    /**
     * @see Fragment.onPrepareOptionsMenu
     */
    fun onPrepareOptionsMenu(menu: Menu) = Unit

    /**
     * @see Fragment.onCreateOptionsMenu
     */
    fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = Unit

    /**
     * @see Fragment.onOptionsItemSelected
     */
    fun onOptionsItemSelected(item: MenuItem?) = false

    /**
     * @see Fragment.onOptionsMenuClosed
     */
    fun onOptionsMenuClosed(menu: Menu?) = Unit

    /**
     * @see Fragment.onDestroyOptionsMenu
     */
    fun onDestroyOptionsMenu() = Unit
}
