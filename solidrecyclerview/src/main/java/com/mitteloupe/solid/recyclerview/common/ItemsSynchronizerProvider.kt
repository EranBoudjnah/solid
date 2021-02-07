package com.mitteloupe.solid.recyclerview.common

import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mitteloupe.solid.recyclerview.ItemsSynchronizer

open class ItemsSynchronizerProvider<VIEW_HOLDER : ViewHolder, DATA>(
    private val asyncDifferConfig: AsyncDifferConfig<DATA>
) : (Adapter<VIEW_HOLDER>) -> ItemsSynchronizer<DATA> {
    private lateinit var adapterListUpdateCallback: AdapterListUpdateCallback
    private lateinit var differItemsSynchronizer: DifferItemsSynchronizer<DATA>

    override fun invoke(adapter: Adapter<VIEW_HOLDER>): ItemsSynchronizer<DATA> {
        adapterListUpdateCallback = AdapterListUpdateCallback(adapter)
        differItemsSynchronizer =
            DifferItemsSynchronizer<DATA>(
                AsyncListDiffer(adapterListUpdateCallback, asyncDifferConfig)
            )
        return differItemsSynchronizer
    }
}
