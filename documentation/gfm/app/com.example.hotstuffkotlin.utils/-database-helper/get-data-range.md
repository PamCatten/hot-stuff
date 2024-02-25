//[app](../../../index.md)/[com.example.hotstuffkotlin.utils](../index.md)/[DatabaseHelper](index.md)/[getDataRange](get-data-range.md)

# getDataRange

[androidJvm]\
fun [getDataRange](get-data-range.md)(offset: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, isSearchQuery: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, searchQuery: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[Item](../../com.example.hotstuffkotlin.models/-item/index.md)&gt;

Retrieve a limited number of saved items from the database.

#### Return

An ArrayList of items to be displayed in the RecyclerView .

#### Author

Cam Patten

#### Parameters

androidJvm

| | |
|---|---|
| offset | The number of rows to be ignored in a result |
| isSearchQuery | Determines if the query needs to be modified to search the NAME, ROOM, CATEGORY, and MAKE columns in the database for values similar to a [searchQuery](get-data-range.md). |
| searchQuery | String used to search for similar values in the database. |
