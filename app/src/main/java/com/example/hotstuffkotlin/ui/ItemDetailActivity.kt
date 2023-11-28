package com.example.hotstuffkotlin.ui

import android.os.Bundle
import android.widget.TextView
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

        val name = findViewById<TextView>(R.id.item_detail_name_text)
        val category = findViewById<TextView>(R.id.item_detail_category_text)
        val room = findViewById<TextView>(R.id.item_detail_room_text)
        val description = findViewById<TextView>(R.id.item_detail_description_text)
        val make = findViewById<TextView>(R.id.item_detail_make_text)
        val value = findViewById<TextView>(R.id.item_detail_value_text)
        val quantity = findViewById<TextView>(R.id.item_detail_quantity_text)

        val valueNumeral = intent.getDoubleExtra("value", 0.00).toString()
        val valueCurrency = "$" // get from stored preference value

        //        if (intent.hasExtra("name")) title = intent.getStringExtra("name")
        title = ""

        name.text = intent.getStringExtra("name")
        category.text = intent.getStringExtra("category")
        room.text = intent.getStringExtra("room")
        quantity.text = intent.getStringExtra("quantity")

        if (intent.hasExtra("value")) value.text = "$valueCurrency$valueNumeral"
        else value.text = "Unspecified"

        if (intent.hasExtra("make")) make.text = intent.getStringExtra("make")
        else make.text = "Unspecified"

        if (intent.hasExtra("description")) description.text = intent.getStringExtra("description")
        else description.text = "Unspecified"

        // appbar
        val appbar = findViewById<Toolbar>(R.id.appbar_item_detail)
        setSupportActionBar(appbar)
    }
}