package com.mitteloupe.solid.activity.handler.common

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.annotation.MenuRes
import com.mitteloupe.solid.activity.handler.OptionsMenuHandler

class SimpleOptionsMenuHandler(
    private val lazyMenuInflater: Lazy<MenuInflater>,
    @MenuRes private val menuResourceId: Int,
    private val onMenuItemSelected: (item: MenuItem) -> Boolean
) : OptionsMenuHandler {
    private val menuInflater by lazy { lazyMenuInflater.value }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(menuResourceId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        onMenuItemSelected(item)
}