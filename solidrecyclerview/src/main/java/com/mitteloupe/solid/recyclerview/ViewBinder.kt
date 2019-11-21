package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView

interface ViewBinder<VIEW_HOLDER : RecyclerView.ViewHolder, DATA> {
    fun bindView(viewHolder: VIEW_HOLDER, data: DATA)
    fun bindView(viewHolder: VIEW_HOLDER, data: DATA, payloads: Set<Any>)
}
