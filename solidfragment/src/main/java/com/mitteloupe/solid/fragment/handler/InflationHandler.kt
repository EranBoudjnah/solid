package com.mitteloupe.solid.fragment.handler

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment

interface InflationHandler {
    /**
     * @see Fragment.onInflate
     */
    fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?)
}