package com.mitteloupe.solidcomponents

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.recyclerview.SolidAdapter
import com.mitteloupe.solid.activity.SolidActivity
import com.mitteloupe.solid.activity.handler.common.LayoutLifecycleHandler
import com.mitteloupe.solidcomponents.adapter.MoodViewHolder
import com.mitteloupe.solidcomponents.hander.KoinActivityScopeHandler
import com.mitteloupe.solidcomponents.model.MoodUiModel
import org.koin.android.scope.currentScope
import kotlinx.android.synthetic.main.activity_main.recycler_view as recyclerView

class MainActivity : SolidActivity() {
    private val solidAdapter: SolidAdapter<MoodViewHolder, MoodUiModel> by currentScope.inject()

    override val lifecycleHandlers = listOf(
        KoinActivityScopeHandler(this, currentScope),
        LayoutLifecycleHandler(this, R.layout.activity_main)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        recyclerView.adapter = solidAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        solidAdapter.setItems(
            MoodUiModel(
                "Great",
                R.drawable.icon_mood_great
            ),
            MoodUiModel(
                "Good",
                R.drawable.icon_mood_good
            ),
            MoodUiModel(
                "OK",
                R.drawable.icon_mood_ok
            ),
            MoodUiModel(
                "Bad",
                R.drawable.icon_mood_bad
            ),
            MoodUiModel(
                "Terrible",
                R.drawable.icon_mood_terrible
            )
        )
    }
}

