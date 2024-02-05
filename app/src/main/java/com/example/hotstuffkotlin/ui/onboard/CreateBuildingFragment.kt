package com.example.hotstuffkotlin.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.addCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentCreateBuildingBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
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

        // Disable onBack click
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        val buildingName = view.findViewById<TextInputLayout>(R.id.create_building_name_container)
        val buildingNameText = view.findViewById<TextInputEditText>(R.id.create_building_name_field)
        val buildingType = view.findViewById<TextInputLayout>(R.id.create_building_type_container)
        val buildingTypeText = view.findViewById<MaterialAutoCompleteTextView>(R.id.create_building_type_field)
        val buildingDescription = view.findViewById<TextInputLayout>(R.id.create_building_desc_container)
        val buildingDescriptionText = view.findViewById<TextInputEditText>(R.id.building_create_desc_field)
        val createBuildingButton = view.findViewById<MaterialButton>(R.id.building_create_button)

        val adapter = ArrayAdapter(requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
            resources.getStringArray(R.array.dropdown_building_type))
        buildingTypeText.setAdapter(adapter)

        buildingNameText.setOnFocusChangeListener { _, focused ->
            fun validName(): String? {
                buildingNameText.error = null
                return if (buildingNameText.text.toString() == "" || buildingNameText.text == null) "Required"
                else null
            }
            if (!focused) buildingName.helperText = validName()
        }
//        buildingTypeText.setOnFocusChangeListener { _, focused ->
//            fun validType(): String? {
//                buildingTypeText.error = null
//                return if (buildingTypeText.text.isEmpty()) "Required"
//                else null
//            }
//            if (!focused) buildingType.helperText = validType()
//        }

        createBuildingButton.setOnClickListener {
            fun confirmForm() {
                DatabaseHelper(requireContext()).addBuilding(
                    name = buildingNameText.text.toString().trim(),
                    type = buildingTypeText.text.toString().trim(),
                    description = buildingDescriptionText.text.toString().trim())

                SharedPreferenceHelper.getInstance(requireContext()).finishOnboarding()
                findNavController().navigate(R.id.action_create_building_to_main_activity)
                requireActivity().finish()
            }
            fun checkForm() {
                val nameCheck = (buildingNameText.text == null) || (buildingNameText.text.toString() == "")
//                val typeCheck = buildingTypeText.text.isEmpty()

                if (nameCheck) buildingNameText.error = getText(R.string.label_required_hint)
//                if (typeCheck) buildingTypeText.error = getText(R.string.label_required_hint)

                if (nameCheck) {
                    val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.dialog_alert)
                    alertDialogBuilder.setTitle(R.string.label_dialog_create_title)
                    alertDialogBuilder.setMessage(R.string.label_dialog_create_building_body)
                    alertDialogBuilder.setPositiveButton(getText(R.string.label_dialog_positive)) { dialog, _ -> dialog.dismiss() }
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                } else confirmForm()
            }
            checkForm()
        }

//
//    private fun onBoardingFinished(): Boolean{
//        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
//        return sharedPref.getBoolean("Finished", false)
//    }

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean { return true }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(false)
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