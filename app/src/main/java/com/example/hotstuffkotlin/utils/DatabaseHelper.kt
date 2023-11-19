package com.example.hotstuffkotlin.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.hotstuffkotlin.models.Item

class DatabaseHelper(context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

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
//        val query = ("CREATE TABLE $TABLE_NAME" + " ( " +
//                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                COLUMN_BUILDING_ID + " INTEGER, " +
//                COLUMN_NAME + " TEXT, " +
//                COLUMN_QUANTITY + " INTEGER, " +
//                COLUMN_CATEGORY + " TEXT, " +
//                COLUMN_ROOM + " TEXT, " +
//                COLUMN_MAKE + " TEXT, " +
//                COLUMN_VALUE + " MONEY, " +
//                COLUMN_IMAGE_PATH + " TEXT, " +
//                COLUMN_DESCRIPTION + " TEXT);")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addItem(name : String,
       quantity : Int,
       category : String?,
       value : Double?,
       room : String?,
       make : String?,
       image : String?,
       description : String?) {
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
        val result : Long =  db.insert(TABLE_NAME, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(this.context, "Database insertion failed.", Toast.LENGTH_SHORT).show()
        } else
            Toast.makeText(this.context, "Database insertion successful!.", Toast.LENGTH_SHORT).show()
        db.close()
    }
//    fun addItem(i : Item) : Long {
//        val db : SQLiteDatabase = this.writableDatabase
//        val cv = ContentValues()
//        cv.put(COLUMN_BUILDING_ID, i.buildingId)
//        cv.put(COLUMN_NAME, i.name)
//        cv.put(COLUMN_QUANTITY, i.quantity)
//        cv.put(COLUMN_CATEGORY, i.category)
//        cv.put(COLUMN_ROOM, i.room)
//        cv.put(COLUMN_MAKE, i.make)
//        cv.put(COLUMN_VALUE, i.value)
//        cv.put(COLUMN_IMAGE_PATH, i.imagePath)
//        cv.put(COLUMN_DESCRIPTION, i.description)
//        val result : Long =  db.insert(TABLE_NAME, null, cv)
//        if (result == (-1).toLong()) {
//            Toast.makeText(this.context, "Database insertion failed.", Toast.LENGTH_SHORT).show()
//        } else
//            Toast.makeText(this.context, "Database insertion successful!.", Toast.LENGTH_SHORT).show()
//        db.close()
//        return result
//    }
    fun getItems(): ArrayList<Item> {
        val itemList : ArrayList<Item> = ArrayList()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db : SQLiteDatabase = this.readableDatabase
        var cursor : Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: Exception) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var itemId : Int
        var buildingId : Int
        var name : String
        var quantity : Int
        var category : String?
        var room : String?
        var make : String?
        var value : Double?
        var imagePath : String?
        var description : String?

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

        return itemList
    }

    fun readData() : Cursor? {
        val query = "SELECT * FROM $TABLE_NAME"
        val db : SQLiteDatabase = this.readableDatabase
        var cursor : Cursor? = null

        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        return cursor
    }

    companion object{
        // here we have defined variables for our database
        private const val DATABASE_NAME : String = "ItemManifest.db"
        private const val DATABASE_VERSION : Int = 1
        private const val TABLE_NAME : String = "item_manifest"
        private const val COLUMN_ID : String = "item_id" // int? primary key item_manifest table
        private const val COLUMN_BUILDING_ID : String = "item_building_id" // int? foreign key building_manifest table
        private const val COLUMN_NAME : String = "item_name" // make required
        private const val COLUMN_QUANTITY : String = "item_quantity" // int? make required
        private const val COLUMN_CATEGORY : String = "item_category"
        private const val COLUMN_ROOM : String = "item_room" // nullable
        private const val COLUMN_MAKE : String = "item_make" // nullable
        private const val COLUMN_VALUE : String = "item_value" // double?
        private const val COLUMN_IMAGE_PATH : String = "item_image" // nullable
        private const val COLUMN_DESCRIPTION : String = "item_description" // nullable

    }
}