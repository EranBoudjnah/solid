package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle

interface LifecycleHandler {
    /**
     * @see Activity.onCreate
     */
    fun onCreate(savedInstanceState: Bundle?) = Unit

    /**
     * @see Activity.onPostCreate
     */
    fun onPostCreate(savedInstanceState: Bundle?) = Unit

    /**
     * @see Activity.onCreate
     */
    fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) = Unit

    /**
     * @see Activity.onPostCreate
     */
    fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) = Unit

    /**
     * @see Activity.onStart
     */
    fun onStart() = Unit

    /**
     * @see Activity.onResume
     */
    fun onResume() = Unit

    /**
     * @see Activity.onPostResume
     */
    fun onPostResume() = Unit

    /**
     * @see Activity.onUserLeaveHint
     */
    fun onUserLeaveHint() = Unit

    /**
     * @see Activity.onPause
     */
    fun onPause() = Unit

    /**
     * @see Activity.onStop
     */
    fun onStop() = Unit

    /**
     * @see Activity.onDestroy
     */
    fun onDestroy() = Unit

    /**
     * @see Activity.onRestart
     */
    fun onRestart() = Unit
}