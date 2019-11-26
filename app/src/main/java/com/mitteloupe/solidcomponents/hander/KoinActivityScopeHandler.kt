package com.mitteloupe.solidcomponents.hander

import android.app.Activity
import android.os.Bundle
import com.mitteloupe.solid.activity.handler.LifecycleHandler
import org.koin.core.scope.Scope

class KoinActivityScopeHandler(
    private val activity: Activity,
    private val currentScope: Scope
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(activity)
    }
}