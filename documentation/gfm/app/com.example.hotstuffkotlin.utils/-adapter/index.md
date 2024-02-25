//[app](../../../index.md)/[com.example.hotstuffkotlin.utils](../index.md)/[Adapter](index.md)

# Adapter

[androidJvm]\
class [Adapter](index.md)(items: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Item](../../com.example.hotstuffkotlin.models/-item/index.md)&gt;) : [RecyclerView.Adapter](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.Adapter.html)&lt;[Adapter.ViewHolder](-view-holder/index.md)&gt;

## Constructors

| | |
|---|---|
| [Adapter](-adapter.md) | [androidJvm]<br>constructor(items: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[Item](../../com.example.hotstuffkotlin.models/-item/index.md)&gt;) |

## Types

| Name | Summary |
|---|---|
| [OnItemClickListener](-on-item-click-listener/index.md) | [androidJvm]<br>interface [OnItemClickListener](-on-item-click-listener/index.md) |
| [ViewHolder](-view-holder/index.md) | [androidJvm]<br>class [ViewHolder](-view-holder/index.md)(itemView: [View](https://developer.android.com/reference/kotlin/android/view/View.html), clickListener: [Adapter.OnItemClickListener](-on-item-click-listener/index.md)) : [RecyclerView.ViewHolder](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView.ViewHolder.html) |

## Functions

| Name | Summary |
|---|---|
| [getItemCount](get-item-count.md) | [androidJvm]<br>open override fun [getItemCount](get-item-count.md)(): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) |
| [onBindViewHolder](on-bind-view-holder.md) | [androidJvm]<br>open override fun [onBindViewHolder](on-bind-view-holder.md)(holder: [Adapter.ViewHolder](-view-holder/index.md), position: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [onCreateViewHolder](on-create-view-holder.md) | [androidJvm]<br>open override fun [onCreateViewHolder](on-create-view-holder.md)(parent: [ViewGroup](https://developer.android.com/reference/kotlin/android/view/ViewGroup.html), viewType: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)): [Adapter.ViewHolder](-view-holder/index.md) |
| [searchClear](search-clear.md) | [androidJvm]<br>fun [searchClear](search-clear.md)()<br>Clear the stored items array in the [Adapter](index.md) and notify [ViewHolder](-view-holder/index.md). |
| [searchInsert](search-insert.md) | [androidJvm]<br>fun [searchInsert](search-insert.md)(insertPosition: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), updatedItemsArray: [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[Item](../../com.example.hotstuffkotlin.models/-item/index.md)&gt;)<br>Insert search result to the stored items array in the [Adapter](index.md) and notify [ViewHolder](-view-holder/index.md). |
| [setOnItemClickListener](set-on-item-click-listener.md) | [androidJvm]<br>fun [setOnItemClickListener](set-on-item-click-listener.md)(itemListener: [Adapter.OnItemClickListener](-on-item-click-listener/index.md)) |
