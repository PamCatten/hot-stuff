package com.example.hotstuffkotlin.ui.create

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentCreateItemBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.OutputStream


class CreateItemFragment : Fragment() {

    private var _binding: FragmentCreateItemBinding? = null
    private val binding get() = _binding!!
    private lateinit var uri: Uri
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
        val takePhotoButton = view.findViewById<MaterialButton>(R.id.itemTakePhotoButton)
        val selectPhotoButton = view.findViewById<MaterialButton>(R.id.itemSelectPhotoButton)
        val createButton = view.findViewById<MaterialButton>(R.id.itemCreateButton)

        val createImage = view.findViewById<ShapeableImageView>(R.id.create_image)

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

        val galleryResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val imgUri = it.data?.data
                createImage.setImageURI(imgUri)
            }
        }

        val selectPicture = registerForActivityResult(ActivityResultContracts.GetContent()) {
            resultURI -> if (resultURI != null) createImage.setImageURI(resultURI)
        }

        val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            isSaved -> if (isSaved) {
                val path = uri.path
                createImage.setImageURI(uri)
            }
        }

        takePhotoButton?.setOnClickListener {
            val fileName = "HS_${System.currentTimeMillis()}.jpg"
            val imageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val imageFile = File(imageDirectory, fileName)

            if (imageFile.createNewFile()) {
                uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", imageFile)
                takePicture.launch(uri)
            }
        }

        selectPhotoButton?.setOnClickListener {
            val requestedPermission = ACCESS_PERMISSION
            val checkSelfPermission = ContextCompat.checkSelfPermission(requireActivity(), requestedPermission)
            if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
                try {
                    selectPicture.launch("image/*")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            } else if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(requestedPermission), PERMISSION_REQUEST_CODE)
                Toast.makeText(context, "Photos and videos access must be enabled to use this feature", Toast.LENGTH_LONG).show()
            }

//            val checkSelfPermission = ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
//            if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(),
//                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
//                try {
////                val galleryIntent = Intent(MediaStore.ACTION_PICK_IMAGES, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
////                    galleryResult.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
//                    selectPicture.launch("image/*")
////                    startActivity(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    Toast.makeText(context, "Error: $e", Toast.LENGTH_LONG).show()
//                }
//            } else {
//                Toast.makeText(context, "Error: Please allow relevant permissions to utilize this feature.", Toast.LENGTH_LONG).show()
//            }
        }

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
                val nameCheck = (nameText.text == null) || (nameText.text.toString() == "")
                val categoryCheck = (categoryText.text == null) || (categoryText.text.toString() == "")
                val roomCheck = (roomText.text == null) || (roomText.text.toString() == "")
                val quantityNullCheck = (quantityText.text == null) || (quantityText.text.toString() == "")
                val quantityValueCheck = (quantityText.text.toString().toIntOrNull() == 0 || quantityText.text.toString() == "")

                if (nameCheck) nameText.error = "Required"
                if (categoryCheck) categoryText.error = "Required"
                if (roomCheck) roomText.error = "Required"
                if (quantityNullCheck) quantityText.error = "Required"
                if (quantityValueCheck) quantityText.error = "Quantity cannot be less than one"

                if (nameCheck || categoryCheck || roomCheck || quantityNullCheck || quantityValueCheck) {
                    val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.dialog_alert)
                    alertDialogBuilder.setTitle(R.string.label_dialog_create_title)
                    alertDialogBuilder.setMessage(R.string.label_dialog_create_body)
                    alertDialogBuilder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
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
                    R.id.toolbar_main_download -> {
                        DatabaseHelper(requireContext()).exportCSV()
                        return true
                    }
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

    private fun saveBitmapToStorage(bitmap: Bitmap, uri: Uri, fileName: String) {
        var fos: OutputStream?

        val contentResolver = requireContext().contentResolver
        contentResolver.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10
        private const val ACCESS_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE
    }
}