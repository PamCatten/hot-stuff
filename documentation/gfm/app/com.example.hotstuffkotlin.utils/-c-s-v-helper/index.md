//[app](../../../index.md)/[com.hotstuffkotlin.utils](../index.md)/[CSVHelper](index.md)

# CSVHelper

class [CSVHelper](index.md)(printWriter: [Writer](https://developer.android.com/reference/kotlin/java/io/Writer.html))

Create a helper object to convert and format List values to a text-output stream.

#### Author

Cam Patten

#### Parameters

androidJvm

| | |
|---|---|
| printWriter | instance of [Writer](https://developer.android.com/reference/kotlin/java/io/Writer.html) created in [DatabaseHelper.exportCSV](../-database-helper/export-c-s-v.md) |

#### See also

| |
|---|
| [DatabaseHelper](../-database-helper/index.md) |

## Constructors

| | |
|---|---|
| [CSVHelper](-c-s-v-helper.md) | [androidJvm]<br>constructor(printWriter: [Writer](https://developer.android.com/reference/kotlin/java/io/Writer.html)) |

## Types

| Name | Summary |
|---|---|
| [Companion](-companion/index.md) | [androidJvm]<br>object [Companion](-companion/index.md) |

## Functions

| Name | Summary |
|---|---|
| [close](close.md) | [androidJvm]<br>fun [close](close.md)()<br>Flush and close Java I/O stream. |
| [separateRow](separate-row.md) | [androidJvm]<br>fun [separateRow](separate-row.md)(nextLine: [MutableList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)&gt;?)<br>Separate and escape quotes escape, and write a given row of values to a text-output stream. |
