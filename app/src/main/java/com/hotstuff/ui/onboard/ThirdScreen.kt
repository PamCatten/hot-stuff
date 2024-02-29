package com.hotstuff.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hotstuff.R
import com.hotstuff.databinding.FragmentOnboardThreeBinding
import com.google.android.material.button.MaterialButton

class ThirdScreen : Fragment() {
    private var _binding: FragmentOnboardThreeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnboardThreeBinding.inflate(inflater, container, false)
        val view = binding.root

        val viewPager = activity?.findViewById<ViewPager2>(R.id.onboard_viewPager)
        val continueButton = view.findViewById<MaterialButton>(R.id.onboard_three_continue_button)
        continueButton.setOnClickListener {
            viewPager?.currentItem = 3
        }
        val skipButton = view.findViewById<MaterialButton>(R.id.onboard_three_skip_button)
        skipButton.setOnClickListener {
            viewPager?.currentItem = 3
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}