package com.mitteloupe.solid.fragment.handler

import androidx.fragment.app.Fragment

interface MemoryHandler {
    /**
     * @see Fragment.onLowMemory
     */
    fun onLowMemory() = Unit
}
