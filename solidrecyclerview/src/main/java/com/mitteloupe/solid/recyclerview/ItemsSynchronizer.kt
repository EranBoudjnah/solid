package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

interface ItemsSynchronizer<VIEW_HOLDER : ViewHolder, ITEM> {
    val itemsSize: Int

    fun getItemAt(position: Int): ITEM

    fun setItems(items: List<ITEM>)

    fun insertItem(position: Int, item: ITEM)

    fun insertItems(position: Int, items: List<ITEM>)

    fun changeItem(position: Int, item: ITEM)

    fun changeItem(position: Int, item: ITEM, payload: Any)

    fun changeItems(position: Int, items: List<ITEM>)

    fun changeItems(position: Int, items: List<ITEM>, payload: Any)

    fun moveItem(fromPosition: Int, toPosition: Int)

    fun removeItem(position: Int)

    fun removeItems(position: Int, itemCount: Int)
}