# SOLID Components

SOLID Compontents are an attempt at following good engineering standards and best practices such as [SOLID](https://en.wikipedia.org/wiki/SOLID) and [DRY](https://en.wikipedia.org/wiki/Don%27t_repeat_yourself) where Google neglected to.

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
    implementation "com.mitteloupe.solid:solidrecyclerview:1.0.0"
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

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)