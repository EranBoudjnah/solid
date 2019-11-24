package com.mitteloupe.solid.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView.Adapter

/**
 * A concrete implementation of [ViewProvider] that covers the common case of providing [View]s by
 * inflating layouts from a resource IDs.
 *
 * If supporting multiple view types (see [SolidAdapter.getItemViewType]),
 * [viewTypeToLayoutResourceId] can be provided to map view types to resource IDs.
 * Otherwise, a single layout resource ID can be provided using the convenience constructor
 * InflatedViewProvider(LayoutInflater, Int).
 *
 * @see ViewProvider
 */
open class InflatedViewProvider(
    private val layoutInflater: LayoutInflater,
    private val viewTypeToLayoutResourceId: (Int) -> Int
) : ViewProvider {
    constructor(
        layoutInflater: LayoutInflater,
        @LayoutRes layoutResourceId: Int
    ) : this(layoutInflater, { layoutResourceId })

    /**
     * Inflates a view from the resource ID provided by [viewTypeToLayoutResourceId] for the given
     * [viewType].
     *
     * @see SolidAdapter.onCreateViewHolder
     * @see Adapter.onCreateViewHolder
     */
    override fun getView(parent: ViewGroup, viewType: Int): View =
        layoutInflater.inflate(viewTypeToLayoutResourceId(viewType), parent, false)
}
