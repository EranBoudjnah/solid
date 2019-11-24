package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * A simplified [ViewBinder], ignoring any payloads when binding data.
 *
 * @see ViewBinder
 */
abstract class SimpleViewBinder<VIEW_HOLDER : ViewHolder, DATA> : ViewBinder<VIEW_HOLDER, DATA> {
    /**
     * Delegates any calls providing [payloads] to the [ViewBinder.bindView] implementation that does
     * not take [payloads] into account when binding.
     *
     * @see ViewBinder.bindView
     */
    override fun bindView(viewHolder: VIEW_HOLDER, data: DATA, payloads: Set<Any>) =
        bindView(viewHolder, data)
}
