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
import com.example.hotstuffkotlin.models.Building
import com.example.hotstuffkotlin.models.Item
import java.io.File
import java.io.PrintWriter

/**
 * Create a helper object to perform basic CRUD operations with SQL on the database.
 *
 * @param context Used to push to and receive information from the application environment.
 * @author Cam Patten
 */
class DatabaseHelper(val context: Context):
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
            "$COLUMN_BUILDING_TYPE TEXT, " +
            "$COLUMN_BUILDING_DESCRIPTION TEXT);"

        db?.execSQL(queryItem)
        db?.execSQL(queryBuilding)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_ITEM")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_BUILDING")
        onCreate(db)
    }

    fun upgradeTables() {
        val db = this.writableDatabase
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_ITEM")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_BUILDING")
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
                "$COLUMN_BUILDING_TYPE TEXT, " +
                "$COLUMN_BUILDING_DESCRIPTION TEXT);"
        db?.execSQL(queryItem)
        db?.execSQL(queryBuilding)
    }

    /**
     * Add the passed item to the database and toast the result (whether success/failure).
     *
     * @param item the item to be added to database
     * @author Cam Patten
     */
    fun addItem(item: Item) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ITEM_BUILDING_ID, item.buildingId)
        cv.put(COLUMN_ITEM_NAME, item.name)
        cv.put(COLUMN_ITEM_QUANTITY, item.quantity)
        cv.put(COLUMN_ITEM_CATEGORY, item.category)
        cv.put(COLUMN_ITEM_ROOM, item.room)
        cv.put(COLUMN_ITEM_MAKE, item.make)
        cv.put(COLUMN_ITEM_VALUE, item.value)
        cv.put(COLUMN_ITEM_IMAGE_URI, item.imageUri)
        cv.put(COLUMN_ITEM_DESCRIPTION, item.description)
        val result = db.insert(TABLE_NAME_ITEM, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(context, context.getText(R.string.toast_addItem_fail),
                Toast.LENGTH_LONG).show()
        }
        else {
            Toast.makeText(context, context.getText(R.string.toast_addItem_success),
                Toast.LENGTH_LONG).show()
        }
        db.close()
    }
    /**
     * Update the passed item in the database and toast the result (whether success/failure).
     *
     * @param item the item in the database to be updated
     * @author Cam Patten
     */
    fun updateItem(item: Item) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_ITEM_NAME, item.name)
        cv.put(COLUMN_ITEM_QUANTITY, item.quantity)
        cv.put(COLUMN_ITEM_CATEGORY, item.category)
        cv.put(COLUMN_ITEM_ROOM, item.room)
        cv.put(COLUMN_ITEM_MAKE, item.make)
        cv.put(COLUMN_ITEM_VALUE, item.value)
        cv.put(COLUMN_ITEM_IMAGE_URI, item.imageUri)
        cv.put(COLUMN_ITEM_DESCRIPTION, item.description)
        val result =  db.update(TABLE_NAME_ITEM, cv, "$COLUMN_ITEM_ID=?",
            arrayOf(item.id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, context.getText(R.string.toast_updateItem_fail),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, context.getText(R.string.toast_updateItem_success),
                Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Delete the row of the passed item id in the database and toast the result
     * (whether success/failure).
     *
     * @param id the id of the item to be deleted
     * @author Cam Patten
     */
    fun deleteItem(id: Int) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_ITEM, "$COLUMN_ITEM_ID=?",
            arrayOf(id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, context.getText(R.string.toast_deleteItem_fail),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, context.getText(R.string.toast_deleteItem_success),
                Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Add the passed building to the database and toast the result (whether success/failure).
     *
     * As of now, [addBuilding] is only called during the onboarding process, but we plan to support
     * users being able to create multiple buildings in the future, when it would presumably be of
     * greater utility.
     *
     * @param building the building to be added to database
     * @author Cam Patten
     */
    fun addBuilding(building: Building) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_BUILDING_NAME, building.name)
        cv.put(COLUMN_BUILDING_TYPE, building.type)
        cv.put(COLUMN_BUILDING_DESCRIPTION, building.description)
        val result: Long =  db.insert(TABLE_NAME_BUILDING, null, cv)
        if (result == (-1).toLong()) {
            Toast.makeText(this.context, context.getText(R.string.toast_addBuilding_fail),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(this.context, context.getText(R.string.toast_addBuilding_success),
                Toast.LENGTH_SHORT).show()
        }
        db.close()
    }
    /**
     * Update the passed building in the database and toast the result (whether success/failure).
     *
     * @param building the building to be updated in the database
     * @author Cam Patten
     */
    fun updateBuilding(building: Building) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_BUILDING_NAME, building.name)
        cv.put(COLUMN_BUILDING_TYPE, building.type)
        cv.put(COLUMN_BUILDING_DESCRIPTION, building.description)
        val result =  db.update(TABLE_NAME_BUILDING, cv, "$COLUMN_BUILDING_ID=?",
            arrayOf(building.id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, context.getText(R.string.toast_updateBuilding_fail),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, context.getText(R.string.toast_updateBuilding_success),
                Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Delete the row of the passed building id in the database and toast the result
     * (whether success/failure).
     *
     * As of now, [deleteBuilding] is never called, but we plan to support users being able to
     * create multiple buildings in the future, when it would presumably be of greater utility.
     *
     * @param id the id of the building to be deleted
     * @author Cam Patten
     */
    fun deleteBuilding(id: Int) {
        val db = this.writableDatabase
        val result = db.delete(TABLE_NAME_BUILDING, "$COLUMN_BUILDING_ID=?",
            arrayOf(id.toString()))
        if (result == (-1)) {
            Toast.makeText(context, context.getText(R.string.toast_deleteBuilding_fail),
                Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(context, context.getText(R.string.toast_deleteBuilding_success),
                Toast.LENGTH_SHORT).show()
        }
    }
    /**
     * Sums the quantity column of the ITEM table and returns the total as a formatted string.
     *
     * @return The total sum of saved item quantities in the database as a formatted string.
     * @author Cam Patten
     */
    fun getTotalQuantity(): String {
        val db = this.writableDatabase
        val query = "SELECT SUM($COLUMN_ITEM_QUANTITY) as $COLUMN_RESULT FROM $TABLE_NAME_ITEM"
        val cursor = db.rawQuery(query, null)
        var total = 0
        if (cursor.moveToFirst())
            total = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RESULT))
        cursor.close()
        return if (total == 1) {
            context.getString(R.string.label_total_quantity_item_builder, total.toString())
        } else {
            context.getString(R.string.label_total_quantity_items_builder, total.toString())
        }
    }
    /**
     * Sum the multiplied product of the quantity and value columns from the ITEM table and return
     * the total as a formatted string.
     *
     * @return The total sum of saved item values in the database to two decimal places as a string.
     * @author Cam Patten
     */
    fun getTotalValue(): String {
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
    /**
     * Retrieve ArrayLists of category names saved in the database and the total
     * item quantity of each category.
     *
     * @return A pair of ArrayLists: entered category names, and summed totals of the item
     * quantities associated with those categories.
     * @author Cam Patten
     */
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
    /**
     * Retrieve ArrayLists of room names saved in the database and the total
     * item quantity of each room.
     *
     * @return A pair of ArrayLists: entered room names, and the summed total value of items
     * within those rooms.
     * @author Cam Patten
     */
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
    /**
     * Retrieve a limited number of saved items from the database.
     *
     * @param offset The number of rows to be ignored in a result
     * @param isSearchQuery Determines if the query needs to be modified to search the NAME, ROOM,
     * CATEGORY, and MAKE columns in the database for values similar to a [searchQuery].
     * @param searchQuery String used to search for similar values in the database.
     * @return An ArrayList of items to be displayed in the RecyclerView .
     * @author Cam Patten
     */
    fun getDataRange(offset: Int = 0, isSearchQuery: Boolean = false,
                     searchQuery: String? = null): ArrayList<Item> {
        val itemList: ArrayList<Item> = ArrayList()
        val selectQuery: String = if (isSearchQuery && searchQuery != null) {
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

    /**
     *
     *
     * @author Cam Patten
     * @return
     */

    /**
     * Write item records to .CSV file, export to device downloads directory and toast result (whether
     * success/failure).
     *
     * @see [CSVHelper]
     * @author Cam Patten
     */
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
            Toast.makeText(context, context.getText(R.string.toast_exportCSV_success),
                Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, context.getText(R.string.toast_exportCSV_fail),
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

        private const val DATABASE_NAME = "HotStuff.db"
        private const val TABLE_NAME_ITEM = "ITEM"
        private const val COLUMN_ITEM_ID = "ID"
        private const val COLUMN_ITEM_BUILDING_ID = "BUILDING_ID"
        private const val COLUMN_ITEM_NAME = "NAME"
        private const val COLUMN_ITEM_QUANTITY = "QUANTITY"
        private const val COLUMN_ITEM_CATEGORY = "CATEGORY"
        private const val COLUMN_ITEM_ROOM = "ROOM"
        private const val COLUMN_ITEM_MAKE = "MAKE"
        private const val COLUMN_ITEM_VALUE = "VALUE"
        private const val COLUMN_ITEM_IMAGE_URI = "IMAGE_URI"
        private const val COLUMN_ITEM_DESCRIPTION = "DESCRIPTION"
        private const val TABLE_NAME_BUILDING = "BUILDING"
        private const val COLUMN_BUILDING_ID = "ID"
        private const val COLUMN_BUILDING_NAME = "NAME"
        private const val COLUMN_BUILDING_TYPE = "TYPE"
        private const val COLUMN_BUILDING_DESCRIPTION = "DESCRIPTION"
        private const val COLUMN_RESULT = "TOTAL"

        // TODO: Cannot retrieve strings without context, cannot add context here within the
        //  companion object without exposing leaks, seems only solution is providing context in
        //  front of each of variable reference and retrieving strings there, pretty gross. Need
        //  to find a better way, but for now, keeping the string resources here

//        private val DATABASE_NAME = R.string.db_name.toString()
//        private val TABLE_NAME_ITEM = R.string.db_table_item.toString()
//        private val COLUMN_ITEM_ID = R.string.db_column_item_id.toString()
//        private val COLUMN_ITEM_BUILDING_ID = R.string.db_column_item_buildingId.toString()
//        private val COLUMN_ITEM_NAME = R.string.db_column_item_name.toString()
//        private val COLUMN_ITEM_QUANTITY = R.string.db_column_item_quantity.toString()
//        private val COLUMN_ITEM_CATEGORY = R.string.db_column_item_category.toString()
//        private val COLUMN_ITEM_ROOM = R.string.db_column_item_room.toString()
//        private val COLUMN_ITEM_MAKE = R.string.db_column_item_make.toString()
//        private val COLUMN_ITEM_VALUE = R.string.db_column_item_value.toString()
//        private val COLUMN_ITEM_IMAGE_URI = R.string.db_column_item_imageUri.toString()
//        private val COLUMN_ITEM_DESCRIPTION = R.string.db_column_item_description.toString()
//        private val TABLE_NAME_BUILDING = R.string.db_table_building.toString()
//        private val COLUMN_BUILDING_ID = R.string.db_column_building_id.toString()
//        private val COLUMN_BUILDING_NAME = R.string.db_column_building_name.toString()
//        private val COLUMN_BUILDING_TYPE = R.string.db_column_building_type.toString()
//        private val COLUMN_BUILDING_DESCRIPTION = R.string.db_column_building_description.toString()
//        private val COLUMN_RESULT = R.string.db_column_result.toString()
    }
}