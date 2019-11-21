package com.mitteloupe.solidcomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.recyclerview.SolidAdapter
import com.mitteloupe.solidcomponents.adapter.MoodViewBinder
import com.mitteloupe.solidcomponents.adapter.MoodViewHolder
import com.mitteloupe.solidcomponents.adapter.MoodViewProvider
import com.mitteloupe.solidcomponents.model.MoodUiModel
import kotlinx.android.synthetic.main.activity_main.recycler_view as recyclerView

class MainActivity : AppCompatActivity() {
    lateinit var solidAdapter: SolidAdapter<MoodViewHolder, MoodUiModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        solidAdapter = SolidAdapter(
            MoodViewProvider(layoutInflater),
            { view -> MoodViewHolder(view) },
            MoodViewBinder(this)
        )
        recyclerView.adapter = solidAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        solidAdapter.setItems(
            MoodUiModel("Great", R.drawable.icon_mood_great),
            MoodUiModel("Good", R.drawable.icon_mood_good),
            MoodUiModel("OK", R.drawable.icon_mood_ok),
            MoodUiModel("Bad", R.drawable.icon_mood_bad),
            MoodUiModel("Terrible", R.drawable.icon_mood_terrible)
        )
    }
}
