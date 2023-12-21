package com.example.hotstuffkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentHomeBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        val totalItems = view.findViewById<TextView>(R.id.label_building_quantity)
        val totalValue = view.findViewById<TextView>(R.id.label_value_total)
        val buildingName = view.findViewById<TextView>(R.id.label_building_name)

        val derivedTotal = "1,000.00"
        val currencyIcon = "$"

        totalValue.text = "$currencyIcon$derivedTotal"
        totalItems.text = DatabaseHelper(requireContext()).tallyItems()

        //var currencyIcon = sharedPreferenceHelper.getCurrency().toString() + "_icon"
        //val currency = resources.getIdentifier(currencyIcon, "string", "hotstuffkotlin")
//        tvActiveBuildingValue?.text = "$currencyIcon$derivedTotal"
//
//        val tvActiveBuildingCount = view?.findViewById<TextView>(R.id.tvActiveBuildingCount)
//        val derivedCount = 18
//        tvActiveBuildingCount?.text = "$derivedCount items"
//
//        val tvActiveBuildingName = view?.findViewById<TextView>(R.id.tvActiveBuildingName)
//        val derivedName = "Sample House"
//        tvActiveBuildingName?.text = derivedName

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}