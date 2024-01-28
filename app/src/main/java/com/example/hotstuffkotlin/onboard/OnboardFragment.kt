package com.example.hotstuffkotlin.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentOnboardBinding
import com.google.android.material.button.MaterialButton


class OnboardFragment : Fragment() {

    private var _binding: FragmentOnboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOnboardBinding.inflate(inflater, container, false)
        val view = binding.root

        // Disable onBack click
        requireActivity().onBackPressedDispatcher.addCallback(this) {}

        val continueButton = view.findViewById<MaterialButton>(R.id.onboard_button)
        continueButton.setOnClickListener {
            findNavController().navigate(R.id.action_onboard_to_view_pager)
        }

//        val onBoardingFinished = true
//            if(onBoardingFinished){
//                findNavController().navigate(R.id.navigation_home)
//            }else{
//                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
//            }

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_onboard, container, false)
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