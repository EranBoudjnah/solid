package com.mitteloupe.solid.service

import android.app.Service
import android.content.Intent
import android.content.res.Configuration
import android.os.IBinder
import com.mitteloupe.solid.service.handler.BindingHandler
import com.mitteloupe.solid.service.handler.ConfigurationChangeHandler
import com.mitteloupe.solid.service.handler.LifecycleHandler
import com.mitteloupe.solid.service.handler.MemoryHandler
import com.mitteloupe.solid.service.handler.START_DEFAULT_BEHAVIOUR
import com.mitteloupe.solid.service.handler.TaskRemovalHandler

abstract class SolidService : Service() {
    open val lifecycleHandlers: List<LifecycleHandler> = emptyList()

    open val bindingHandlers: List<BindingHandler> = emptyList()

    open val taskRemovalHandlers: List<TaskRemovalHandler> = emptyList()

    open val configurationChangeHandlers: List<ConfigurationChangeHandler> = emptyList()

    open val memoryHandlers: List<MemoryHandler> = emptyList()

    override fun onCreate() {
        super.onCreate()

        lifecycleHandlers.forEach { handler ->
            handler.onCreate()
        }
    }

    /**
     * Returns the first non-[START_DEFAULT_BEHAVIOUR] result.
     * Falls back to default behaviour if no such results found.
     *
     * @see [Service.onStartCommand]
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int) =
        lifecycleHandlers
            .asSequence()
            .map { handler -> handler.onStartCommand(intent, flags, startId) }
            .firstOrNull { result -> result != START_DEFAULT_BEHAVIOUR }
            ?: super.onStartCommand(intent, flags, startId)

    override fun onDestroy() {
        lifecycleHandlers.forEach { handler ->
            handler.onDestroy()
        }

        super.onDestroy()
    }

    override fun onTaskRemoved(rootIntent: Intent) {
        taskRemovalHandlers.forEach { handler ->
            handler.onTaskRemoved(rootIntent)
        }

        super.onTaskRemoved(rootIntent)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        configurationChangeHandlers.forEach { handler ->
            handler.onConfigurationChanged(newConfig)
        }
    }

    override fun onBind(intent: Intent): IBinder? =
        bindingHandlers.firstResultOrNull { handler ->
            handler.onBind(intent)
        }

    override fun onRebind(intent: Intent) {
        super.onRebind(intent)

        bindingHandlers.forEach { handler ->
            handler.onRebind(intent)
        }
    }

    /**
     * Note that all binding handlers will be notified. However,
     * if *any* of the handlers returned true, [onRebind] would get called.
     *
     * @see Service.onUnbind
     */
    override fun onUnbind(intent: Intent): Boolean {
        var result = false
        bindingHandlers.forEach { handler ->
            result = handler.onUnbind(intent) || result
        }
        return super.onUnbind(intent) || result
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
}

private fun <HANDLER, RETURN_VALUE> List<HANDLER>.firstResultOrNull(
    mappingFunction: (HANDLER) -> RETURN_VALUE
) = asSequence()
    .map(mappingFunction)
    .firstOrNull { value -> value != null }
