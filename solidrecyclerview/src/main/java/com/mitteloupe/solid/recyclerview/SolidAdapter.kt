package com.mitteloupe.solid.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

private const val ITEM_TYPE_DEFAULT = 0

open class SolidAdapter<VIEW_HOLDER : RecyclerView.ViewHolder, ITEM>
@JvmOverloads constructor(
    private val viewProvider: ViewProvider,
    private val viewHoldersProvider: (View, Int) -> VIEW_HOLDER,
    private val viewBinder: ViewBinder<VIEW_HOLDER, ITEM>,
    private val itemsSynchronizerProvider: (RecyclerView.Adapter<VIEW_HOLDER>) -> ItemsSynchronizer<VIEW_HOLDER, ITEM> =
        { itemsSynchronizer -> SimpleItemsSynchronizer(itemsSynchronizer) },
    private val positionToType: (ItemsSynchronizer<VIEW_HOLDER, ITEM>, Int) -> Int =
        { _, _ -> ITEM_TYPE_DEFAULT }
) : RecyclerView.Adapter<VIEW_HOLDER>() {
    constructor(
        viewProvider: ViewProvider,
        viewHoldersProvider: (View, Int) -> VIEW_HOLDER,
        viewBinder: ViewBinder<VIEW_HOLDER, ITEM>,
        positionToType: (ItemsSynchronizer<VIEW_HOLDER, ITEM>, Int) -> Int
    ) : this(
        viewProvider = viewProvider,
        viewBinder = viewBinder,
        viewHoldersProvider = viewHoldersProvider,
        itemsSynchronizerProvider = { itemsSynchronizer -> SimpleItemsSynchronizer(itemsSynchronizer) },
        positionToType = positionToType
    )

    private val itemsSynchronizer by lazy {
        itemsSynchronizerProvider(this)
    }

    open fun setItems(items: List<ITEM>) {
        itemsSynchronizer.setItems(items)
    }

    fun setItems(vararg items: ITEM) {
        val itemsList = items.asList()
        itemsSynchronizer.setItems(itemsList)
    }

    fun insertItem(position: Int, item: ITEM) {
        itemsSynchronizer.insertItem(position, item)
    }

    fun insertItems(position: Int, items: List<ITEM>) {
        itemsSynchronizer.insertItems(position, items)
    }

    fun changeItem(position: Int, item: ITEM) {
        itemsSynchronizer.changeItem(position, item)
    }

    fun changeItem(position: Int, item: ITEM, payload: Any) {
        itemsSynchronizer.changeItem(position, item, payload)
    }

    fun changeItems(position: Int, items: List<ITEM>) {
        itemsSynchronizer.changeItems(position, items)
    }

    fun changeItems(position: Int, items: List<ITEM>, payload: Any) {
        itemsSynchronizer.changeItems(position, items, payload)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        itemsSynchronizer.moveItem(fromPosition, toPosition)
    }

    fun removeItem(position: Int) {
        itemsSynchronizer.removeItem(position)
    }

    fun removeItems(position: Int, itemCount: Int) {
        itemsSynchronizer.removeItems(position, itemCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIEW_HOLDER {
        val view = viewProvider.getView(parent, viewType)
        return viewHoldersProvider(view, viewType)
    }

    override fun getItemCount() = itemsSynchronizer.itemsSize

    override fun getItemViewType(position: Int) = positionToType(itemsSynchronizer, position)

    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        @Suppress("UNCHECKED_CAST")
        viewBinder.bindView(holder, itemsSynchronizer.getItemAt(position))
    }

    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int, payloads: List<Any>) {
        @Suppress("UNCHECKED_CAST")
        viewBinder.bindView(holder, itemsSynchronizer.getItemAt(position), payloads.toSet())
    }
}
