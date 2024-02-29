package com.hotstuffkotlin.ui.onboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hotstuffkotlin.R
import com.hotstuffkotlin.databinding.FragmentOnboardBinding
import com.google.android.material.button.MaterialButton

class OnboardFragment : Fragment() {
    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        val view = binding.root

        requireActivity().onBackPressedDispatcher.addCallback(this) {} // Disable onBack click
        val buttonContinue = view.findViewById<MaterialButton>(R.id.onboard_button)
        buttonContinue.setOnClickListener {
            findNavController().navigate(R.id.action_onboard_to_view_pager)
        }
        val disclaimer = view.findViewById<TextView>(R.id.onboard_text_disclaimer)
        disclaimer.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_terms))))
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}