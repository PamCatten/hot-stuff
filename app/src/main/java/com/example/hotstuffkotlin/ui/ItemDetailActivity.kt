package com.example.hotstuffkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.ActivityItemDetailBinding

class ItemDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityItemDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_item_detail)

        if (intent.hasExtra("name")) title = intent.getStringExtra("name")

        // appbar
        val appbar = findViewById<Toolbar>(R.id.appbar_item_detail)
        setSupportActionBar(appbar)
    }
}