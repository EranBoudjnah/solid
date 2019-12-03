package com.mitteloupe.solidcomponents

import com.mitteloupe.solid.application.SolidApplication
import com.mitteloupe.solidcomponents.hander.KoinApplicationScopeHandler

class SampleSolidApplication : SolidApplication() {
    override val lifecycleHandlers = listOf(
        KoinApplicationScopeHandler(this)
    )
}
