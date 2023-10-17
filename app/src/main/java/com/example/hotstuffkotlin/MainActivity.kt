package com.example.hotstuffkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.hotstuffkotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // appbar
        setSupportActionBar(findViewById(R.id.appBar))

        // navbar
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_items,
            R.id.navigation_placeholder,
            R.id.navigation_learn,
            R.id.navigation_settings))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // bottom dialog
        // attempt 2
        val bottomNav = findViewById<BottomNavigationView>(R.id.mobile_navigation)
        val menuItem = bottomNav.menu.findItem(R.id.navigation_placeholder)


        val createNavButton = findViewById<ImageButton>(R.id.buttonShow)
        createNavButton.setOnClickListener {
            val bsDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
            val buttonClose = view.findViewById<ImageButton>(R.id.dialog_button_close)

            buttonClose.setOnClickListener {
                bsDialog.dismiss()
            }
            bsDialog.setCancelable(true)
            bsDialog.setContentView(view)
            bsDialog.show()
        }
        // attempt 1
//        val buttonShow = findViewById<ImageButton>(R.id.buttonShow)
//
//        buttonShow.setOnClickListener {
//            val bsDialog = BottomSheetDialog(this)
//            val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
//            val buttonClose = view.findViewById<ImageButton>(R.id.dialog_button_close)
//
//            buttonClose.setOnClickListener {
//                bsDialog.dismiss()
//            }
//            bsDialog.setCancelable(true)
//            bsDialog.setContentView(view)
//            bsDialog.show()
//        }

        // dropdown / autocomplete
        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)
    }

}