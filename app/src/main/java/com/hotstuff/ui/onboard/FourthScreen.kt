package com.hotstuff.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hotstuff.R
import com.hotstuff.databinding.FragmentOnboardFourBinding
import com.google.android.material.button.MaterialButton

class FourthScreen : Fragment() {
    private var _binding: FragmentOnboardFourBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnboardFourBinding.inflate(inflater, container, false)
        val view = binding.root

        val continueButton = view.findViewById<MaterialButton>(R.id.onboard_four_continue_button)
        continueButton.setOnClickListener { findNavController()
            .navigate(R.id.action_view_pager_to_create_building)
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}