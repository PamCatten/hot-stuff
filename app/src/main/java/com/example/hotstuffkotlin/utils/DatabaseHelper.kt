package com.example.hotstuffkotlin.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    public

    override fun onCreate(db: SQLiteDatabase?) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BUILDING_ID + " INTEGER FOREIGN KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_MAKE + " TEXT, " +
                COLUMN_VALUE + " MONEY, " +
                COLUMN_IMAGE_PATH + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT);")
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
    companion object{
        // here we have defined variables for our database
        private const val DATABASE_NAME : String = "ItemManifest.db"
        private const val DATABASE_VERSION : Int = 1
        val TABLE_NAME : String = "item_manifest"
        val COLUMN_ID : String = "item_id" // int? primary key item_manifest table
        val COLUMN_BUILDING_ID : String = "item_building_id" // int? foreign key building_manifest table
        val COLUMN_NAME : String = "item_name" // make required
        val COLUMN_QUANTITY : String = "item_quantity" // int? make required
        val COLUMN_LOCATION : String = "item_location" // nullable
        val COLUMN_MAKE : String = "item_make" // nullable
        val COLUMN_VALUE : String = "item_value" // double?
        val COLUMN_IMAGE_PATH : String = "item_image" // nullable
        val COLUMN_DESCRIPTION : String = "item_description" // nullable

    }
}