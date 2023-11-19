package com.example.hotstuffkotlin.ui

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.ActivityMainBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appbar: Toolbar

    // db stopgap
    lateinit var db : DatabaseHelper
    lateinit var item_id : ArrayList<Int>
    lateinit var building_id : ArrayList<Int>
    lateinit var item_name : ArrayList<String>
    lateinit var item_quantity : ArrayList<Int>
    lateinit var item_category : ArrayList<String>
    lateinit var item_value : ArrayList<Double>
    lateinit var item_room : ArrayList<String>
    lateinit var item_make : ArrayList<String>
    lateinit var item_imagePath : ArrayList<String>
    lateinit var item_description : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTheme()

        // appbar
        appbar = findViewById(R.id.appbar)
        setSupportActionBar(appbar)

        // navbar
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_items,
            R.id.navigation_placeholder,
            R.id.navigation_learn,
            R.id.navigation_settings
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // bottom dialog
        val createButton = navView.menu.findItem(R.id.navigation_placeholder)
        createButton.setOnMenuItemClickListener {
            val bsDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
            val buttonClose = view.findViewById<ImageButton>(R.id.dialog_button_close)
            val buttonNewItem = view.findViewById<LinearLayout>(R.id.dialog_layoutNewItem)
            val buttonNewBuilding = view.findViewById<LinearLayout>(R.id.dialog_layoutNewBuilding)

            buttonClose.setOnClickListener {
                bsDialog.dismiss()
            }

            buttonNewItem.setOnClickListener{
                val intent = Intent(this, NewItemActivity::class.java)
                startActivity(intent)
            }

            buttonNewBuilding.setOnClickListener{
                val intent = Intent(this, NewBuildingActivity::class.java)
                startActivity(intent)
            }

            bsDialog.setCancelable(true)
            bsDialog.setContentView(view)
            bsDialog.show()
            true
        }

        // db
        val db = DatabaseHelper(this, null)
        item_id = ArrayList()
        building_id = ArrayList()
        item_name = ArrayList()
        item_quantity = ArrayList()
        item_category = ArrayList()
        item_value = ArrayList()
        item_room = ArrayList()
        item_make = ArrayList()
        item_imagePath = ArrayList()
        item_description = ArrayList()

        storeDataInArrays()
    }
    fun storeDataInArrays() {
        val cursor : Cursor? = db.readData()
        if (cursor == null || cursor.count == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show()
        } else {
            while (cursor.moveToNext()) {
                item_id.add(cursor.getInt(0))
                building_id.add(cursor.getInt(1))
                item_name.add(cursor.getString(2))
                item_quantity.add(cursor.getInt(3))
                item_category.add(cursor.getString(4))
                item_value.add(cursor.getDouble(5))
                item_room.add(cursor.getString(6))
                item_make.add(cursor.getString(7))
                item_imagePath.add(cursor.getString(8))
                item_description.add(cursor.getString(9))
            }
        }
    }

//    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
//        super.onPrepareOptionsMenu(menu.apply {
//            findItem(R.id.appbar_search)?.let {
//                it.isVisible = SharedPreferenceHelper.allowSearch() == true
//            }
//        })
//        return true
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.appbar_menu, menu)
        return true
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        ThemeManager.applyTheme(requireNotNull(preferences.getString("appearance", "system")))
    }

}