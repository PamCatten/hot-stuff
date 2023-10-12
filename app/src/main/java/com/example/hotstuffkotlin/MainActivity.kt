package com.example.hotstuffkotlin

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
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
            R.id.navigation_create,
            R.id.navigation_learn,
            R.id.navigation_settings))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // dropdown / autocomplete
        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, languages)
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        autocompleteTV.setAdapter(arrayAdapter)

        binding.apply {}
    }

    fun showCreateDialog() {
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(R.layout.bottom_dialog)
        val dialogNewItem = dialog.findViewById<LinearLayout>(R.id.dialog_layoutNewItem)
        val dialogNewBuilding = dialog.findViewById<LinearLayout>(R.id.dialog_layoutNewBuilding)

        dialogNewItem?.setOnClickListener {
            Toast.makeText(this, "Clicked create new item", Toast.LENGTH_SHORT).show()
        }
        dialogNewBuilding?.setOnClickListener {
            Toast.makeText(this, "Clicked create new building", Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

}