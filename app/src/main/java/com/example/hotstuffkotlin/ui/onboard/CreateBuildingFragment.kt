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
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentCreateBuildingBinding
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

        val buildingName = view.findViewById<TextInputLayout>(R.id.building_name_container)
        val buildingNameText = view.findViewById<TextInputEditText>(R.id.building_name_field)
        val buildingType = view.findViewById<TextInputLayout>(R.id.building_type_container)
        val buildingTypeText = view.findViewById<MaterialAutoCompleteTextView>(R.id.building_type_field)
        val buildingDescription = view.findViewById<TextInputLayout>(R.id.building_description_container)
        val buildingDescriptionText = view.findViewById<TextInputEditText>(R.id.building_description_field)

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
        buildingTypeText.setOnFocusChangeListener { _, focused ->
            fun validCategory(): String? {
                buildingTypeText.error = null
                return if (buildingTypeText.text == null) "Required"
                else null
            }
            if (!focused) buildingType.helperText = validCategory()
        }
//        roomText.setOnFocusChangeListener { _, focused ->
//            fun validRoom(): String? {
//                roomText.error = null
//                return if (roomText.text.toString() == "" || roomText.text == null) "Required"
//                else null
//            }
//            if (!focused) roomContainer.helperText = validRoom()
//        }

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