package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.Menu

interface KeyHandler {
    /**
     * Called when a key was pressed down and not handled by any of the views
     * inside of the activity.
     *
     * @see Activity.onKeyDown
     */
    fun onKeyDown(keyCode: Int, event: KeyEvent) = false

    /**
     * @see Activity.onKeyLongPress
     */
    fun onKeyLongPress(keyCode: Int, event: KeyEvent) = false

    /**
     * @see Activity.onKeyShortcut
     */
    fun onKeyShortcut(keyCode: Int, event: KeyEvent) = false

    /**
     * @see Activity.onKeyMultiple
     */
    fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent) = false

    /**
     * @see Activity.onKeyUp
     */
    fun onKeyUp(keyCode: Int, event: KeyEvent) = false

    /**
     * @see Activity.onProvideKeyboardShortcuts
     */
    fun onProvideKeyboardShortcuts(
        data: MutableList<KeyboardShortcutGroup>?,
        menu: Menu?,
        deviceId: Int
    ) = Unit
}