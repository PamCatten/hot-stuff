package com.example.hotstuffkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.ActivityNewBuildingBinding

class NewBuildingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewBuildingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewBuildingBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_new_building)

        // appbar
        val appbar = findViewById<Toolbar>(R.id.appbar_new_building)
        setSupportActionBar(appbar)
    }
}