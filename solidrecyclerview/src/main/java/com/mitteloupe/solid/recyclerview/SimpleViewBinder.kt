package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView.ViewHolder

abstract class SimpleViewBinder<VIEW_HOLDER : ViewHolder, DATA> : ViewBinder<VIEW_HOLDER, DATA> {
    override fun bindView(viewHolder: VIEW_HOLDER, data: DATA, payloads: Set<Any>) =
        bindView(viewHolder, data)
}
