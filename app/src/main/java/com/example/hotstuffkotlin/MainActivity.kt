package com.example.hotstuffkotlin

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager
import com.example.hotstuffkotlin.databinding.ActivityMainBinding
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
import com.example.hotstuffkotlin.utils.ThemeManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTheme()

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
        val createButton = navView.menu.findItem(R.id.navigation_placeholder)
        createButton.setOnMenuItemClickListener {
            val bsDialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.bottom_dialog, null)
            val buttonClose = view.findViewById<ImageButton>(R.id.dialog_button_close)

            buttonClose.setOnClickListener {
                bsDialog.dismiss()
            }
            bsDialog.setCancelable(true)
            bsDialog.setContentView(view)
            bsDialog.show()
            true
        }

        // derived info
        val tvActiveBuildingValue = findViewById<TextView>(R.id.tvActiveBuildingValue)
        val derivedTotal = "6,500.00"
        //var currencyIcon = sharedPreferenceHelper.getCurrency().toString() + "_icon"
        //val currency = resources.getIdentifier(currencyIcon, "string", "hotstuffkotlin")
        tvActiveBuildingValue?.text = "$derivedTotal"

        val tvActiveBuildingCount = findViewById<TextView>(R.id.tvActiveBuildingCount)
        val derivedCount = 18
        tvActiveBuildingCount?.text = "$derivedCount items"

        val tvActiveBuildingName = findViewById<TextView>(R.id.tvActiveBuildingName)
        val derivedName = "Sample House"
        tvActiveBuildingName?.text = derivedName

    }

    private fun initTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        ThemeManager.applyTheme(requireNotNull(preferences.getString("appearance", "system")))
    }

}