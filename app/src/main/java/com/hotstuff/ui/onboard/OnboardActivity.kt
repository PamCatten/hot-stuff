package com.hotstuff.ui.onboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.hotstuff.R
import com.hotstuff.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityOnboardBinding
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = findViewById(R.id.toolbar_onboard)
        setSupportActionBar(toolbar)

        val navController = findNavController(R.id.nav_host_fragment_activity_onboard)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_onboard,
            R.id.navigation_view_pager))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_onboard)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}