# SOLID Components

[![Version - Application](https://img.shields.io/maven-central/v/com.mitteloupe.solid/solidapplication?label=application+|+MavenCentral)](https://mvnrepository.com/artifact/com.mitteloupe.solid/solidapplication)

[![Version - Activity](https://img.shields.io/maven-central/v/com.mitteloupe.solid/solidactivity?label=activity+|+MavenCentral)](https://mvnrepository.com/artifact/com.mitteloupe.solid/solidactivity)
[![Version - Fragment](https://img.shields.io/maven-central/v/com.mitteloupe.solid/solidfragment?label=fragment+|+MavenCentral)](https://mvnrepository.com/artifact/com.mitteloupe.solid/solidfragment)
[![Version - Service](https://img.shields.io/maven-central/v/com.mitteloupe.solid/solidservice?label=service+|+MavenCentral)](https://mvnrepository.com/artifact/com.mitteloupe.solid/solidservice)

[![Version - RecyclerView](https://img.shields.io/maven-central/v/com.mitteloupe.solid/solidrecyclerview?label=recyclerview+|+MavenCentral)](https://mvnrepository.com/artifact/com.mitteloupe.solid/solidrecyclerview)

[![Build Status](https://img.shields.io/travis/EranBoudjnah/Solid)](https://travis-ci.com/EranBoudjnah/solid)
[![License](https://img.shields.io/github/license/EranBoudjnah/Solid)](https://github.com/EranBoudjnah/solid/blob/master/LICENSE)
[![Platform](https://img.shields.io/badge/platform-android-lightgrey)](https://developer.android.com/reference)

SOLID Components are an attempt at following good engineering standards and best practices such as [SOLID](https://en.wikipedia.org/wiki/SOLID) and [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself) where Google neglected to.

![Icon](https://github.com/EranBoudjnah/solid/raw/master/assets/RockSolid.png)

## Installation

And the implementation dependencies below. You can add just the components you need.

```groovy
dependencies {
    implementation "com.mitteloupe.solid:solidapplication:1.0.8"

    implementation "com.mitteloupe.solid:solidactivity:1.0.8"
    implementation "com.mitteloupe.solid:solidfragment:1.0.7"
    implementation "com.mitteloupe.solid:solidservice:1.0.8"

    implementation "com.mitteloupe.solid:solidrecyclerview:1.0.5"
}
```


## Usage

### Application

Make your app Application instance extend `SolidApplication`. To implement functionality, simply override
`lifecycleHandlers`, `configurationChangeHandlers` or `memoryHandlers`, providing relevant handlers.


### Activity

Use `SolidActivity` as the parent activity of any activity in your app. Instead of having a *BaseActivity*, you can now provide common activity code by overriding one or more of the handler lists, providing a list of handlers.

Common use cases can include dependency injection, analytics, logging, setting up of ViewHolders. 

A `Koin` injection handler will look as follows:

```kotlin
class KoinActivityScopeHandler(
    private val activity: Activity,
    private val currentScope: Scope
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(activity)
    }
}
```

Implement an activity using the handler as follows:

```kotlin
class MainActivity : SolidActivity() {
    override val lifecycleHandlers = listOf(
        KoinActivityScopeHandler(this, currentScope),
        ...
    )

    ...
}
```

### Fragment

Use `SolidFragment` as the parent fragment of any fragment in your app. Instead of having a *BaseFragment*, you can now provide common fragment code by overriding one or more of the handler lists, providing a list of handlers.

Common use cases can include dependency injection, analytics, logging, setting up of ViewHolders. 

A `Koin` injection handler will look as follows:

```kotlin
class KoinFragmentScopeHandler(
    private val fragment: Fragment,
    private val currentScope: Scope
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        currentScope.declare(fragment)
    }
}
```

Implement an fragment using the handler as follows:

```kotlin
class MainFragment : SolidFragment() {
    override val lifecycleHandlers = listOf(
        KoinFragmentScopeHandler(this, currentScope),
        ...
    )

    ...
}
```

### Service

`SolidService` allows composing services instead of inheriting from base services.

As an example, an `IntentService` using `SolidService` would look like this:

```kotlin
class SolidIntentService : SolidService() {
    override val lifecycleHandlers = listOf(
        IntentHandler(this, { intent -> handleIntent(intent) })
    )

    private fun handleIntent(intent: Intent?) {
        ...
    }
}
```

### Adapter

Instead of setting a `RecyclerView.Adapter` to your RecyclerView, simply set a `SolidAdapter`.

`SolidAdapter` has a few constructor-injected dependencies that define its behaviour:

1. `ViewProvider` - this will provide child Views for your RecyclerView. A handy `InflatedViewProvider` is available for simple layout inflation.

2. viewHolderProvider - this is a lambda that, given a View, returns a ViewHolder. It is worth noting that when using a `SolidAdapter`, ViewHolder do just that. They hold references to Views (commonly obtained by calling `findViewById()`). This is their sole responsibility.

3. `ViewBinder` - this will bind a data item to views provided by a ViewHolder.

4. itemsSynchronizerProvider - this is a lambda that, given a `RecyclerView.Adapter`, returns an `ItemsSynchronizer`. The responsibility of `ItemsSynchronizer` is to hold the data items and synchronize changes with the `RecyclerView.Adapter`. If not provided, the `SolidAdapter` uses a default `SimpleItemsSynchronizer`, which provides most common functionality.

5. positionToType - this is a lambda that, given an `ItemsSynchronizer` instance and a position, returns the view type for that position. By default, it always returns `ITEM_TYPE_DEFAULT`.

#### Comparison

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
