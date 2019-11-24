package com.mitteloupe.solid.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Classes implementing this interface bind the [View]s held by [ViewHolder]s to provided data,
 * optionally taking a collection of change payloads.
 *
 * @See ViewHolder
 * @See SolidAdapter.onBindViewHolder
 * @See Adapter.onBindViewHolder
 */
interface ViewBinder<VIEW_HOLDER : ViewHolder, DATA> {
    /**
     * Binds [data] to the [View]s held by [viewHolder].
     *
     * @See SolidAdapter.onBindViewHolder
     * @See Adapter.onBindViewHolder
     */
    fun bindView(viewHolder: VIEW_HOLDER, data: DATA)

    /**
     * Binds [data] to the [View]s held by [viewHolder], taking into account the changes specified
     * in [payloads].
     *
     * @See SolidAdapter.onBindViewHolder
     * @See Adapter.onBindViewHolder
     */
    fun bindView(viewHolder: VIEW_HOLDER, data: DATA, payloads: Set<Any>)
}
