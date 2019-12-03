package com.mitteloupe.solidcomponents

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.mitteloupe.solidcomponents.service.SolidIntentService

class ResponseReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val text = intent.getStringExtra(SolidIntentService.PARAM_OUTPUT_MESSAGE)

        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        const val ACTION_RESPONSE = "com.mitteloupe.solidcomponents.action.MESSAGE_PROCESSED"
    }
}
