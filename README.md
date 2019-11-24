# SOLID Components

[![Build Status](https://img.shields.io/travis/EranBoudjnah/Solid)](https://travis-ci.com/EranBoudjnah/Solid)
[![Version](https://img.shields.io/bintray/v/shadowcra/Solid/com.mittelouple.solidcomponents)](https://bintray.com/shadowcra/Solid/com.mittelouple.solidcomponents)
[![License](https://img.shields.io/github/license/EranBoudjnah/Solid)](http://cocoapods.org/pods/MTCircularSlider)
[![Platform](https://img.shields.io/badge/platform-android-lightgrey)](https://developer.android.com/reference)

SOLID Components are an attempt at following good engineering standards and best practices such as [SOLID](https://en.wikipedia.org/wiki/SOLID) and [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself) where Google neglected to.

![Icon](https://github.com/EranBoudjnah/solid/raw/master/assets/RockSolid.png)

## Installation

Add the maven repository to your `build.gradle` file.

```groovy
repositories {
    maven {
        url "https://dl.bintray.com/shadowcra/Solid"
    }
}
```

And then the implementation.

```groovy
dependencies {
    implementation "com.mitteloupe.solid:solidrecyclerview:1.0.2"
}
```


## Usage

Instead of setting a `RecyclerView.Adapter` to your RecyclerView, simply set a `SolidAdapter`.

`SolidAdapter` has a few constructor-injected dependencies that define its behaviour:

1. `ViewProvider` - this will provide child Views for your RecyclerView. A handy `InflatedViewProvider` is available for simple layout inflation.

2. viewHolderProvider - this is a lambda that, given a View, returns a ViewHolder. It is worth noting that when using a `SolidAdapter`, ViewHolder do just that. They hold references to Views (commonly obtained by calling `findViewById()`). This is their sole responsibility.

3. `ViewBinder` - this will bind a data item to views provided by a ViewHolder.

4. itemsSynchronizerProvider - this is a lambda that, given a `RecyclerView.Adapter`, returns an `ItemsSynchronizer`. The responsibility of `ItemsSynchronizer` is to hold the data items and synchronize changes with the `RecyclerView.Adapter`. If not provided, the `SolidAdapter` uses a default `SimpleItemsSynchronizer`, which provides most common functionality.

5. positionToType - this is a lambda that, given an `ItemsSynchronizer` instance and a position, returns the view type for that position. By default, it always returns `ITEM_TYPE_DEFAULT`.

## Comparison

Let's take a look at a simple, common RecyclerView.Adapter implementation:

Without SolidAdapter:

```kotlin
class MoodViewHolder(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    val iconView: ImageView = layoutIconView
    val titleView: TextView = layoutTitleView

    override fun bindData(moodItem: MoodUiModel) {
        iconView.setImageDrawable(
            AppCompatResources.getDrawable(context, data.iconResourceId)
        )
        indexView.text = data.title
    }
}

class ListItemsAdapter(
    private val layoutInflater: LayoutInflater
) : RecyclerView.Adapter<MoodViewHolder>() {
    private val listData = mutableListOf<MoodUiModel>()

    fun setData(listData: List<MoodUiModel>) {
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(position: Int, item: ListItemUiModel) {
        listData.add(position, item)
        notifyItemInserted(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = layoutInflater.inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun getItemCount() = listData.size

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bindData(listData[position])
    }
}

val adapter = ListItemsAdapter(layoutInflater)
```

With SolidAdapter:

```kotlin
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
        viewHolder.indexView.text = data.title
    }
}

val adapter = SolidAdapter(
    MoodViewProvider(layoutInflater),
    { view, _ -> MoodViewHolder(view) },
    MoodViewBinder(this)
)
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)