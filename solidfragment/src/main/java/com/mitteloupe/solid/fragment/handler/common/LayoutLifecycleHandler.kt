package com.mitteloupe.solid.fragment.handler.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.mitteloupe.solid.fragment.handler.LifecycleHandler

class LayoutLifecycleHandler(
    @LayoutRes private val layoutResourceId: Int
) : LifecycleHandler {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutResourceId, container, false)
}
