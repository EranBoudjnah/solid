package com.mitteloupe.solid.activity.handler

import android.app.Activity
import android.view.SearchEvent

interface SearchHandler {
    /**
     * @see Activity.onSearchRequested
     */
    fun onSearchRequested(searchEvent: SearchEvent?) = false

    /**
     * @see Activity.onSearchRequested
     */
    fun onSearchRequested() = false
}