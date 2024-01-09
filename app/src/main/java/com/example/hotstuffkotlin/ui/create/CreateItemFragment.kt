package com.example.hotstuffkotlin.ui.create

import android.content.Intent
import android.net.Uri
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
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentCreateItemBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CreateItemFragment : Fragment() {

    private var _binding: FragmentCreateItemBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateItemBinding.inflate(inflater, container, false)
        val view = binding.root

        val nameText = view.findViewById<TextInputEditText>(R.id.itemNameText)
        val nameContainer = view.findViewById<TextInputLayout>(R.id.itemNameContainer)
        val quantityText = view.findViewById<TextInputEditText>(R.id.itemQuantityText)
        val quantityContainer = view.findViewById<TextInputLayout>(R.id.itemQuantityContainer)
        val categoryText = view.findViewById<TextInputEditText>(R.id.itemCategoryText)
        val categoryContainer = view.findViewById<TextInputLayout>(R.id.itemCategoryContainer)
        val roomText = view.findViewById<TextInputEditText>(R.id.itemRoomText)
        val roomContainer = view.findViewById<TextInputLayout>(R.id.itemRoomContainer)
        val valueText = view.findViewById<TextInputEditText>(R.id.itemValueText)
        val makeText = view.findViewById<TextInputEditText>(R.id.itemMakeText)
        val descriptionText = view.findViewById<TextInputEditText>(R.id.itemDescriptionText)
        val photoButton = view.findViewById<MaterialButton>(R.id.itemPhotoButton)
        val createButton = view.findViewById<MaterialButton>(R.id.itemCreateButton)

        nameText.setOnFocusChangeListener { _, focused ->
            fun validName(): String? {
                nameText.error = null
                return if (nameText.text.toString() == "" || nameText.text == null) "Required"
                else null
            }
            if (!focused) nameContainer.helperText = validName()
        }

        quantityText.setOnFocusChangeListener { _, focused ->
            fun validQuantity(): String? {
                quantityText.error = null
                return if (quantityText.text.toString() == "" || quantityText.text == null) "Required"
                else if (quantityText.text.toString().toInt() == 0) "Quantity cannot be less than one"
                else null
            }
            if (!focused) quantityContainer.helperText = validQuantity()
        }

        categoryText.setOnFocusChangeListener { _, focused ->
            fun validCategory(): String? {
                categoryText.error = null
                return if (categoryText.text.toString() == "" || categoryText.text == null) "Required"
                else null
            }
            if (!focused) categoryContainer.helperText = validCategory()
        }

        roomText.setOnFocusChangeListener { _, focused ->
            fun validRoom(): String? {
                roomText.error = null
                return if (roomText.text.toString() == "" || roomText.text == null) "Required"
                else null
            }
            if (!focused) roomContainer.helperText = validRoom()
        }

//        val photo = view.findViewById<ShapeableImageView>(R.id.create_image)
//        photoButton?.setOnClickListener {
//            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
//                if (isSuccess) { photo.setImageURI()
//                }
//            }
//        }

        createButton?.setOnClickListener {
            fun resetForm() {
                val inputName: String = nameText.text.toString().trim()
                val inputQuantity: Int = quantityText.text.toString().toInt()
                val inputCategory: String = categoryText.text.toString().trim()
                val inputRoom: String = roomText.text.toString().trim()
                val inputMake: String? = makeText.text?.toString()?.trim()
                val inputValue: Double? = valueText.text?.toString()?.toDoubleOrNull()
                val imagePath: String = "Example path" // TODO: Fix placeholder, Add take/select image support
                val inputDescription: String? = descriptionText.text?.toString()?.trim()

                nameText.text = null
                quantityText.text = null
                categoryText.text = null
                roomText.text = null
                valueText.text = null
                makeText.text = null
                descriptionText.text = null

                nameContainer.helperText = "Required"
                quantityContainer.helperText = "Required"
                categoryContainer.helperText = "Required"
                roomContainer.helperText = "Required"

                DatabaseHelper(requireContext()).addItem(
                    name=inputName, quantity=inputQuantity, category=inputCategory, room=inputRoom,
                    value=inputValue,make=inputMake, image=imagePath, description=inputDescription
                )
            }

            fun checkForm() {
                val nameCheck = nameText.text == null || nameText.text.toString() == ""
                val categoryCheck = categoryText.text == null || categoryText.text.toString() == ""
                val roomCheck = roomText.text == null || roomText.text.toString() == ""
                val quantityNullCheck = quantityText.text == null || quantityText.text.toString() == ""
                val quantityValueCheck = quantityText.text.toString().toInt() == 0

                if (nameCheck) nameText.error = "Required"
                if (categoryCheck) categoryText.error = "Required"
                if (roomCheck) roomText.error = "Required"
                if (quantityNullCheck) quantityText.error = "Required"
                if (quantityValueCheck) quantityText.error = "Quantity cannot be less than one"

                if (nameCheck || categoryCheck || roomCheck || quantityNullCheck || quantityValueCheck) return
                else resetForm()
            }
            checkForm()
        }

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.toolbar_main_search -> { return true }
                    R.id.toolbar_main_download -> { return true }
                    R.id.toolbar_main_report -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.toolbar_issue_link))))
                        return true
                    }
                    R.id.toolbar_main_rate -> { return true }
                    R.id.toolbar_main_feedback -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        // TODO: Find an alternative way to extract these
                        intent.data = Uri.parse(
                            "mailto:campatten.dev@outlook.com" +
                                    "?subject=FEEDBACK: (Your Suggestion)" +
                                    "&body=Hey! Thanks for helping me improve Hot Stuff. Just a quick heads up, please make sure 'feedback' is somewhere in the subject of your suggestion so it ends up where I can see it! \n\n Much love, \nCam"
                        )
                        startActivity(intent)
                        return true
                    }
                    else -> return true
                }
            }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(true)
                menu.findItem(R.id.toolbar_main_report).setVisible(true)
                menu.findItem(R.id.toolbar_main_rate).setVisible(true)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(true)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}