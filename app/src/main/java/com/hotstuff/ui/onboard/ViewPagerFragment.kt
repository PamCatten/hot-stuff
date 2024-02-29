package com.hotstuff.ui.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.hotstuff.R
import com.hotstuff.databinding.FragmentViewPagerBinding
import com.hotstuff.utils.OnboardAdapter

class ViewPagerFragment : Fragment() {
    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root

        val fragmentList = arrayListOf(FirstScreen(), SecondScreen(), ThirdScreen(), FourthScreen())
        val viewPager = view.findViewById<ViewPager2>(R.id.onboard_viewPager)
        val adapter = OnboardAdapter(fragmentList, requireActivity().supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
//        viewPager.isUserInputEnabled = false // disables swiping
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}