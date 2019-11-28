package com.mitteloupe.solid.activity.handler.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.mitteloupe.solid.activity.handler.LifecycleHandler

class LayoutLifecycleHandler(
    private val activity: AppCompatActivity,
    @LayoutRes private val layoutResourceId: Int
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity.setContentView(layoutResourceId)
    }
}
