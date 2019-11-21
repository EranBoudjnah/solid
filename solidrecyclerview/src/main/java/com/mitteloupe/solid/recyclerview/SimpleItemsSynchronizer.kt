package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class SimpleItemsSynchronizer<VIEW_HOLDER : ViewHolder, ITEM>(
    private val adapter: Adapter<VIEW_HOLDER>
) : ItemsSynchronizer<VIEW_HOLDER, ITEM> {
    private val items: MutableList<ITEM> = mutableListOf()

    override val itemsSize: Int
        get() = items.size

    override fun getItemAt(position: Int) = items[position]

    override fun setItems(items: List<ITEM>) {
        val oldItemsSize = itemsSize
        this.items.clear()
        this.items.addAll(items)
        adapter.notifyItemRangeRemoved(0, oldItemsSize)
        adapter.notifyItemRangeInserted(0, itemsSize)
    }

    override fun insertItem(position: Int, item: ITEM) {
        items.add(position, item)
        adapter.notifyItemInserted(position)
    }

    override fun insertItems(position: Int, items: List<ITEM>) {
        this.items.addAll(position, items)
        adapter.notifyItemRangeInserted(position, items.size)
    }

    override fun changeItem(position: Int, item: ITEM) {
        items[position] = item
        adapter.notifyItemChanged(position)
    }

    override fun changeItem(position: Int, item: ITEM, payload: Any) {
        items[position] = item
        adapter.notifyItemChanged(position, payload)
    }

    override fun changeItems(position: Int, items: List<ITEM>) {
        items.forEachIndexed { index, item -> this.items[index + position] = item }
        adapter.notifyItemRangeChanged(position, items.size)
    }

    override fun changeItems(position: Int, items: List<ITEM>, payload: Any) {
        items.forEachIndexed { index, item -> this.items[index + position] = item }
        adapter.notifyItemRangeChanged(position, items.size, payload)
    }

    override fun moveItem(fromPosition: Int, toPosition: Int) {
        val item = items.removeAt(fromPosition)
        items.add(toPosition, item)
        adapter.notifyItemMoved(fromPosition, toPosition)
    }

    override fun removeItem(position: Int) {
        items.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    override fun removeItems(position: Int, itemCount: Int) {
        items.subList(position, position + itemCount).clear()
        adapter.notifyItemRangeRemoved(position, itemCount)
    }
}