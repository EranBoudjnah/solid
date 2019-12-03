package com.mitteloupe.solidcomponents

import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import com.mitteloupe.solid.activity.SolidActivity

class MainDrawerItemSelectedListener(
    private val activity: SolidActivity
) : NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem) = when (p0.itemId) {
        R.id.menu_item_main_example1 -> {
            Toast.makeText(
                activity,
                R.string.menu_item_example_1,
                Toast.LENGTH_SHORT
            )
                .show()
            true
        }
        R.id.menu_item_main_example2 -> {
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
