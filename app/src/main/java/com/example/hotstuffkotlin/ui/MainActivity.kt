package com.example.hotstuffkotlin.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.ActivityMainBinding
import com.example.hotstuffkotlin.utils.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTheme()

        // appbar
        appbar = findViewById(R.id.appbar_main)
        setSupportActionBar(appbar)

        // navbar
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_items,
            R.id.navigation_create_item,
            R.id.navigation_learn,
            R.id.navigation_settings
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_item_detail, R.id.navigation_edit_item, R.id.navigation_search
                -> navView.visibility = View.GONE
                else -> navView.visibility = View.VISIBLE
            }

//            val transfer = appbar.menu.findItem(R.id.appbar_main_transfer)
//            when (destination.id) {
//                R.id.navigation_item_detail -> transfer.isVisible = true
//                else -> transfer.isVisible = false
//            }
        }

        appbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.appbar_main_search -> {
                    navController.navigate(R.id.navigation_search)
                }
            }
            true
        }

        // bottom dialog
//        val createButton = navView.menu.findItem(R.id.navigation_placeholder)
//        createButton.setOnMenuItemClickListener {
//            val intent = Intent(this, NewItemActivity::class.java)
//            startActivity(intent)
//            navController.navigate(R.id.navigation_new_item)
//            true
//        }

//        createButton.setOnMenuItemClickListener {
//            val bsDialog = BottomSheetDialog(this)
//            val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
//            val buttonClose = view.findViewById<ImageButton>(R.id.dialog_button_close)
//            val buttonNewItem = view.findViewById<LinearLayout>(R.id.dialog_layoutNewItem)
//            val buttonNewBuilding = view.findViewById<LinearLayout>(R.id.dialog_layoutNewBuilding)

//            buttonClose.setOnClickListener {
//                bsDialog.dismiss()
//            }

//            buttonNewItem.setOnClickListener{
//                val intent = Intent(this, NewItemActivity::class.java)
//                startActivity(intent)
//            }
//            buttonNewBuilding.setOnClickListener{
//                val intent = Intent(this, NewBuildingActivity::class.java)
//                startActivity(intent)
//            }

//            bsDialog.setCancelable(true)
//            bsDialog.setContentView(view)
//            bsDialog.show()
//            true
//        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return true
    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        ThemeManager.applyTheme(requireNotNull(preferences.getString("appearance", "system")))
    }

}