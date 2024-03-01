package com.hotstuff.ui.items

import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.media.MediaScannerConnection
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hotstuff.R
import com.hotstuff.databinding.FragmentEditItemBinding
import com.hotstuff.utils.DatabaseHelper
import java.io.File
import java.io.FileOutputStream

class EditItemFragment : Fragment() {

    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!
    private var imageFile: File? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        val view = binding.root

        val bundle = this.requireArguments()

        val name = view.findViewById<TextInputEditText>(R.id.edit_name_text)
        val quantity = view.findViewById<TextInputEditText>(R.id.edit_quantity_text)
        val category = view.findViewById<MaterialAutoCompleteTextView>(R.id.edit_category_text)
        val room = view.findViewById<MaterialAutoCompleteTextView>(R.id.edit_room_text)
        val value = view.findViewById<TextInputEditText>(R.id.edit_value_text)
        val make = view.findViewById<TextInputEditText>(R.id.edit_make_text)
        val description = view.findViewById<TextInputEditText>(R.id.edit_description_text)

        val nameContainer = view.findViewById<TextInputLayout>(R.id.edit_name_container)
        val quantityContainer = view.findViewById<TextInputLayout>(R.id.edit_quantity_container)
        val categoryContainer = view.findViewById<TextInputLayout>(R.id.edit_category_container)
        val roomContainer = view.findViewById<TextInputLayout>(R.id.edit_room_container)

        val editImage = view.findViewById<ShapeableImageView>(R.id.edit_item_image)
        val cameraButton = view.findViewById<MaterialButton>(R.id.button_edit_take)
        val selectButton = view.findViewById<MaterialButton>(R.id.button_edit_select)
        val saveButton = view.findViewById<MaterialButton>(R.id.button_edit_save)

        val id: Int = (bundle.getInt("id"))
        name.setText(bundle.getString("name"))
        category.setText(bundle.getString("category"))
        room.setText(bundle.getString("room"))
        quantity.setText(bundle.getInt("quantity").toString())

        if (bundle.getDouble("value") != 0.00) value.setText(bundle.getDouble("value").toString())
        else value.text = null

        if (bundle.getString("make") != null) make.setText(bundle.getString("make"))
        else make.setText(R.string.filler_unspecified)

        var imageURI = bundle.getString("image")?.toUri()
        if (imageURI != null) editImage.setImageURI(imageURI)

        if (bundle.getString("description") != null) description.setText(bundle.getString("description"))
        else description.setText(R.string.filler_unspecified)

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

        val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) {
            isSaved -> if (isSaved) {
                editImage.setImageURI(imageURI)
                val contentResolver = requireContext().contentResolver
                val source = ImageDecoder.createSource(contentResolver, imageURI!!)
                val bitmap = ImageDecoder.decodeBitmap(source) { decoder, _, _ ->
                    decoder.setTargetSampleSize(1)
                    decoder.isMutableRequired = true
                }

                val fos = FileOutputStream(imageFile)
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                fos.flush()
                fos.close()
                MediaScannerConnection.scanFile(context, arrayOf(imageFile!!.path), arrayOf(
                    com.hotstuff.ui.create.CreateItemFragment.SELECT_MIME_TYPE), null)
            }
        }
        cameraButton?.setOnClickListener {
            try {
                val imageAlbum = File(Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    getString(R.string.app_name))
                if (!imageAlbum.exists()) imageAlbum.mkdirs()
                imageFile = File(imageAlbum, "HS-${System.currentTimeMillis()}.jpg")
                if (imageFile!!.createNewFile()) {
                    imageURI = FileProvider.getUriForFile(requireContext(), "${requireContext().packageName}.provider", imageFile!!)
                    takePicture.launch(imageURI)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
            }
        }

        val selectPicture = registerForActivityResult(ActivityResultContracts.OpenDocument()) { resultURI ->
            if (resultURI != null) {
                try {
                    imageURI = resultURI
                    val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    val resolver: ContentResolver = requireActivity().contentResolver
                    resolver.takePersistableUriPermission(imageURI!!, takeFlags)
                    editImage.setImageURI(imageURI)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            }
        }
        selectButton?.setOnClickListener {
            val requestedPermission = com.hotstuff.ui.create.CreateItemFragment.ACCESS_PERMISSION
            val checkSelfPermission = ContextCompat.checkSelfPermission(requireActivity(), requestedPermission)
            if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
                try {
                    selectPicture.launch(arrayOf(com.hotstuff.ui.create.CreateItemFragment.SELECT_INPUT_TYPE))
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error: $e", Toast.LENGTH_SHORT).show()
                }
            } else if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(requestedPermission),
                    com.hotstuff.ui.create.CreateItemFragment.PERMISSION_REQUEST_CODE
                )
                Toast.makeText(context, getText(R.string.toast_need_photo_permission), Toast.LENGTH_LONG).show()
            }
        }
        saveButton?.setOnClickListener {
            fun resetForm() {
                val item = com.hotstuff.models.Item()
                item.id = id
//                item.buildingId =
                item.name = name.text.toString().trim()
                item.quantity = quantity.text.toString().toInt()
                item.category = category.text.toString().trim()
                item.room = room.text.toString().trim()
                item.make = make.text?.toString()?.trim()
                item.value = value.text?.toString()?.toDoubleOrNull()
                item.imageUri = if (imageURI != null) imageURI.toString() else null
                item.description = description.text?.toString()?.trim()

                name.text = null
                quantity.text = null
                category.text = null
                room.text = null
                value.text = null
                make.text = null
                description.text = null
                nameContainer.helperText = getText(R.string.label_required_hint)
                quantityContainer.helperText = getText(R.string.label_required_hint)
                categoryContainer.helperText = getText(R.string.label_required_hint)
                roomContainer.helperText = getText(R.string.label_required_hint)

                bundle.putInt("id", id)
//                bundle.putInt("buildingId", buildingId)
                bundle.putInt("quantity", item.quantity)
                bundle.putString("name", item.name)
                bundle.putString("category", item.category)
                bundle.putString("room", item.room)
                bundle.putString("make", item.make)
                bundle.putDouble("value", item.value ?: 0.00)
                bundle.putString("image", item.imageUri)
                bundle.putString("description", item.description)

                DatabaseHelper(requireContext()).updateItem(item)
                findNavController().navigate(R.id.action_edit_item_to_item_detail, bundle)
            }
            fun checkForm() {
                val nameCheck = name.text == null || name.text.toString() == ""
                val categoryCheck = category.text == null || category.text.toString() == ""
                val roomCheck = room.text == null || room.text.toString() == ""
                val quantityNullCheck = quantity.text == null || quantity.text.toString() == ""
                val quantityValueCheck = quantity.text.toString().toInt() == 0

                if (nameCheck || categoryCheck || roomCheck || quantityNullCheck || quantityValueCheck) {
                    val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.dialog_alert)
                    alertDialogBuilder.setTitle(R.string.dialog_create_item_title)
                    alertDialogBuilder.setMessage(R.string.dialog_create_item_message)
                    alertDialogBuilder.setPositiveButton(getText(R.string.dialog_positive)) { dialog, _ -> dialog.dismiss() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
                else resetForm()
            }
            checkForm()

        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}