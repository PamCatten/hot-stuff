package com.example.hotstuffkotlin.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.hotstuffkotlin.models.Item

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val context = context

    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_BUILDING_ID INTEGER, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_QUANTITY INTEGER, " +
                "$COLUMN_CATEGORY TEXT, " +
                "$COLUMN_ROOM TEXT, " +
                "$COLUMN_MAKE TEXT, " +
                "$COLUMN_VALUE MONEY, " +
                "$COLUMN_IMAGE_PATH TEXT, " +
                "$COLUMN_DESCRIPTION TEXT);"
        db?.execSQL(query)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun addItem(name: String, quantity: Int, category: String, room: String,
        make: String?, value: Double?, image: String?, description: String?) {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_QUANTITY, quantity)
        cv.put(COLUMN_CATEGORY, category)
        cv.put(COLUMN_ROOM, room)
        cv.put(COLUMN_MAKE, make)
        cv.put(COLUMN_VALUE, value)
        cv.put(COLUMN_IMAGE_PATH, image)
        cv.put(COLUMN_DESCRIPTION, description)

        val result: Long =  db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) Toast.makeText(this.context, "Oh no! Database insertion failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(this.context, "Database insertion successful!", Toast.LENGTH_SHORT).show()
        db.close()
    }
    fun updateItem(id: Int, name: String, quantity: Int, category: String, room: String,
       make: String?, value: Double?, image: String?, description: String?) {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_NAME, name)
        cv.put(COLUMN_QUANTITY, quantity)
        cv.put(COLUMN_CATEGORY, category)
        cv.put(COLUMN_ROOM, room)
        cv.put(COLUMN_MAKE, make)
        cv.put(COLUMN_VALUE, value)
        cv.put(COLUMN_IMAGE_PATH, image)
        cv.put(COLUMN_DESCRIPTION, description)
        val result =  db.update(TABLE_NAME, cv, "item_id=?", arrayOf(id.toString()))
        if (result == (-1)) Toast.makeText(context, "Oh no! Update failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Update successful!", Toast.LENGTH_SHORT).show()
    }
    fun deleteItem(id: Int) {
        val db : SQLiteDatabase = this.writableDatabase
        val result = db.delete(TABLE_NAME, "item_id=?", arrayOf(id.toString()))
        if (result == (-1)) Toast.makeText(context, "Deletion failed.", Toast.LENGTH_SHORT).show()
        else Toast.makeText(context, "Delete successful!", Toast.LENGTH_SHORT).show()
    }
    fun getTotalQuantity() : String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_QUANTITY) as TOTAL FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        var total = 0
        if (cursor.moveToFirst())
            total = cursor.getInt(cursor.getColumnIndexOrThrow("TOTAL"))
        cursor.close()
        return if (total == 1) "1 item" else "$total items"
    }
    fun getTotalValue() : String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_QUANTITY * $COLUMN_VALUE) as TOTAL FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        var total = 0.00
        if (cursor.moveToFirst())
            total = cursor.getDouble(cursor.getColumnIndexOrThrow("TOTAL"))
        cursor.close()
        return String.format("%.2f", total)
    }
    fun getCategoryQuantity(): Pair<ArrayList<String>, ArrayList<Float>> {
        val db = this.writableDatabase
        val query = "SELECT $COLUMN_CATEGORY, SUM($COLUMN_QUANTITY) AS TOTAL FROM $TABLE_NAME GROUP BY $COLUMN_CATEGORY"
        val cursor = db.rawQuery(query, null)
        val categoryLabels = ArrayList<String>()
        val categoryQuantityFloats = ArrayList<Float>()
        if (cursor.moveToFirst()) {
            val categoryColumn = cursor.getColumnIndexOrThrow("$COLUMN_CATEGORY")
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
        val query = "SELECT $COLUMN_ROOM, SUM($COLUMN_QUANTITY * $COLUMN_VALUE) as TOTAL FROM $TABLE_NAME GROUP BY $COLUMN_ROOM ORDER BY TOTAL ASC"
        val cursor = db.rawQuery(query, null)
        val roomLabels = ArrayList<String>()
        val roomValueFloats = ArrayList<Float>()
        if (cursor.moveToFirst()) {
            val roomColumn = cursor.getColumnIndexOrThrow("$COLUMN_ROOM")
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
            "SELECT * FROM $TABLE_NAME WHERE ($COLUMN_NAME LIKE $searchQuery OR $COLUMN_ROOM LIKE $searchQuery OR $COLUMN_CATEGORY LIKE $searchQuery OR $COLUMN_MAKE LIKE $searchQuery) LIMIT $PAGINATION_DATA_LIMIT OFFSET $offset"
        } else {
            "SELECT * FROM $TABLE_NAME LIMIT $PAGINATION_DATA_LIMIT OFFSET $offset"
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

    companion object{
        private const val DATABASE_NAME : String = "ItemManifest.db"
        private const val DATABASE_VERSION : Int = 1
        private const val TABLE_NAME : String = "item_manifest"
        private const val COLUMN_ID : String = "item_id" // int? primary key item_manifest table
        private const val COLUMN_BUILDING_ID : String = "item_building_id" // int? foreign key building_manifest table
        private const val COLUMN_NAME : String = "item_name" // string, required
        private const val COLUMN_QUANTITY : String = "item_quantity" // int? make required
        private const val COLUMN_CATEGORY : String = "item_category"
        private const val COLUMN_ROOM : String = "item_room" // nullable
        private const val COLUMN_MAKE : String = "item_make" // nullable
        private const val COLUMN_VALUE : String = "item_value" // double?
        private const val COLUMN_IMAGE_PATH : String = "item_image" // nullable
        private const val COLUMN_DESCRIPTION : String = "item_description" // nullable
        private const val PAGINATION_DATA_LIMIT : Int = 20
    }
}