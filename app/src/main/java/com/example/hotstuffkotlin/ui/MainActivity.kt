package com.example.hotstuffkotlin.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager.widget.ViewPager
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.ActivityMainBinding
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTheme()

        // onboard
        val viewPager = findViewById<ViewPager>(R.id.onboard_viewPager)
        val progressDots = findViewById<TabLayout>(R.id.onboard_progressDots)
//        progressDots.setupWithViewPager(viewPager, true)

        // toolbar
        toolbar = findViewById(R.id.toolbar_main)
        setSupportActionBar(toolbar)

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
                R.id.navigation_home,
                R.id.navigation_items,
                R.id.navigation_create_item,
                R.id.navigation_learn,
                R.id.navigation_settings
                -> navView.visibility = View.VISIBLE
                else -> navView.visibility = View.GONE
            }
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

//            buttonClose.setOnClickListener { bsDialog.dismiss() }

//            buttonNewItem.setOnClickListener {
//                startActivity(Intent(this, NewItemActivity::class.java))
//            }
//            buttonNewBuilding.setOnClickListener {
//                startActivity(Intent(this, NewBuildingActivity::class.java))
//            }

//            bsDialog.setCancelable(true)
//            bsDialog.setContentView(view)
//            bsDialog.show()
//            true
//        }
    }

    private fun initTheme() {
        val theme = SharedPreferenceHelper.getInstance(this).getThemePref()
        SharedPreferenceHelper.getInstance(this).applyThemePref(theme)
    }

}