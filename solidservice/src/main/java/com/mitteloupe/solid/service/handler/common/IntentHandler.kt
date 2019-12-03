package com.mitteloupe.solid.service.handler.common

import android.app.IntentService
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
import androidx.annotation.WorkerThread
import com.mitteloupe.solid.service.handler.LifecycleHandler

/**
 * A composite implementation of an [IntentService] via a handler.
 */
class IntentHandler(
    private val service: Service,
    private val onIntentReceived: (intent: Intent?) -> Unit,
    private val debuggingName: String = "Unnamed",
    private val serviceThreadWrapper: ServiceThreadWrapper =
        ServiceThreadWrapper(debuggingName, service, onIntentReceived)
) : LifecycleHandler {
    /**
     * Sets intent redelivery preferences. Usually called from the constructor
     * with your preferred semantics.
     *
     * <p>If enabled is true, [onStartCommand] will return
     * [Service.START_REDELIVER_INTENT], so if this process dies before
     * [intentHandler] is called, the process will be restarted
     * and the intent redelivered.  If multiple Intents have been sent, only
     * the most recent one is guaranteed to be redelivered.
     *
     * <p>If enabled is false (the default),
     * [onStartCommand] will return [Service.START_NOT_STICKY],
     * and if the process dies, the Intent dies along with it.
     */
    var isRedelivery = false

    override fun onCreate() {
        serviceThreadWrapper.setUp()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message: Message = serviceThreadWrapper.obtainMessage().apply {
            arg1 = startId
            obj = intent
        }
        serviceThreadWrapper.sendMessage(message)
        return if (isRedelivery) Service.START_REDELIVER_INTENT else Service.START_NOT_STICKY
    }

    override fun onDestroy() {
        serviceThreadWrapper.quit()
    }
}

class ServiceThreadWrapper(
    private val debuggingName: String,
    private val service: Service,
    private val onIntentReceived: (intent: Intent?) -> Unit,
    private val lazyThread: Lazy<HandlerThread> =
        lazy { HandlerThread("IntentHandler[$debuggingName]") },
    private val serviceHandlerProvider: (Looper, ServiceHandler.Callback) -> ServiceHandler =
        { looper, callback -> ServiceHandler(looper, callback) }
) : ServiceHandler.Callback {
    private val thread by lazy { lazyThread.value }
    private lateinit var serviceLooper: Looper
    private lateinit var serviceHandler: ServiceHandler

    fun setUp() {
        thread.start()

        serviceLooper = thread.looper
        serviceHandler = serviceHandlerProvider(serviceLooper, this)
    }

    fun obtainMessage(): Message = serviceHandler.obtainMessage()

    fun sendMessage(message: Message) = serviceHandler.sendMessage(message)

    fun quit() = serviceLooper.quit()

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call [Service.stopSelf].
     *
     * @param intent The value passed to [Context.startService].
     *               This may be null if the service is being restarted after
     *               its process has gone away; see [Service.onStartCommand]
     *               for details.
     */
    @WorkerThread
    override fun handleIntent(intent: Intent?) = onIntentReceived(intent)

    override fun stopService() = service.stopSelf()
}

class ServiceHandler(
    looper: Looper,
    private val callback: Callback
) : Handler(looper) {

    override fun handleMessage(message: Message) {
        callback.handleIntent(message.obj as? Intent)
        callback.stopService()
    }

    interface Callback {
        fun handleIntent(intent: Intent?)
        fun stopService()
    }
}
