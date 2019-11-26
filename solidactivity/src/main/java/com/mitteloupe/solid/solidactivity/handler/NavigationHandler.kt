package com.mitteloupe.solid.solidactivity.handler

import android.app.Activity
import android.app.TaskStackBuilder
import android.net.Uri

interface NavigationHandler {
    /**
     * @return true if intercepting back press behaviour, false otherwise
     *
     * @see Activity.onBackPressed
     */
    fun onBackPressed() = false

    /**
     * @return true if intercepting up navigation behaviour, false otherwise
     *
     * @see Activity.onNavigateUp
     */
    fun onNavigateUp() = false

    /**
     * @return true if intercepting up navigation from child behaviour, false otherwise
     *
     * @see Activity.onNavigateUpFromChild
     */
    fun onNavigateUpFromChild(child: Activity) = false

    /**
     * @see Activity.onPrepareNavigateUpTaskStack
     */
    fun onPrepareNavigateUpTaskStack(builder: TaskStackBuilder) = Unit

    /**
     * @see Activity.onCreateNavigateUpTaskStack
     */
    fun onCreateNavigateUpTaskStack(builder: TaskStackBuilder) = Unit

    /**
     * @see Activity.onProvideReferrer
     */
    fun onProvideReferrer(): Uri? = null
}