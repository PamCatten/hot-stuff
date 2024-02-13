package com.example.hotstuffkotlin.ui.create

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
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
import com.example.hotstuffkotlin.models.Item
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.io.File
import java.io.FileOutputStream


class CreateItemFragment : Fragment() {

    private var _binding: FragmentCreateItemBinding? = null
    private val binding get() = _binding!!
    private var uri: Uri? = null
    private var imageFile: File? = null
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

        val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            isSaved -> if (isSaved) {
                createImage.setImageURI(uri)
                    val contentResolver = requireContext().contentResolver
                    val source = ImageDecoder.createSource(contentResolver, uri!!)
                    val bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                        decoder.setTargetSampleSize(1)
                        decoder.isMutableRequired = true
                    }

                    val fos = FileOutputStream(imageFile)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                    MediaScannerConnection.scanFile(context, arrayOf(imageFile!!.path), arrayOf(SELECT_MIME_TYPE), null)
            }
        }
        takePhotoButton?.setOnClickListener {
            try {
                val imageAlbum = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Hot Stuff")
                if (!imageAlbum.exists()) imageAlbum.mkdirs()
                imageFile = File(imageAlbum, "HS-${System.currentTimeMillis()}.jpg")
                if (imageFile!!.createNewFile()) {
                    uri = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", imageFile!!)
                    takePicture.launch(uri)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
            }
        }

        val selectPicture = registerForActivityResult(ActivityResultContracts.OpenDocument()) { resultURI ->
            if (resultURI != null) {
                try {
                    uri = resultURI
                    val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    val resolver: ContentResolver = requireActivity().contentResolver
                    resolver.takePersistableUriPermission(uri!!, takeFlags)
                    createImage.setImageURI(uri)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
        selectPhotoButton?.setOnClickListener {
            val requestedPermission = ACCESS_PERMISSION
            val checkSelfPermission = ContextCompat.checkSelfPermission(requireActivity(), requestedPermission)
            if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
                try {
                    selectPicture.launch(arrayOf(SELECT_INPUT_TYPE))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            } else if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(requestedPermission), PERMISSION_REQUEST_CODE)
                Toast.makeText(context, getText(R.string.toast_need_photo_permission), Toast.LENGTH_LONG).show()
            }
        }

        createButton?.setOnClickListener {
            fun resetForm() {
//                val inputName: String = nameText.text.toString().trim()
//                val inputQuantity: Int = quantityText.text.toString().toInt()
//                val inputCategory: String = categoryText.text.toString().trim()
//                val inputRoom: String = roomText.text.toString().trim()
//                val inputMake: String? = makeText.text?.toString()?.trim()
//                val inputValue: Double? = valueText.text?.toString()?.toDoubleOrNull()
//                val inputURI: String? = if (uri != null) uri.toString() else null
//                val inputDescription: String? = descriptionText.text?.toString()?.trim()
                val newItem = Item()
//                newItem.buildingId
                newItem.name = nameText.text.toString().trim()
                newItem.quantity = quantityText.text.toString().toInt()
                newItem.category = categoryText.text.toString().trim()
                newItem.room = roomText.text.toString().trim()
                newItem.make = makeText.text?.toString()?.trim()
                newItem.value = valueText.text?.toString()?.toDoubleOrNull()
                newItem.imageUri = if (uri != null) uri.toString() else null
                newItem.description = descriptionText.text?.toString()?.trim()


                nameText.text = null
                quantityText.text = null
                categoryText.text = null
                roomText.text = null
                valueText.text = null
                makeText.text = null
                createImage.setImageResource(R.drawable.image_default_item)
                descriptionText.text = null

                nameContainer.helperText = getText(R.string.label_required_hint)
                quantityContainer.helperText = getText(R.string.label_required_hint)
                categoryContainer.helperText = getText(R.string.label_required_hint)
                roomContainer.helperText = getText(R.string.label_required_hint)

                DatabaseHelper(requireContext()).addItem(newItem)
            }
            fun checkForm() {
                val nameCheck = (nameText.text == null) || (nameText.text.toString() == "")
                val categoryCheck = (categoryText.text == null) || (categoryText.text.toString() == "")
                val roomCheck = (roomText.text == null) || (roomText.text.toString() == "")
                val quantityNullCheck = (quantityText.text == null) || (quantityText.text.toString() == "")
                val quantityValueCheck = (quantityText.text.toString().toIntOrNull() == 0 || quantityText.text.toString() == "")

                if (nameCheck) nameText.error = getText(R.string.label_required_hint)
                if (categoryCheck) categoryText.error = getText(R.string.label_required_hint)
                if (roomCheck) roomText.error = getText(R.string.label_required_hint)
                if (quantityNullCheck) quantityText.error = getText(R.string.label_required_hint)
                if (quantityValueCheck) quantityText.error = getText(R.string.label_quantity_value_hint)

                if (nameCheck || categoryCheck || roomCheck || quantityNullCheck || quantityValueCheck) {
                    val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.dialog_alert)
                    alertDialogBuilder.setTitle(R.string.label_dialog_create_title)
                    alertDialogBuilder.setMessage(R.string.label_dialog_create_body)
                    alertDialogBuilder.setPositiveButton(getText(R.string.label_dialog_positive)) { dialog, _ -> dialog.dismiss() }
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
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_issue))))
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

    companion object {
        const val PERMISSION_REQUEST_CODE = 10
        const val ACCESS_PERMISSION = android.Manifest.permission.READ_MEDIA_IMAGES
        const val SELECT_INPUT_TYPE = "image/*"
        const val SELECT_MIME_TYPE = "image/jpg"
    }
}