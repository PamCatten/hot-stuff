package com.example.hotstuffkotlin.onboard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

        val buttonContinue = view.findViewById<MaterialButton>(R.id.onboard_button)
        buttonContinue.setOnClickListener {
            findNavController().navigate(R.id.action_onboard_to_view_pager)
        }

        val disclaimer = view.findViewById<TextView>(R.id.onboard_text_disclaimer)
        disclaimer.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.learn_eula_link))))
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