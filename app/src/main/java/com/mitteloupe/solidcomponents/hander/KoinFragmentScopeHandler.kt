package com.mitteloupe.solidcomponents.hander

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mitteloupe.solid.fragment.handler.LifecycleHandler
import org.koin.core.scope.Scope

class KoinFragmentScopeHandler(
    private val fragment: Fragment,
    private val currentScope: Scope
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(fragment)
    }
}
