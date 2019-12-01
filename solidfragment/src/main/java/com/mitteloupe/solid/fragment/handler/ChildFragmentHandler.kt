package com.mitteloupe.solid.fragment.handler

import androidx.fragment.app.Fragment

interface ChildFragmentHandler {
    /**
     * @see Fragment.onAttachFragment
     */
    fun onAttachFragment(childFragment: Fragment)
}