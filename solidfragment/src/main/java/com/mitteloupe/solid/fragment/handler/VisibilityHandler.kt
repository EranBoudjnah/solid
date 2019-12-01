package com.mitteloupe.solid.fragment.handler

import androidx.fragment.app.Fragment

interface VisibilityHandler {
    /**
     * @see Fragment.onHiddenChanged
     */
    fun onHiddenChanged(hidden: Boolean)
}