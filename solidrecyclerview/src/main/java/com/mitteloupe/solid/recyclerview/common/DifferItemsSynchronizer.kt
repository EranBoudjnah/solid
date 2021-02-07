package com.mitteloupe.solid.recyclerview.common

import androidx.recyclerview.widget.AsyncListDiffer
import com.mitteloupe.solid.recyclerview.ItemsSynchronizer

class DifferItemsSynchronizer<ANY>(
    private val differ: AsyncListDiffer<ANY>
) : ItemsSynchronizer<ANY> {
    private val items: MutableList<ANY> = mutableListOf()

    override val itemsSize: Int
        get() = items.size

    override fun getItemAt(position: Int) = items[position]

    override fun setItems(items: List<ANY>) {
        if (items.isEmpty()) {
            differ.submitList(null) {
                this.items.clear()
            }
        } else {
            submitList(ArrayList(items))
        }
    }

    override fun insertItem(position: Int, item: ANY) {
        val items = ArrayList(this.items)
        items.add(position, item)
        submitList(items)
    }

    override fun insertItems(position: Int, items: List<ANY>) {
        val newItems = ArrayList(this.items)
        newItems.addAll(position, items)
        submitList(newItems)
    }

    override fun changeItem(position: Int, item: ANY) {
        val items = ArrayList(this.items)
        items[position] = item
        submitList(items)
    }

    override fun changeItem(position: Int, item: ANY, payload: Any) {
        val items = ArrayList(this.items)
        items[position] = item
        submitList(items)
    }

    override fun changeItems(position: Int, items: List<ANY>) {
        val newItems = ArrayList(this.items)
        items.forEachIndexed { index, item -> newItems[index + position] = item }
        submitList(newItems)
    }

    override fun changeItems(position: Int, items: List<ANY>, payload: Any) {
        val newItems = ArrayList(this.items)
        items.forEachIndexed { index, item -> newItems[index + position] = item }
        submitList(newItems)
    }

    override fun moveItem(fromPosition: Int, toPosition: Int) {
        val items = ArrayList(this.items)
        val item = items.removeAt(fromPosition)
        items.add(toPosition, item)
        submitList(items)
    }

    override fun removeItem(position: Int) {
        val items = ArrayList(this.items)
        items.removeAt(position)
        submitList(items)
    }

    override fun removeItems(position: Int, itemCount: Int) {
        val items = ArrayList(items)
        items.subList(position, position + itemCount).clear()
        submitList(items)
    }

    private fun submitList(items: List<ANY>) {
        differ.submitList(items) {
            this.items.clear()
            this.items.addAll(items)
        }
    }
}
