package com.mitteloupe.solid.activity.handler.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mitteloupe.solid.activity.handler.LifecycleHandler

class ToolbarHandler(
    private val activity: AppCompatActivity,
    private val lazyToolbar: Lazy<Toolbar>
) : LifecycleHandler {
    private val toolbar by lazy { lazyToolbar.value }

    override fun onCreate(savedInstanceState: Bundle?) {
        activity.setSupportActionBar(toolbar)
    }
}