package com.mitteloupe.solidcomponents.service

import android.content.Intent
import android.os.SystemClock
import android.text.format.DateFormat
import com.mitteloupe.solid.service.SolidService
import com.mitteloupe.solid.service.handler.common.IntentHandler
import com.mitteloupe.solidcomponents.ResponseReceiver

private const val DELAY_TIME_MILLISECONDS = 3000L

class SolidIntentService : SolidService() {
    override val lifecycleHandlers = listOf(
        IntentHandler(
            this,
            { intent -> handleIntent(intent) }
        )
    )

    private fun handleIntent(intent: Intent?) {
        val inMessage = intent?.getStringExtra(PARAM_INPUT_MESSAGE) ?: "Input message missing"
        SystemClock.sleep(DELAY_TIME_MILLISECONDS)

        val result = processInput(inMessage)

        broadcastResult(result)
    }

    private fun processInput(input: String) =
        "$input -> ${formattedTimestamp()}"

    private fun formattedTimestamp() =
        DateFormat.format("MM/dd/yy hh:mmaa", System.currentTimeMillis())

    private fun broadcastResult(result: String) {
        val broadcastIntent = Intent()
        broadcastIntent.action = ResponseReceiver.ACTION_RESPONSE
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT)
        broadcastIntent.putExtra(PARAM_OUTPUT_MESSAGE, result)
        sendBroadcast(broadcastIntent)
    }

    companion object {
        const val PARAM_INPUT_MESSAGE = "inMessage"
        const val PARAM_OUTPUT_MESSAGE = "outMessage"
    }
}
