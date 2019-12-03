package com.mitteloupe.solidcomponents.hander

import android.app.Activity
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import com.mitteloupe.solid.activity.handler.LifecycleHandler
import com.mitteloupe.solidcomponents.ResponseReceiver

class ResponseReceiverHandler(
    private val activity: Activity,
    private val responseReceiver: ResponseReceiver
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        val filter =
            IntentFilter(ResponseReceiver.ACTION_RESPONSE)
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        activity.registerReceiver(responseReceiver, filter)
    }

    override fun onDestroy() {
        activity.unregisterReceiver(responseReceiver)
    }
}
