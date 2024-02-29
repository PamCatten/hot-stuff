package com.hotstuffkotlin.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hotstuffkotlin.R
import com.hotstuffkotlin.databinding.FragmentCreateBuildingBinding
import com.hotstuffkotlin.models.Building
import com.hotstuffkotlin.utils.DatabaseHelper
import com.hotstuffkotlin.utils.PreferenceHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CreateBuildingFragment : Fragment() {

    private var _binding: FragmentCreateBuildingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCreateBuildingBinding.inflate(inflater, container, false)
        val view = binding.root

        val context = requireContext()
        val preferenceHelper = PreferenceHelper(context)
        requireActivity().onBackPressedDispatcher.addCallback(this) {} // Disable onBack click

        val buildingName = view.findViewById<TextInputLayout>(R.id.create_building_name_container)
        val buildingNameText = view.findViewById<TextInputEditText>(R.id.create_building_name_field)
//        val buildingType = view.findViewById<TextInputLayout>(R.id.create_building_type_container)
        val buildingTypeText = view.findViewById<MaterialAutoCompleteTextView>(R.id.create_building_type_field)
//        val buildingDescription = view.findViewById<TextInputLayout>(R.id.create_building_desc_container)
        val buildingDescriptionText = view.findViewById<TextInputEditText>(R.id.create_building_desc_field)
        val createBuildingButton = view.findViewById<MaterialButton>(R.id.create_building_button)
        val adapter = ArrayAdapter(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.building_type))
        buildingTypeText.setAdapter(adapter)

        buildingNameText.setOnFocusChangeListener { _, focused ->
            fun validName(): String? {
                buildingNameText.error = null
                return if (buildingNameText.text.toString() == "" || buildingNameText.text == null) "Required"
                else null
            }
            if (!focused) buildingName.helperText = validName()
        }
        createBuildingButton.setOnClickListener {
            fun confirmForm() {
                val newBuilding = com.hotstuffkotlin.models.Building()
                newBuilding.name = buildingNameText.text.toString().trim()
                newBuilding.type = buildingTypeText.text.toString().trim()
                newBuilding.description = buildingDescriptionText.text.toString().trim()
                DatabaseHelper(context).addBuilding(newBuilding)

                preferenceHelper
                    .putStringPref(getString(R.string.key_buildingName), newBuilding.name)
                preferenceHelper
                    .putStringPref(getString(R.string.key_buildingType), newBuilding.type!!)
                preferenceHelper
                    .putStringPref(getString(R.string.key_buildingDesc), newBuilding.description!!)
                preferenceHelper.putBooleanPref(getString(R.string.key_onboard), false)
                findNavController().navigate(R.id.action_create_building_to_main_activity)
                requireActivity().finish()
            }
            fun checkForm() {
                val nameCheck = (buildingNameText.text == null) || (buildingNameText.text.toString() == "")
                if (nameCheck) {
                    buildingNameText.error = getText(R.string.label_required_hint)
                    val alertDialogBuilder = MaterialAlertDialogBuilder(context, R.style.dialog_alert)
                    alertDialogBuilder.setTitle(R.string.dialog_create_item_title)
                    alertDialogBuilder.setMessage(R.string.dialog_create_building_message)
                    alertDialogBuilder.setPositiveButton(getText(R.string.dialog_positive)) { dialog, _ -> dialog.dismiss() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                } else confirmForm()
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