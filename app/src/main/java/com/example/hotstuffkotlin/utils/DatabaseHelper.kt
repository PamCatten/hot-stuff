package com.example.hotstuffkotlin.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Environment
import android.widget.Toast
import androidx.core.database.getDoubleOrNull
import androidx.core.database.getStringOrNull
import com.example.hotstuffkotlin.models.Item
import java.io.File
import java.io.PrintWriter

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val context = context

    override fun onCreate(db: SQLiteDatabase?) {
        val queryItem = "CREATE TABLE $TABLE_NAME_ITEM (" +
            "$COLUMN_ID_ITEM INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_BUILDING_ID_ITEM INTEGER, " +
            "$COLUMN_NAME_ITEM TEXT, " +
            "$COLUMN_QUANTITY_ITEM INTEGER, " +
            "$COLUMN_CATEGORY_ITEM TEXT, " +
            "$COLUMN_ROOM_ITEM TEXT, " +
            "$COLUMN_MAKE_ITEM TEXT, " +
            "$COLUMN_VALUE_ITEM MONEY, " +
            "$COLUMN_IMAGE_PATH_ITEM TEXT, " +
            "$COLUMN_DESCRIPTION_ITEM TEXT);"
        val queryBuilding = "CREATE TABLE $TABLE_NAME_BUILDING (" +
            "$COLUMN_ID_BUILDING INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_NAME_BUILDING TEXT, " +
            "$COLUMN_DESCRIPTION_BUILDING TEXT);"

        db?.execSQL(queryItem)
        db?.execSQL(queryBuilding)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_ITEM")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_BUILDING")
        onCreate(db)
    }
    fun addItem(name: String, quantity: Int, category: String, room: String,
        make: String?, value: Double?, image: String?, description: String?) {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_NAME_ITEM, name)
        cv.put(COLUMN_QUANTITY_ITEM, quantity)
        cv.put(COLUMN_CATEGORY_ITEM, category)
        cv.put(COLUMN_ROOM_ITEM, room)
        cv.put(COLUMN_MAKE_ITEM, make)
        cv.put(COLUMN_VALUE_ITEM, value)
        cv.put(COLUMN_IMAGE_PATH_ITEM, image)
        cv.put(COLUMN_DESCRIPTION_ITEM, description)

        val result: Long =  db.insert(TABLE_NAME_ITEM, null, cv)
        if (result == (-1).toLong()) Toast.makeText(this.context, "Oh no! Item failed to save", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this.context, "Item saved!", Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun updateItem(id: Int, name: String, quantity: Int, category: String, room: String,
       make: String?, value: Double?, image: String?, description: String?) {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME_ITEM, name)
        cv.put(COLUMN_QUANTITY_ITEM, quantity)
        cv.put(COLUMN_CATEGORY_ITEM, category)
        cv.put(COLUMN_ROOM_ITEM, room)
        cv.put(COLUMN_MAKE_ITEM, make)
        cv.put(COLUMN_VALUE_ITEM, value)
        cv.put(COLUMN_IMAGE_PATH_ITEM, image)
        cv.put(COLUMN_DESCRIPTION_ITEM, description)
        val result =  db.update(TABLE_NAME_ITEM, cv, "item_id=?", arrayOf(id.toString()))
        if (result == (-1)) Toast.makeText(context, "Oh no! Update failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show()
    }
    fun deleteItem(id: Int) {
        val db : SQLiteDatabase = this.writableDatabase
        val result = db.delete(TABLE_NAME_ITEM, "item_id=?", arrayOf(id.toString()))
        if (result == (-1)) Toast.makeText(context, "Deletion failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Delete successful!", Toast.LENGTH_SHORT).show()
    }

    // As of now, addBuilding will only be called once, during the onboarding process, and
    // deleteBuilding will never be called, but the current plan is to support multiple buildings
    // in the future, so I'm going to leave them in to help future Cam out.
    fun addBuilding(name: String, description: String?) {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME_BUILDING, name)
        cv.put(COLUMN_DESCRIPTION_BUILDING, description)
        val result: Long =  db.insert(TABLE_NAME_BUILDING, null, cv)
        if (result == (-1).toLong()) Toast.makeText(this.context, "Oh no! Database insertion failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this.context, "Database insertion successful!", Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun updateBuilding(id: Int, name: String, description: String?) {
        val db: SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME_ITEM, name)
        cv.put(COLUMN_DESCRIPTION_ITEM, description)
        val result =  db.update(TABLE_NAME_ITEM, cv, "building_id=?", arrayOf(id.toString()))
        if (result == (-1)) Toast.makeText(context, "Oh no! Update failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show()
    }
    fun deleteBuilding(id: Int) {
        val db: SQLiteDatabase = this.writableDatabase
        val result = db.delete(TABLE_NAME_BUILDING, "building_id=?", arrayOf(id.toString()))
        if (result == (-1)) Toast.makeText(context, "Deletion failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Delete successful!", Toast.LENGTH_SHORT).show()
    }

    fun getTotalQuantity() : String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_QUANTITY_ITEM) as TOTAL FROM $TABLE_NAME_ITEM"
        val cursor = db.rawQuery(query, null)
        var total = 0
        if (cursor.moveToFirst())
            total = cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL"))
        cursor.close()
        return if (total == 1) "1 item" else "$total items"
    }
    fun getTotalValue() : String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_QUANTITY_ITEM * $COLUMN_VALUE_ITEM) as TOTAL FROM $TABLE_NAME_ITEM"
        val cursor = db.rawQuery(query, null)
        var total = 0.00
        if (cursor.moveToFirst())
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("TOTAL"))
        cursor.close()
        return String.format("%.2f", total)
    }
    fun getCategoryQuantity(): Pair<ArrayList<String>, ArrayList<Float>> {
        val db = this.writableDatabase
        val query = "SELECT $COLUMN_CATEGORY_ITEM, SUM($COLUMN_QUANTITY_ITEM) AS TOTAL FROM $TABLE_NAME_ITEM GROUP BY $COLUMN_CATEGORY_ITEM"
        val cursor = db.rawQuery(query, null)
        val categoryLabels = ArrayList<String>()
        val categoryQuantityFloats = ArrayList<Float>()
        if (cursor.moveToFirst()) {
            val categoryColumn = cursor.getColumnIndexOrThrow("$COLUMN_CATEGORY_ITEM")
            val totalColumn = cursor.getColumnIndexOrThrow("TOTAL")
            do {
                categoryLabels.add(cursor.getString(categoryColumn))
                categoryQuantityFloats.add(cursor.getFloat(totalColumn))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return Pair(categoryLabels, categoryQuantityFloats)
    }
    fun getRoomValue(): Pair<ArrayList<String>, ArrayList<Float>> {
        val db = this.writableDatabase
        val query = "SELECT $COLUMN_ROOM_ITEM, SUM($COLUMN_QUANTITY_ITEM * $COLUMN_VALUE_ITEM) as TOTAL FROM $TABLE_NAME_ITEM GROUP BY $COLUMN_ROOM_ITEM ORDER BY TOTAL ASC"
        val cursor = db.rawQuery(query, null)
        val roomLabels = ArrayList<String>()
        val roomValueFloats = ArrayList<Float>()
        if (cursor.moveToFirst()) {
            val roomColumn = cursor.getColumnIndexOrThrow("$COLUMN_ROOM_ITEM")
            val totalColumn = cursor.getColumnIndexOrThrow("TOTAL")
            do {
                roomLabels.add(cursor.getString(roomColumn))
                roomValueFloats.add(cursor.getFloat(totalColumn))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return Pair(roomLabels, roomValueFloats)
    }
    fun getDataRange(offset: Int = 0, queryType: String = "ALL", searchQuery: String? = null): ArrayList<Item> {
        val itemList: ArrayList<Item> = ArrayList()
        val selectQuery: String = if (queryType == "SEARCH" && searchQuery != null) {
            "SELECT * FROM $TABLE_NAME_ITEM WHERE ($COLUMN_NAME_ITEM LIKE $searchQuery OR $COLUMN_ROOM_ITEM LIKE $searchQuery OR $COLUMN_CATEGORY_ITEM LIKE $searchQuery OR $COLUMN_MAKE_ITEM LIKE $searchQuery) LIMIT $PAGINATION_DATA_LIMIT OFFSET $offset"
        } else {
            "SELECT * FROM $TABLE_NAME_ITEM LIMIT $PAGINATION_DATA_LIMIT OFFSET $offset"
        }
        val db: SQLiteDatabase = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var itemId: Int
        var buildingId: Int
        var name: String
        var quantity: Int
        var category: String
        var room: String?
        var make: String?
        var value: Double?
        var imagePath: String?
        var description: String?

        if (cursor.moveToFirst()) {
            do {
                itemId = cursor.getInt(cursor.getColumnIndexOrThrow("item_id"))
                buildingId = cursor.getInt(cursor.getColumnIndexOrThrow("item_building_id"))
                name = cursor.getString(cursor.getColumnIndexOrThrow("item_name"))
                quantity = cursor.getInt(cursor.getColumnIndexOrThrow("item_quantity"))
                category = cursor.getString(cursor.getColumnIndexOrThrow("item_category"))
                room = cursor.getString(cursor.getColumnIndexOrThrow("item_room"))
                make = cursor.getString(cursor.getColumnIndexOrThrow("item_make"))
                value = cursor.getDouble(cursor.getColumnIndexOrThrow("item_value"))
                imagePath = cursor.getString(cursor.getColumnIndexOrThrow("item_image"))
                description = cursor.getString(cursor.getColumnIndexOrThrow("item_description"))
                val i = Item(itemId = itemId, buildingId = buildingId, name = name,
                    quantity = quantity, category = category, room = room, make = make,
                    value = value, imagePath = imagePath, description = description)
                itemList.add(i)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    fun exportCSV() {
        val exportDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "")
        if (!exportDirectory.exists()) exportDirectory.mkdirs()
        val file = File(exportDirectory, "HS-Item-Manifest-${System.currentTimeMillis()}.csv")
        val db = this.writableDatabase
        val cursor: Cursor?
        val csvQuery = "SELECT * FROM $TABLE_NAME_ITEM"
        cursor = db.rawQuery(csvQuery, null)
        file.createNewFile()
        val csvHelper = CSVHelper(PrintWriter(file))
        try {
            var columnNames: MutableList<String> = cursor.columnNames.toCollection(ArrayList())
            columnNames = columnNames.subList(2, columnNames.size)

            csvHelper.separateRow(columnNames)
            val rowArray = ArrayList<String>()

            while (cursor.moveToNext()) {
                rowArray.clear()
                rowArray.add(cursor.getString(2)) // name
                rowArray.add(cursor.getInt(3).toString()) // quantity
                rowArray.add(cursor.getString(4)) // category
                rowArray.add(cursor.getString(5)) // room
                rowArray.add(cursor.getStringOrNull(6) ?: "Unspecified") // make
                rowArray.add(cursor.getDoubleOrNull(7).toString()) // value
                rowArray.add(cursor.getStringOrNull(8).toString()) // imagePath
                rowArray.add(cursor.getStringOrNull(9) ?: "") // description
                csvHelper.separateRow(rowArray)
            }
            Toast.makeText(context, "CSV downloaded!", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
        }
        csvHelper.close()
        cursor.close()
    }

    companion object{
        private const val DATABASE_NAME: String = "HotStuff.db"
        private const val DATABASE_VERSION: Int = 1
        private const val TABLE_NAME_ITEM: String = "item_manifest"
        private const val COLUMN_ID_ITEM: String = "item_id" // int? primary key item_manifest table
        private const val COLUMN_BUILDING_ID_ITEM: String = "item_building_id" // int? foreign key building_manifest table
        private const val COLUMN_NAME_ITEM: String = "item_name" // string, required
        private const val COLUMN_QUANTITY_ITEM: String = "item_quantity" // int? make required
        private const val COLUMN_CATEGORY_ITEM: String = "item_category"
        private const val COLUMN_ROOM_ITEM: String = "item_room" // nullable
        private const val COLUMN_MAKE_ITEM: String = "item_make" // nullable
        private const val COLUMN_VALUE_ITEM: String = "item_value" // double?
        private const val COLUMN_IMAGE_PATH_ITEM: String = "item_image" // nullable
        private const val COLUMN_DESCRIPTION_ITEM : String = "item_description" // nullable

        private const val TABLE_NAME_BUILDING: String = "building_manifest"
        private const val COLUMN_ID_BUILDING: String = "building_id"
        private const val COLUMN_NAME_BUILDING: String = "building_name"
        private const val COLUMN_DESCRIPTION_BUILDING: String = "building_description"

        private const val PAGINATION_DATA_LIMIT : Int = 20
    }
}