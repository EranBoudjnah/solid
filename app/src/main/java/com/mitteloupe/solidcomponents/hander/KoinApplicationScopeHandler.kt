package com.mitteloupe.solidcomponents.hander

import android.app.Application
import com.mitteloupe.solid.application.handler.LifecycleHandler
import com.mitteloupe.solidcomponents.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinApplicationScopeHandler(
    private val application: Application
) : LifecycleHandler {
    override fun onCreate() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(application)
            modules(appModule)
        }
    }
}
