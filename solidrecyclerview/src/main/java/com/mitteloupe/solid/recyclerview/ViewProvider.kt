package com.mitteloupe.solid.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Implement this interface to provide [View]s to the SolidAdapter to assign to [ViewHolder]s.
 *
 * @see SolidAdapter.onBindViewHolder
 * @see Adapter.onBindViewHolder
 */
interface ViewProvider {
    /**
     * Provides a [View] in the context of [parent] for the given [viewType]
     * (@see [SolidAdapter.getItemViewType]).
     */
    fun getView(parent: ViewGroup, viewType: Int): View
}
