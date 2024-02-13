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
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.models.Item
import java.io.File
import java.io.PrintWriter

class DatabaseHelper(val context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val queryItem = "CREATE TABLE $TABLE_NAME_ITEM (" +
            "$COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_ITEM_BUILDING_ID INTEGER, " +
            "$COLUMN_ITEM_NAME TEXT, " +
            "$COLUMN_ITEM_QUANTITY INTEGER, " +
            "$COLUMN_ITEM_CATEGORY TEXT, " +
            "$COLUMN_ITEM_ROOM TEXT, " +
            "$COLUMN_ITEM_MAKE TEXT, " +
            "$COLUMN_ITEM_VALUE MONEY, " +
            "$COLUMN_ITEM_IMAGE_URI TEXT, " +
            "$COLUMN_ITEM_DESCRIPTION TEXT);"

        val queryBuilding = "CREATE TABLE $TABLE_NAME_BUILDING (" +
            "$COLUMN_BUILDING_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "$COLUMN_BUILDING_NAME TEXT, " +
            "$COLUMN_BUILDING_DESCRIPTION TEXT);"

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
        val db = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_ITEM_NAME, name)
        cv.put(COLUMN_ITEM_QUANTITY, quantity)
        cv.put(COLUMN_ITEM_CATEGORY, category)
        cv.put(COLUMN_ITEM_ROOM, room)
        cv.put(COLUMN_ITEM_MAKE, make)
        cv.put(COLUMN_ITEM_VALUE, value)
        cv.put(COLUMN_ITEM_IMAGE_URI, image)
        cv.put(COLUMN_ITEM_DESCRIPTION, description)

        val result: Long =  db.insert(TABLE_NAME_ITEM, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(this.context, R.string.toast_addItem_fail.toString(),
                Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(this.context, R.string.toast_addItem_success.toString(),
                Toast.LENGTH_LONG).show()
        }
        db.close()
    }
    fun updateItem(id: Int, name: String, quantity: Int, category: String, room: String,
       make: String?, value: Double?, image: String?, description: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ITEM_NAME, name)
        cv.put(COLUMN_ITEM_QUANTITY, quantity)
        cv.put(COLUMN_ITEM_CATEGORY, category)
        cv.put(COLUMN_ITEM_ROOM, room)
        cv.put(COLUMN_ITEM_MAKE, make)
        cv.put(COLUMN_ITEM_VALUE, value)
        cv.put(COLUMN_ITEM_IMAGE_URI, image)
        cv.put(COLUMN_ITEM_DESCRIPTION, description)
        val result =  db.update(TABLE_NAME_ITEM, cv, "$COLUMN_ITEM_ID=?",
            arrayOf(id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, R.string.toast_updateItem_fail.toString(),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, R.string.toast_updateItem_success.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteItem(id: Int) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_ITEM, "$COLUMN_ITEM_ID=?",
            arrayOf(id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, R.string.toast_deleteItem_fail.toString(),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, R.string.toast_deleteItem_success.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }

    // As of now, addBuilding will only be called once, during the onboarding process, and
    // deleteBuilding will never be called, but the current plan is to support multiple buildings
    // in the future
    fun addBuilding(name: String, type: String?, description: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_BUILDING_NAME, name)
        cv.put(COLUMN_BUILDING_TYPE, type)
        cv.put(COLUMN_BUILDING_DESCRIPTION, description)
        val result: Long =  db.insert(TABLE_NAME_BUILDING, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(this.context, R.string.toast_addBuilding_fail.toString(),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this.context, R.string.toast_addBuilding_success.toString(),
                Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    fun updateBuilding(id: Int, name: String, description: String?) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ITEM_NAME, name)
        cv.put(COLUMN_ITEM_DESCRIPTION, description)
        val result =  db.update(TABLE_NAME_ITEM, cv, "$COLUMN_BUILDING_ID=?",
            arrayOf(id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, R.string.toast_updateBuilding_fail.toString(),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, R.string.toast_updateItem_success.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteBuilding(id: Int) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_BUILDING, "$COLUMN_BUILDING_ID=?",
            arrayOf(id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, R.string.toast_deleteBuilding_fail.toString(),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, R.string.toast_deleteBuilding_success.toString(),
                Toast.LENGTH_SHORT).show()
        }
    }

    fun getTotalQuantity() : String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_ITEM_QUANTITY) as $COLUMN_RESULT FROM $TABLE_NAME_ITEM"
        val cursor = db.rawQuery(query, null)
        var total = 0
        if (cursor.moveToFirst())
            total = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESULT))
        cursor.close()
        return if (total == 1) "1 item" else "$total items"
    }
    fun getTotalValue() : String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_ITEM_QUANTITY * $COLUMN_ITEM_VALUE) as " +
                "$COLUMN_RESULT FROM $TABLE_NAME_ITEM"
        val cursor = db.rawQuery(query, null)
        var total = 0.00
        if (cursor.moveToFirst())
            total = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RESULT))
        cursor.close()
        return String.format("%.2f", total)
    }
    fun getCategoryQuantity(): Pair<ArrayList<String>, ArrayList<Float>> {
        val db = this.writableDatabase
        val query = "SELECT $COLUMN_ITEM_CATEGORY, SUM($COLUMN_ITEM_QUANTITY) AS " +
                "$COLUMN_RESULT FROM $TABLE_NAME_ITEM GROUP BY $COLUMN_ITEM_CATEGORY"
        val cursor = db.rawQuery(query, null)
        val categoryLabels = ArrayList<String>()
        val categoryQuantityFloats = ArrayList<Float>()
        if (cursor.moveToFirst()) {
            val categoryColumn = cursor.getColumnIndexOrThrow(COLUMN_ITEM_CATEGORY)
            val totalColumn = cursor.getColumnIndexOrThrow(COLUMN_RESULT)
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
        val query = "SELECT $COLUMN_ITEM_ROOM, SUM($COLUMN_ITEM_QUANTITY * $COLUMN_ITEM_VALUE) as " +
                "$COLUMN_RESULT FROM $TABLE_NAME_ITEM GROUP BY $COLUMN_ITEM_ROOM ORDER BY $COLUMN_RESULT ASC"
        val cursor = db.rawQuery(query, null)
        val roomLabels = ArrayList<String>()
        val roomValueFloats = ArrayList<Float>()
        if (cursor.moveToFirst()) {
            val roomColumn = cursor.getColumnIndexOrThrow(COLUMN_ITEM_ROOM)
            val totalColumn = cursor.getColumnIndexOrThrow(COLUMN_RESULT)
            do {
                roomLabels.add(cursor.getString(roomColumn))
                roomValueFloats.add(cursor.getFloat(totalColumn))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return Pair(roomLabels, roomValueFloats)
    }
    fun getDataRange(offset: Int = 0, queryType: String = "ALL",
                     searchQuery: String? = null): ArrayList<Item> {
        val itemList: ArrayList<Item> = ArrayList()
        val selectQuery: String = if (queryType == "SEARCH" && searchQuery != null) {
            "SELECT * FROM $TABLE_NAME_ITEM WHERE ($COLUMN_ITEM_NAME LIKE $searchQuery OR " +
            "$COLUMN_ITEM_ROOM LIKE $searchQuery OR $COLUMN_ITEM_CATEGORY LIKE $searchQuery " +
            "OR $COLUMN_ITEM_MAKE LIKE $searchQuery) LIMIT $PAGINATION_DATA_LIMIT OFFSET $offset"
        } else {
            "SELECT * FROM $TABLE_NAME_ITEM LIMIT $PAGINATION_DATA_LIMIT OFFSET $offset"
        }
        val db = this.readableDatabase
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var buildingId: Int
        var name: String
        var quantity: Int
        var category: String
        var room: String?
        var make: String?
        var value: Double?
        var imageUri: String?
        var description: String?

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ID))
                buildingId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_BUILDING_ID))
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_NAME))
                quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ITEM_QUANTITY))
                category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_CATEGORY))
                room = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_ROOM))
                make = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_MAKE))
                value = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_ITEM_VALUE))
                imageUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_IMAGE_URI))
                description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ITEM_DESCRIPTION))
                val i = Item(id = id, buildingId = buildingId, name = name,
                    quantity = quantity, category = category, room = room, make = make,
                    value = value, imageUri = imageUri, description = description)
                itemList.add(i)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return itemList
    }

    fun exportCSV() {
        val exportDirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "")
        if (!exportDirectory.exists()) exportDirectory.mkdirs()
        val file = File(exportDirectory, "HS-Data-${System.currentTimeMillis()}.csv")
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
                rowArray.add(cursor.getStringOrNull(6) ?: "") // make
                rowArray.add(cursor.getDoubleOrNull(7).toString()) // value
                rowArray.add(cursor.getStringOrNull(8).toString()) // image_uri
                rowArray.add(cursor.getStringOrNull(9) ?: "") // description
                csvHelper.separateRow(rowArray)
            }
            Toast.makeText(context, R.string.toast_exportCSV_success.toString(),
                Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, R.string.toast_exportCSV_fail.toString(),
                Toast.LENGTH_LONG).show()
            Toast.makeText(context, "Error: $e",
                Toast.LENGTH_LONG).show()
        }
        csvHelper.close()
        cursor.close()
    }

    companion object{
        private const val DATABASE_VERSION = 1
        private const val PAGINATION_DATA_LIMIT = 20

        private val DATABASE_NAME = R.string.db_name.toString()
        private val TABLE_NAME_ITEM = R.string.db_table_item.toString()
        private val COLUMN_ITEM_ID = R.string.db_column_item_id.toString()
        private val COLUMN_ITEM_BUILDING_ID = R.string.db_column_item_buildingId.toString()
        private val COLUMN_ITEM_NAME = R.string.db_column_item_name.toString()
        private val COLUMN_ITEM_QUANTITY = R.string.db_column_item_quantity.toString()
        private val COLUMN_ITEM_CATEGORY = R.string.db_column_item_category.toString()
        private val COLUMN_ITEM_ROOM = R.string.db_column_item_room.toString()
        private val COLUMN_ITEM_MAKE = R.string.db_column_item_make.toString()
        private val COLUMN_ITEM_VALUE = R.string.db_column_item_value.toString()
        private val COLUMN_ITEM_IMAGE_URI = R.string.db_column_item_imageUri.toString()
        private val COLUMN_ITEM_DESCRIPTION = R.string.db_column_item_description.toString()
        private val TABLE_NAME_BUILDING = R.string.db_table_building.toString()
        private val COLUMN_BUILDING_ID = R.string.db_column_building_id.toString()
        private val COLUMN_BUILDING_NAME = R.string.db_column_building_name.toString()
        private val COLUMN_BUILDING_TYPE = R.string.db_column_building_type.toString()
        private val COLUMN_BUILDING_DESCRIPTION = R.string.db_column_building_description.toString()
        private val COLUMN_RESULT = R.string.db_column_result.toString()
    }
}