//[app](../../../index.md)/[com.example.hotstuffkotlin.utils](../index.md)/[DatabaseHelper](index.md)

# DatabaseHelper

class [DatabaseHelper](index.md)(val context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) : [SQLiteOpenHelper](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteOpenHelper.html)

Create a helper object to perform basic CRUD operations with SQL on the database.

#### Author

Cam Patten

#### Parameters

androidJvm

| | |
|---|---|
| context | Used to push to and receive information from the application environment. |

## Constructors

| | |
|---|---|
| [DatabaseHelper](-database-helper.md) | [androidJvm]<br>constructor(context: [Context](https://developer.android.com/reference/kotlin/android/content/Context.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Properties

| Name | Summary |
|---|---|
| [context](context.md) | [androidJvm]<br>val [context](context.md): [Context](https://developer.android.com/reference/kotlin/android/content/Context.html) |

## Functions

| Name | Summary |
|---|---|
| [addBuilding](add-building.md) | [androidJvm]<br>fun [addBuilding](add-building.md)(building: [Building](../../com.example.hotstuffkotlin.models/-building/index.md))<br>Add the passed building to the database and toast the result (whether success/failure). |
| [addItem](add-item.md) | [androidJvm]<br>fun [addItem](add-item.md)(item: [Item](../../com.example.hotstuffkotlin.models/-item/index.md))<br>Add the passed item to the database and toast the result (whether success/failure). |
| [close](index.md#-1132609887%2FFunctions%2F-912451524) | [androidJvm]<br>open override fun [close](index.md#-1132609887%2FFunctions%2F-912451524)() |
| [deleteBuilding](delete-building.md) | [androidJvm]<br>fun [deleteBuilding](delete-building.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Delete the row of the passed building id in the database and toast the result (whether success/failure). |
| [deleteItem](delete-item.md) | [androidJvm]<br>fun [deleteItem](delete-item.md)(id: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html))<br>Delete the row of the passed item id in the database and toast the result (whether success/failure). |
| [exportCSV](export-c-s-v.md) | [androidJvm]<br>fun [exportCSV](export-c-s-v.md)()<br>Write item records to .CSV file, export to device downloads directory and toast result (whether success/failure). |
| [getCategoryQuantity](get-category-quantity.md) | [androidJvm]<br>fun [getCategoryQuantity](get-category-quantity.md)(): [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;&gt;<br>Retrieve ArrayLists of category names saved in the database and the total item quantity of each category. |
| [getDatabaseName](index.md#2092117245%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getDatabaseName](index.md#2092117245%2FFunctions%2F-912451524)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html) |
| [getDataRange](get-data-range.md) | [androidJvm]<br>fun [getDataRange](get-data-range.md)(offset: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html) = 0, isSearchQuery: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html) = false, searchQuery: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)? = null): [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[Item](../../com.example.hotstuffkotlin.models/-item/index.md)&gt;<br>Retrieve a limited number of saved items from the database. |
| [getReadableDatabase](index.md#725964472%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getReadableDatabase](index.md#725964472%2FFunctions%2F-912451524)(): [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html) |
| [getRoomValue](get-room-value.md) | [androidJvm]<br>fun [getRoomValue](get-room-value.md)(): [Pair](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-pair/index.html)&lt;[ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;, [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)&lt;[Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-float/index.html)&gt;&gt;<br>Retrieve ArrayLists of room names saved in the database and the total item quantity of each room. |
| [getTotalQuantity](get-total-quantity.md) | [androidJvm]<br>fun [getTotalQuantity](get-total-quantity.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Sums the quantity column of the ITEM table and returns the total as a formatted string. |
| [getTotalValue](get-total-value.md) | [androidJvm]<br>fun [getTotalValue](get-total-value.md)(): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>Sum the multiplied product of the quantity and value columns from the ITEM table and return the total as a formatted string. |
| [getWritableDatabase](index.md#-1273172728%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [getWritableDatabase](index.md#-1273172728%2FFunctions%2F-912451524)(): [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html) |
| [onConfigure](index.md#-1648481475%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onConfigure](index.md#-1648481475%2FFunctions%2F-912451524)(p0: [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html)) |
| [onCreate](on-create.md) | [androidJvm]<br>open override fun [onCreate](on-create.md)(db: [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html)?) |
| [onDowngrade](index.md#-995824338%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onDowngrade](index.md#-995824338%2FFunctions%2F-912451524)(p0: [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p2: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [onOpen](index.md#-653481789%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [onOpen](index.md#-653481789%2FFunctions%2F-912451524)(p0: [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html)) |
| [onUpgrade](on-upgrade.md) | [androidJvm]<br>open override fun [onUpgrade](on-upgrade.md)(db: [SQLiteDatabase](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.html)?, oldVersion: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), newVersion: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setIdleConnectionTimeout](index.md#1030004133%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [~~setIdleConnectionTimeout~~](index.md#1030004133%2FFunctions%2F-912451524)(p0: [Long](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)) |
| [setLookasideConfig](index.md#-1601100201%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setLookasideConfig](index.md#-1601100201%2FFunctions%2F-912451524)(p0: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html), p1: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)) |
| [setOpenParams](index.md#-627348398%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setOpenParams](index.md#-627348398%2FFunctions%2F-912451524)(p0: [SQLiteDatabase.OpenParams](https://developer.android.com/reference/kotlin/android/database/sqlite/SQLiteDatabase.OpenParams.html)) |
| [setWriteAheadLoggingEnabled](index.md#1941933624%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [setWriteAheadLoggingEnabled](index.md#1941933624%2FFunctions%2F-912451524)(p0: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)) |
| [updateBuilding](update-building.md) | [androidJvm]<br>fun [updateBuilding](update-building.md)(building: [Building](../../com.example.hotstuffkotlin.models/-building/index.md))<br>Update the passed building in the database and toast the result (whether success/failure). |
| [updateItem](update-item.md) | [androidJvm]<br>fun [updateItem](update-item.md)(item: [Item](../../com.example.hotstuffkotlin.models/-item/index.md))<br>Update the passed item in the database and toast the result (whether success/failure). |
| [upgradeTables](upgrade-tables.md) | [androidJvm]<br>fun [upgradeTables](upgrade-tables.md)() |
