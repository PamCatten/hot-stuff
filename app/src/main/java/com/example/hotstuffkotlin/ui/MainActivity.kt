package com.example.hotstuffkotlin.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
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
        navController.addOnDestinationChangedListener{_, destination, _ ->
            if (destination.id == R.id.navigation_item_detail) navView.visibility = View.GONE
            else navView.visibility = View.VISIBLE
        }

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