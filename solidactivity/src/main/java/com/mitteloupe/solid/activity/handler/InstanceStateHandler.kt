package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle

interface InstanceStateHandler {
    /**
     * @see Activity.onSaveInstanceState
     */
    fun onSaveInstanceState(outState: Bundle) = Unit

    /**
     * @see Activity.onSaveInstanceState
     */
    fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) = Unit

    /**
     * @see Activity.onRestoreInstanceState
     */
    fun onRestoreInstanceState(savedInstanceState: Bundle) = Unit

    /**
     * @see Activity.onRestoreInstanceState
     */
    fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) = Unit
}
