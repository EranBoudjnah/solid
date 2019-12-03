package com.mitteloupe.solid.service.handler

import android.app.Service
import android.content.Intent
import android.os.IBinder

interface BindingHandler {
    /**
     * @see Service.onBind
     */
    fun onBind(intent: Intent): IBinder? = null

    /**
     * @see Service.onRebind
     */
    fun onRebind(intent: Intent)

    /**
     * @return Return true if you would like to have the service's
     * [onRebind] method later called when new clients bind to it.
     *
     * @see Service.onUnbind
     */
    fun onUnbind(intent: Intent): Boolean
}
