package com.mitteloupe.solid.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

open class InflatedViewProvider(
    private val layoutInflater: LayoutInflater,
    private val viewTypeToLayoutResourceId: (Int) -> Int
) : ViewProvider {
    constructor(
        layoutInflater: LayoutInflater,
        @LayoutRes layoutResourceId: Int
    ) : this(layoutInflater, { layoutResourceId })

    override fun getView(parent: ViewGroup, viewType: Int): View =
        layoutInflater.inflate(viewTypeToLayoutResourceId(viewType), parent, false)
}
