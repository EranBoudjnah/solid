package com.mitteloupe.solidcomponents

import android.content.Intent
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.navigation.NavigationView
import com.mitteloupe.solid.activity.SolidActivity
import com.mitteloupe.solidcomponents.service.SolidIntentService

class MainDrawerItemSelectedListener(
    private val activity: SolidActivity
) : NavigationView.OnNavigationItemSelectedListener {
    override fun onNavigationItemSelected(p0: MenuItem) = when (p0.itemId) {
        R.id.menu_item_main_example1 -> {
            showToast(R.string.menu_item_example_1)

            launchIntentService(activity.getString(R.string.intent_service_example_1))

            true
        }
        R.id.menu_item_main_example2 -> {
            showToast(R.string.menu_item_example_2)

            launchIntentService(activity.getString(R.string.intent_service_example_2))

            true
        }
        else -> false
    }

    private fun showToast(@StringRes messageResource: Int) {
        Toast.makeText(
            activity,
            messageResource,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun launchIntentService(inputMessage: String) {
        val solidServiceIntent = Intent(activity, SolidIntentService::class.java)
        solidServiceIntent.putExtra(SolidIntentService.PARAM_INPUT_MESSAGE, inputMessage)
        activity.startService(solidServiceIntent)
    }
}
