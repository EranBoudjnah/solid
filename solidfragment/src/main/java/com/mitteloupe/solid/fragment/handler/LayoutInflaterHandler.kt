package com.mitteloupe.solid.fragment.handler

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

interface LayoutInflaterHandler {
    /**
     * @see Fragment.onGetLayoutInflater
     */
    fun onGetLayoutInflater(savedInstanceState: Bundle?): LayoutInflater
}