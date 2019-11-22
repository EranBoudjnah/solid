package com.mitteloupe.solidcomponents.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.recyclerview.InflatedViewProvider
import com.mitteloupe.solid.recyclerview.SimpleViewBinder
import com.mitteloupe.solidcomponents.R
import com.mitteloupe.solidcomponents.model.MoodUiModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_mood.mood_icon as layoutIconView
import kotlinx.android.synthetic.main.item_mood.mood_title as layoutTitleView

class MoodViewProvider(
    layoutInflater: LayoutInflater
) : InflatedViewProvider(layoutInflater, R.layout.item_mood)

class MoodViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    val iconView: ImageView = layoutIconView
    val titleView: TextView = layoutTitleView
}

class MoodViewBinder(
    private val context: Context
) : SimpleViewBinder<MoodViewHolder, MoodUiModel>() {
    override fun bindView(viewHolder: MoodViewHolder, data: MoodUiModel) {
        viewHolder.iconView.setImageDrawable(
            AppCompatResources.getDrawable(context, data.iconResourceId)
        )
        viewHolder.titleView.text = data.title
    }
}
