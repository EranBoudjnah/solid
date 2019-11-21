package com.mitteloupe.solid.recyclerview

import android.view.View
import android.view.ViewGroup

interface ViewProvider {
    fun getView(parent: ViewGroup, viewType: Int): View
}
