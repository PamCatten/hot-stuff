package com.example.hotstuffkotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentHomeBinding
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    lateinit var prefs: SharedPreferenceHelper

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // derived info
        val tvActiveBuildingValue = view?.findViewById<TextView>(R.id.tvActiveBuildingValue)
        val derivedTotal = "6,500.00"
        val currencyIcon = "$"
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