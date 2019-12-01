package com.mitteloupe.solid.fragment.handler

import androidx.fragment.app.Fragment

interface NavigationHandler {
    /**
     * @see Fragment.onPrimaryNavigationFragmentChanged
     */
    fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean)
}