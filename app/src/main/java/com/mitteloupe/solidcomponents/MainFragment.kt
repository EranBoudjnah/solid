package com.mitteloupe.solidcomponents

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.fragment.SolidFragment
import com.mitteloupe.solid.fragment.handler.common.LayoutLifecycleHandler
import com.mitteloupe.solid.recyclerview.SolidAdapter
import com.mitteloupe.solidcomponents.adapter.MoodViewHolder
import com.mitteloupe.solidcomponents.hander.KoinFragmentScopeHandler
import com.mitteloupe.solidcomponents.model.MoodUiModel
import org.koin.android.scope.currentScope

class MainFragment : SolidFragment() {
    private val solidAdapter: SolidAdapter<MoodViewHolder, MoodUiModel> by currentScope.inject()

    override val lifecycleHandlers = listOf(
        KoinFragmentScopeHandler(this, currentScope),
        LayoutLifecycleHandler(R.layout.fragment_main)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = requireView().findViewById(R.id.recycler_view)
        recyclerView.adapter = solidAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)

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
