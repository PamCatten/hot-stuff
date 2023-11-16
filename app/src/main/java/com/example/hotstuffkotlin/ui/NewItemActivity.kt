package com.example.hotstuffkotlin.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.ActivityNewItemBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper

class NewItemActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // appbar
        val appbar = findViewById<Toolbar>(R.id.appbar_new_item)
        setSupportActionBar(appbar)

        // form validation
        nameListener()
        quantityListener()
        binding.itemCreateButton.setOnClickListener{ submitForm() }



    }

    private fun submitForm() {
        val nameNullCheck = binding.itemNameText.text == null
        val nameValueCheck = binding.itemNameText.text.toString() == ""
        val quantityNullCheck1 = binding.itemQuantityText.text == null
        val quantityNullCheck2 = binding.itemQuantityText.text.toString() == ""
        val quantityValueCheck = binding.itemQuantityText.text.toString().toInt() == 0

        // TODO: violates DRY, don't have time to make this logic bulletproof right now
//        if (binding.itemNameText.text.toString() == "")
//            binding.itemNameContainer.helperText = "Your item must be named"
//        if (binding.itemQuantityText.text.toString() == "")
//            binding.itemQuantityContainer.helperText = "Your item must have a quantity"
//        else if (binding.itemQuantityText.text.toString().toInt() == 0)
//            binding.itemQuantityContainer.helperText = "Your item quantity cannot be less than one"
//        nameListener()
//        quantityListener()
        // end violation

        if (nameNullCheck || nameValueCheck || quantityNullCheck1 || quantityNullCheck2 || quantityValueCheck)
            invalidForm()
        else
            resetForm()
    }

    private fun invalidForm() {
        var message = ""
        if(binding.itemNameContainer.helperText != null)
            message += "\n\nName: " + binding.itemNameContainer.helperText
        if(binding.itemQuantityContainer.helperText != null)
            message += "\n\nQuantity: " + binding.itemQuantityContainer.helperText
        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("OK") {_, _ ->
                // do nothing
            }.show()
    }

    private fun resetForm() {
        val inputName : String = binding.itemNameText.text.toString().trim()
        val inputQuantity: Int = binding.itemQuantityText.text.toString().toInt()
        val inputValue : Double? = binding.itemValueText.text?.toString()?.toDoubleOrNull()
        val inputCategory : String? = binding.itemCategoryText.text?.toString()?.trim()
        val inputRoom : String? = binding.itemRoomText.text?.toString()?.trim()
        val inputMake : String? = binding.itemMakeText.text?.toString()?.trim()
        val imagePath : String? = "Example path"
        val inputDescription : String? = binding.itemDescriptionText.text?.toString()?.trim()

        binding.itemNameText.text = null
        binding.itemQuantityText.text = null
        binding.itemValueText.text = null
        binding.itemCategoryText.text = null
        binding.itemRoomText.text = null
        binding.itemMakeText.text = null
        binding.itemDescriptionText.text = null
        binding.itemNameContainer.helperText = "Required"
        binding.itemQuantityContainer.helperText = "Required"

        val db = DatabaseHelper(this, null)
        db.addItem(inputName, inputQuantity, inputCategory, inputValue,
            inputRoom, inputMake, imagePath, inputDescription)
    }

    private fun nameListener() {
        binding.itemNameText.setOnFocusChangeListener{ _, focused ->
            if (!focused)
                binding.itemNameContainer.helperText = validName()
        }
    }

    private fun quantityListener() {
        binding.itemQuantityText.setOnFocusChangeListener{ _, focused ->
            if (!focused)
                binding.itemQuantityContainer.helperText = validQuantity()
        }
    }

    private fun validName(): String? {
        val nameText = binding.itemNameText.text.toString()
        if (nameText == "") return "Your item must be named"
        return null
    }
    private fun validQuantity(): String? {
        val quantityText = binding.itemQuantityText.text.toString()
        if (quantityText == "")
            return "Your item must have a quantity"
        else if (quantityText.toInt() == 0)
            return "Your item quantity cannot be less than one"
        return null
    }
}