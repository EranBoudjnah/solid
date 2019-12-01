package com.mitteloupe.solid.fragment.handler

import android.os.Bundle
import androidx.fragment.app.Fragment

interface InstanceStateHandler {
    /**
     * @see Fragment.onSaveInstanceState
     */
    fun onSaveInstanceState(outState: Bundle)
}
