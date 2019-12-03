package com.mitteloupe.solid.service.handler

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import androidx.annotation.IntDef

const val START_DEFAULT_BEHAVIOUR = -1

@IntDef(
    flag = true,
    value = [Service.START_FLAG_REDELIVERY, Service.START_FLAG_RETRY]
)
@Retention(AnnotationRetention.SOURCE)
annotation class StartArgFlags

@IntDef(
    flag = false,
    value = [Service.START_STICKY_COMPATIBILITY, Service.START_STICKY, Service.START_NOT_STICKY, Service.START_REDELIVER_INTENT, START_DEFAULT_BEHAVIOUR]
)
@Retention(AnnotationRetention.SOURCE)
annotation class StartResult

interface LifecycleHandler {
    /**
     * @see Service.onCreate
     */
    fun onCreate() = Unit

    /**
     * @see Service.onStartCommand
     */
    @SuppressLint("WrongConstant")
    @StartResult
    fun onStartCommand(intent: Intent?, @StartArgFlags flags: Int, startId: Int): Int =
        START_DEFAULT_BEHAVIOUR

    /**
     * @see Service.onDestroy
     */
    fun onDestroy() = Unit
}
