package com.mitteloupe.solid.application

import android.app.Application
import android.content.res.Configuration
import com.mitteloupe.solid.application.handler.ConfigurationChangeHandler
import com.mitteloupe.solid.application.handler.LifecycleHandler
import com.mitteloupe.solid.application.handler.MemoryHandler

abstract class SolidApplication : Application() {
    open val lifecycleHandlers: List<LifecycleHandler> = emptyList()

    open val configurationChangeHandlers: List<ConfigurationChangeHandler> = emptyList()

    open val memoryHandlers: List<MemoryHandler> = emptyList()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        configurationChangeHandlers.forEach { handler ->
            handler.onConfigurationChanged(newConfig)
        }
    }

    override fun onCreate() {
        super.onCreate()

        lifecycleHandlers.forEach { handler ->
            handler.onCreate()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()

        memoryHandlers.forEach { handler ->
            handler.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        memoryHandlers.forEach { handler ->
            handler.onTrimMemory(level)
        }
    }

    override fun onTerminate() {
        lifecycleHandlers.forEach { handler ->
            handler.onTerminate()
        }

        super.onTerminate()
    }
}
