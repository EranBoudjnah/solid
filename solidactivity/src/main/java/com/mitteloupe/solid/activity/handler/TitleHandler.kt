package com.mitteloupe.solid.activity.handler

import android.app.Activity

interface TitleHandler {
    /**
     * @see Activity.onTitleChanged
     */
    fun onTitleChanged(title: CharSequence, color: Int) = Unit

    /**
     * @see Activity.onChildTitleChanged
     */
    fun onChildTitleChanged(childActivity: Activity, title: CharSequence) = Unit
}