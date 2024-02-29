package com.hotstuff.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hotstuff.R
import com.hotstuff.databinding.FragmentSettingsContainerBinding

class SettingsContainerFragment : Fragment() {
    private var _binding: FragmentSettingsContainerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsContainerBinding.inflate(inflater, container, false)
        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.settings_frame, SettingsFragment()).commit()
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}