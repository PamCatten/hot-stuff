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
        categoryListener()
        roomListener()
        binding.itemCreateButton.setOnClickListener{ submitForm() }
    }

    private fun submitForm() {
        val nameNullCheck = binding.itemNameText.text == null
        val nameValueCheck = binding.itemNameText.text.toString() == ""
        val categoryNullCheck = binding.itemCategoryText.text == null
        val categoryValueCheck = binding.itemCategoryText.text.toString() == ""
        val roomNullCheck = binding.itemRoomText.text == null
        val roomValueCheck = binding.itemRoomText.text.toString() == ""
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

        if (nameNullCheck || nameValueCheck || categoryNullCheck ||
            categoryValueCheck || roomNullCheck || roomValueCheck ||
            quantityNullCheck1 || quantityNullCheck2 || quantityValueCheck)
            invalidForm()
        else
            resetForm()
    }

    private fun invalidForm() {
        var message = ""
        if(binding.itemNameContainer.helperText != null)
            message += "\n\nName: " + binding.itemNameContainer.helperText
        if(binding.itemCategoryContainer.helperText != null)
            message += "\n\nCategory: " + binding.itemCategoryContainer.helperText
        if(binding.itemRoomContainer.helperText != null)
            message += "\n\nRoom: " + binding.itemRoomContainer.helperText
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
        val inputCategory : String = binding.itemCategoryText.text.toString().trim()
        val inputRoom : String = binding.itemRoomText.text.toString().trim()
        val inputMake : String? = binding.itemMakeText.text?.toString()?.trim()
        val inputValue : Double? = binding.itemValueText.text?.toString()?.toDoubleOrNull()
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
        binding.itemCategoryContainer.helperText = "Required"
        binding.itemRoomContainer.helperText = "Required"

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
    private fun categoryListener() {
        binding.itemCategoryText.setOnFocusChangeListener{ _, focused ->
            if (!focused)
                binding.itemCategoryContainer.helperText = validCategory()
        }
    }
    private fun roomListener() {
        binding.itemRoomText.setOnFocusChangeListener{ _, focused ->
            if (!focused)
                binding.itemRoomContainer.helperText = validRoom()
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
    private fun validCategory(): String? {
        val categoryText = binding.itemCategoryText.text.toString()
        if (categoryText == "") return "Your item must have a category"
        return null
    }
    private fun validRoom(): String? {
        val roomText = binding.itemRoomText.text.toString()
        if (roomText == "") return "Your item must have a room"
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