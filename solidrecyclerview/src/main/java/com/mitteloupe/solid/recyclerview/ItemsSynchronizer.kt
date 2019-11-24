package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

/**
 * Classes implementing this interface are responsible for updating a collection of items represented
 * by a [RecyclerView] and notifying an [Adapter] when those changes occur. The functions in this
 * interface mirror the different notify functions available on [Adapter]s. That is:
 *
 * [Adapter.notifyDataSetChanged]
 * [Adapter.notifyItemInserted]
 * [Adapter.notifyItemMoved]
 * [Adapter.notifyItemRangeChanged]
 * [Adapter.notifyItemRangeInserted]
 * [Adapter.notifyItemRangeRemoved]
 * [Adapter.notifyItemRemoved]
 */
interface ItemsSynchronizer<ITEM> {
    val itemsSize: Int

    /**
     * Returns the item expected to be displayed in [position].
     */
    fun getItemAt(position: Int): ITEM

    /**
     * Replaces th existing items with the provided [items].
     */
    fun setItems(items: List<ITEM>)

    /**
     * Inserts an [item] at [position].
     *
     * @see Adapter.notifyItemInserted
     */
    fun insertItem(position: Int, item: ITEM)

    /**
     * Inserts [items] at [position].
     *
     * @see Adapter.notifyItemRangeInserted
     */
    fun insertItems(position: Int, items: List<ITEM>)

    /**
     * Replaces an [item] at [position].
     *
     * @see Adapter.notifyItemChanged
     */
    fun changeItem(position: Int, item: ITEM)

    /**
     * Replaces an [item] at [position]. The [payload] specifies the nature of the change.
     *
     * @see Adapter.notifyItemChanged
     */
    fun changeItem(position: Int, item: ITEM, payload: Any)

    /**
     * Replaces the items starting at [position] with the same number of [items].
     *
     * @see Adapter.notifyItemRangeChanged
     */
    fun changeItems(position: Int, items: List<ITEM>)

    /**
     * Replaces the items starting at [position] with the same number of [items]. The [payload]
     * specifies the nature of the change.
     *
     * @see Adapter.notifyItemRangeChanged
     */
    fun changeItems(position: Int, items: List<ITEM>, payload: Any)

    /**
     * Moves an item from [fromPosition] to [toPosition].
     *
     * @see Adapter.notifyItemMoved
     */
    fun moveItem(fromPosition: Int, toPosition: Int)

    /**
     * Removes an item from [position].
     */
    fun removeItem(position: Int)

    /**
     * Removes [itemCount] items starting from [position].
     */
    fun removeItems(position: Int, itemCount: Int)
}
