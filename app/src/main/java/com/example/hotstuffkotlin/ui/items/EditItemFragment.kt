package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentEditItemBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class EditItemFragment : Fragment() {

    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        val view = binding.root

        val bundle = this.requireArguments()

        val name = view.findViewById<TextInputEditText>(R.id.itemNameText)
        val quantity = view.findViewById<TextInputEditText>(R.id.itemQuantityText)
        val category = view.findViewById<TextInputEditText>(R.id.itemCategoryText)
        val room = view.findViewById<TextInputEditText>(R.id.itemRoomText)
        val value = view.findViewById<TextInputEditText>(R.id.itemValueText)
        val make = view.findViewById<TextInputEditText>(R.id.itemMakeText)
        val description = view.findViewById<TextInputEditText>(R.id.itemDescriptionText)

        val nameContainer = view.findViewById<TextInputLayout>(R.id.itemNameContainer)
        val quantityContainer = view.findViewById<TextInputLayout>(R.id.itemQuantityContainer)
        val categoryContainer = view.findViewById<TextInputLayout>(R.id.itemCategoryContainer)
        val roomContainer = view.findViewById<TextInputLayout>(R.id.itemRoomContainer)
        val saveButton = view.findViewById<MaterialButton>(R.id.itemSaveButton)

        val id: Int = (bundle.getInt("id"))
        name.setText(bundle.getString("name"))
        category.setText(bundle.getString("category"))
        room.setText(bundle.getString("room"))
        quantity.setText(bundle.getInt("quantity").toString())

        if (bundle.getDouble("value") != 0.00) value.setText(bundle.getDouble("value").toString())
        else value.text = null

        if (bundle.getString("make") != null) make.setText(bundle.getString("make"))
        else make.setText(R.string.unspecified_value_filler)

        if (bundle.getString("description") != null) description.setText(bundle.getString("description"))
        else description.setText(R.string.unspecified_value_filler)

        name.setOnFocusChangeListener { _, focused ->
            fun validName(): String? {
                name.error = null
                return if (name.text.toString() == "" || name.text == null) "Required"
                else null
            }
            if (!focused) nameContainer.helperText = validName()
        }

        quantity.setOnFocusChangeListener { _, focused ->
            fun validQuantity(): String? {
                quantity.error = null
                return if (quantity.text.toString() == "" || quantity.text == null) "Required"
                else if (quantity.text.toString().toInt() == 0) "Quantity cannot be less than one"
                else null
            }
            if (!focused) quantityContainer.helperText = validQuantity()
        }

        category.setOnFocusChangeListener { _, focused ->
            fun validCategory(): String? {
                category.error = null
                return if (category.text.toString() == "" || category.text == null) "Required"
                else null
            }
            if (!focused) categoryContainer.helperText = validCategory()
        }

        room.setOnFocusChangeListener { _, focused ->
            fun validRoom(): String? {
                room.error = null
                return if (room.text.toString() == "" || room.text == null) "Required"
                else null
            }
            if (!focused) roomContainer.helperText = validRoom()
        }

        saveButton?.setOnClickListener {
            fun resetForm() {
                val inputName: String = name.text.toString().trim()
                val inputQuantity: Int = quantity.text.toString().toInt()
                val inputCategory: String = category.text.toString().trim()
                val inputRoom: String = room.text.toString().trim()
                val inputMake: String? = make.text?.toString()?.trim()
                val inputValue: Double? = value.text?.toString()?.toDoubleOrNull()
                val imagePath: String = "Example path" // TODO: Fix placeholder, Add take/select image support
                val inputDescription: String? = description.text?.toString()?.trim()

                name.text = null
                quantity.text = null
                category.text = null
                room.text = null
                value.text = null
                make.text = null
                description.text = null

                nameContainer.helperText = "Required"
                quantityContainer.helperText = "Required"
                categoryContainer.helperText = "Required"
                roomContainer.helperText = "Required"

                bundle.putInt("id", id)
//                bundle.putInt("buildingId", buildingId)
                bundle.putInt("quantity", inputQuantity)
                bundle.putString("name", inputName)
                bundle.putString("category", inputCategory)
                bundle.putString("room", inputRoom)
                bundle.putString("make", inputMake)
                bundle.putString("description", inputDescription)
                bundle.putString("image", imagePath)
                bundle.putDouble("value", inputValue ?: 0.00)

                DatabaseHelper(requireContext()).updateItem( id=id,
                    name=inputName, quantity=inputQuantity, category=inputCategory, room=inputRoom,
                    value=inputValue,make=inputMake, image=imagePath, description=inputDescription
                )

                findNavController().navigate(R.id.action_edit_item_to_item_detail, bundle)
            }

            fun checkForm() {
                val nameCheck = name.text == null || name.text.toString() == ""
                val categoryCheck = category.text == null || category.text.toString() == ""
                val roomCheck = room.text == null || room.text.toString() == ""
                val quantityNullCheck = quantity.text == null || quantity.text.toString() == ""
                val quantityValueCheck = quantity.text.toString().toInt() == 0

                if (nameCheck) name.error = "Required"
                if (categoryCheck) category.error = "Required"
                if (roomCheck) room.error = "Required"
                if (quantityNullCheck) quantity.error = "Required"
                if (quantityValueCheck) quantity.error = "Quantity cannot be less than one"

                if (nameCheck || categoryCheck || roomCheck || quantityNullCheck || quantityValueCheck) return
                else resetForm()
            }
            checkForm()


//            val callback = object : OnBackPressedCallback(
//        true // default to enabled
//            ) {
//                override fun handleOnBackPressed() {
//                    navigateBack()
//                }
//            }
//            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        }
        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean { return true }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_report).setVisible(false)
                menu.findItem(R.id.toolbar_main_rate).setVisible(false)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(false)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}