package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.ActionMode
import android.view.ActionMode.Callback

interface ActionModeHandler {
    /**
     * @see Activity.onWindowStartingActionMode
     */
    fun onWindowStartingActionMode(callback: Callback): ActionMode? = null

    /**
     * @see Activity.onWindowStartingActionMode
     */
    fun onWindowStartingActionMode(
        callback: Callback,
        type: Int
    ): ActionMode? = null

    /**
     * @see Activity.onActionModeStarted
     */
    fun onActionModeStarted(mode: ActionMode?) {
        // Do nothing. Override to implement functionality.
    }

    /**
     * @see Activity.onActionModeFinished
     */
    fun onActionModeFinished(mode: ActionMode?) {
        // Do nothing. Override to implement functionality.
    }
}