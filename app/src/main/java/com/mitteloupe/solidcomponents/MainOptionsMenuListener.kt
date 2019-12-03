package com.mitteloupe.solidcomponents

import android.view.MenuItem
import android.widget.Toast

class MainOptionsMenuListener(
    private val activity: MainActivity
) : (MenuItem) -> Boolean {
    override fun invoke(item: MenuItem) = when (item.itemId) {
        R.id.menu_item_main_option1 -> {
            Toast.makeText(
                activity,
                R.string.menu_item_example_1,
                Toast.LENGTH_SHORT
            )
                .show()
            true
        }
        R.id.menu_item_main_option2 -> {
            Toast.makeText(
                activity,
                R.string.menu_item_example_2,
                Toast.LENGTH_SHORT
            )
                .show()
            true
        }

        else -> false
    }
}
