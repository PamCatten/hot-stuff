package com.example.hotstuffkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class NewItemActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_item)

        // appbar
        val appbar = findViewById<Toolbar>(R.id.appbar_new_item)
        setSupportActionBar(appbar)
    }
}