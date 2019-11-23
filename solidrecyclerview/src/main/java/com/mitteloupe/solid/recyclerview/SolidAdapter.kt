package com.mitteloupe.solid.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

private const val ITEM_TYPE_DEFAULT = 0

/**
 * SolidAdapters are implementations of [Adapter] which are meant to be set as-is to [RecyclerView]s.
 * Instead of changing the behaviour of a SolidAdapter, inject the required behaviour via the
 * relevant dependency.
 *
 * [viewHoldersProvider] is used to construct [ViewHolder]s. The adapter passes two
 * arguments to the [viewHoldersProvider]: a [View] and a view type (see [SolidAdapter.getItemViewType]).
 *
 * The [View] for the [ViewHolder] is provided by the [viewProvider].
 *
 * [itemsSynchronizerProvider] can be used to provide different behaviours when the data set is
 * accessed. The [ItemsSynchronizer] will need a reference to the Adapter to notify it on data
 * changes. Because a properly constructed instance of the Adapter may not available in construction
 * time, a provider is used. By default, a [SimpleItemsSynchronizer] is provided.
 *
 * [positionToType] can be used to map an item (retrieved from the itemsSynchronizer provided by
 * [itemsSynchronizerProvider]) at a given position to a view type (see [SolidAdapter.getItemViewType]).
 *
 * @see Adapter
 * @see ItemsSynchronizer
 * @see ViewBinder
 * @see ViewHolder
 * @see ViewProvider
 *
 * @param viewProvider              See [ViewProvider]
 * @param viewHoldersProvider       See above
 * @param viewBinder                See [ViewBinder]
 * @param itemsSynchronizerProvider See above and [ItemsSynchronizer]
 * @param positionToType            See above
 */
open class SolidAdapter<VIEW_HOLDER : ViewHolder, ITEM>
@JvmOverloads constructor(
    private val viewProvider: ViewProvider,
    private val viewHoldersProvider: (View, Int) -> VIEW_HOLDER,
    private val viewBinder: ViewBinder<VIEW_HOLDER, ITEM>,
    private val itemsSynchronizerProvider: (Adapter<VIEW_HOLDER>) -> ItemsSynchronizer<VIEW_HOLDER, ITEM> =
        { itemsSynchronizer -> SimpleItemsSynchronizer(itemsSynchronizer) },
    private val positionToType: (ItemsSynchronizer<VIEW_HOLDER, ITEM>, Int) -> Int =
        { _, _ -> ITEM_TYPE_DEFAULT }
) : Adapter<VIEW_HOLDER>() {
    /**
     * A SolidAdapter constructor without an itemsSynchronizerProvider parameter.
     * Calls {@link  }
     */
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

    /**
     * Delegates setting of items to [ItemsSynchronizer.setItems].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    open fun setItems(items: List<ITEM>) {
        itemsSynchronizer.setItems(items)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.setItems].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun setItems(vararg items: ITEM) {
        val itemsList = items.asList()
        itemsSynchronizer.setItems(itemsList)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.insertItem].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun insertItem(position: Int, item: ITEM) {
        itemsSynchronizer.insertItem(position, item)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.insertItems].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun insertItems(position: Int, items: List<ITEM>) {
        itemsSynchronizer.insertItems(position, items)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.changeItem].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun changeItem(position: Int, item: ITEM) {
        itemsSynchronizer.changeItem(position, item)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.changeItem].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun changeItem(position: Int, item: ITEM, payload: Any) {
        itemsSynchronizer.changeItem(position, item, payload)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.changeItems].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun changeItems(position: Int, items: List<ITEM>) {
        itemsSynchronizer.changeItems(position, items)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.changeItems].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun changeItems(position: Int, items: List<ITEM>, payload: Any) {
        itemsSynchronizer.changeItems(position, items, payload)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.moveItem].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun moveItem(fromPosition: Int, toPosition: Int) {
        itemsSynchronizer.moveItem(fromPosition, toPosition)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.removeItem].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun removeItem(position: Int) {
        itemsSynchronizer.removeItem(position)
    }

    /**
     * Delegates setting of items to [ItemsSynchronizer.removeItems].
     *
     * This is one of a set of functions that mirror the different notification functions provided
     * by [Adapter].
     */
    fun removeItems(position: Int, itemCount: Int) {
        itemsSynchronizer.removeItems(position, itemCount)
    }

    /**
     * Obtains a [View] from [viewProvider] and returns the [ViewHolder] wrapping that [View] provided
     * by [viewHoldersProvider].
     *
     * @see [Adapter.onCreateViewHolder]
     * @see [SolidAdapter.getItemViewType]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VIEW_HOLDER {
        val view = viewProvider.getView(parent, viewType)
        return viewHoldersProvider(view, viewType)
    }

    /**
     * Returns the itemsSize provided by [itemsSynchronizer].
     */
    override fun getItemCount() = itemsSynchronizer.itemsSize

    /**
     * To determine the type of a [View] at a given [position], a call is made to [positionToType].
     */
    override fun getItemViewType(position: Int) = positionToType(itemsSynchronizer, position)

    /**
     * Binds a view provided by the [holder] to an item provided by [itemsSynchronizer] at a given
     * [position].
     */
    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int) {
        viewBinder.bindView(holder, itemsSynchronizer.getItemAt(position))
    }

    /**
     * Binds a view provided by the [holder] to an item provided by [itemsSynchronizer] at a given
     * [position] with given [payloads].
     */
    override fun onBindViewHolder(holder: VIEW_HOLDER, position: Int, payloads: List<Any>) {
        viewBinder.bindView(holder, itemsSynchronizer.getItemAt(position), payloads.toSet())
    }
}
