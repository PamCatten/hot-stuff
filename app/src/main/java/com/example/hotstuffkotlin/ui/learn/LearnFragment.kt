package com.example.hotstuffkotlin.ui.learn

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentLearnBinding
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper

class LearnFragment : Fragment() {
    private var _binding: FragmentLearnBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        val bundle = Bundle()
        val view = binding.root

        // walkthrough card
        val cardViewWalkthrough = view.findViewById<ViewGroup>(R.id.learn_walkthrough_card)
        val dropdownWalkthrough = view.findViewById<ConstraintLayout>(R.id.learn_walkthrough_header)
        val dropdownIconWalkthrough = view.findViewById<ImageView>(R.id.learn_walkthrough_dropdown_icon)
        val groupWalkthrough = view.findViewById<Group>(R.id.learn_walkthrough_group)

        dropdownWalkthrough?.setOnClickListener{
            if(groupWalkthrough?.visibility == View.VISIBLE && cardViewWalkthrough != null) {
                groupWalkthrough.visibility = View.GONE
                dropdownIconWalkthrough?.setImageResource(R.drawable.icon_dropdown)
            }
            else if (groupWalkthrough?.visibility == View.GONE && cardViewWalkthrough != null) {
                groupWalkthrough.visibility = View.VISIBLE
                dropdownIconWalkthrough?.setImageResource(R.drawable.icon_dropdown_close)
            }
        }
        val buttonWhyCare = view.findViewById<TextView>(R.id.learn_walkthrough_whyCare)
        val buttonGetStarted = view.findViewById<TextView>(R.id.learn_walkthrough_gettingStarted)
        val buttonExport = view.findViewById<TextView>(R.id.learn_walkthrough_export)

        buttonWhyCare.setOnClickListener {
            bundle.putCharSequence("article", getText(R.string.article_whyCare))
            findNavController().navigate(R.id.navigation_article, bundle)
        }
        buttonGetStarted.setOnClickListener {
            bundle.putCharSequence("article", getText(R.string.article_getStarted))
            findNavController().navigate(R.id.navigation_article, bundle)
        }
        buttonExport.setOnClickListener {
            bundle.putCharSequence("article", getText(R.string.article_export))
            findNavController().navigate(R.id.navigation_article, bundle)
        }

        // resources card
        val cardViewResources = view.findViewById<ViewGroup>(R.id.learn_resources_card)
        val dropdownResources = view.findViewById<ConstraintLayout>(R.id.learn_resources_header)
        val dropdownIconResources = view.findViewById<ImageView>(R.id.learn_resources_dropdown_icon)
        val groupResources = view.findViewById<Group>(R.id.learn_resources_group)

        dropdownResources?.setOnClickListener{
            if(groupResources?.visibility == View.VISIBLE && cardViewResources != null) {
                groupResources.visibility = View.GONE
                dropdownIconResources?.setImageResource(R.drawable.icon_dropdown)
            }
            else if (groupResources?.visibility == View.GONE && cardViewResources != null) {
                groupResources.visibility = View.VISIBLE
                dropdownIconResources?.setImageResource(R.drawable.icon_dropdown_close)
            }
        }

        val buttonDetail = view.findViewById<TextView>(R.id.learn_resources_attentionToDetail)
        val buttonJargonGlossary = view.findViewById<TextView>(R.id.learn_resources_jargonGlossary)
        val buttonAdjusters = view.findViewById<TextView>(R.id.learn_resources_adjusters)
        val buttonLikeKind = view.findViewById<TextView>(R.id.learn_resources_likeKind)
        buttonDetail.setOnClickListener {
            bundle.putCharSequence("article", getText(R.string.article_detail))
            findNavController().navigate(R.id.navigation_article, bundle)
        }
        buttonJargonGlossary.setOnClickListener{
            bundle.putCharSequence("article", getText(R.string.article_jargonGlossary))
            findNavController().navigate(R.id.navigation_article, bundle)
        }
        buttonAdjusters.setOnClickListener {
            bundle.putCharSequence("article", getText(R.string.article_adjusters))
            findNavController().navigate(R.id.navigation_article, bundle)
        }
        buttonLikeKind.setOnClickListener {
            bundle.putCharSequence("article", getText(R.string.article_likeKind))
            findNavController().navigate(R.id.navigation_article, bundle)
        }

        // onboard test DELETE WHEN DONE TESTING
        SharedPreferenceHelper.getInstance(requireContext()).testOnboard()
        // END

        // about card
        val cardViewAbout = view.findViewById<ViewGroup>(R.id.learn_about_card)
        val dropdownAbout = view.findViewById<ConstraintLayout>(R.id.learn_about_header)
        val dropdownIconAbout = view.findViewById<ImageView>(R.id.learn_about_dropdown_icon)
        val groupAbout = view.findViewById<Group>(R.id.learn_about_group)

        dropdownAbout?.setOnClickListener{
            if(groupAbout?.visibility == View.VISIBLE && cardViewAbout != null) {
                groupAbout.visibility = View.GONE
                dropdownIconAbout?.setImageResource(R.drawable.icon_dropdown)
            }
            else if (groupAbout?.visibility == View.GONE && cardViewAbout != null) {
                groupAbout.visibility = View.VISIBLE
                dropdownIconAbout?.setImageResource(R.drawable.icon_dropdown_close)
            }
        }

        val buttonSourceCode = view.findViewById<TextView>(R.id.learn_about_sourceCode)
        val buttonAcknowledgements = view.findViewById<TextView>(R.id.learn_about_acknowledgements)
        val buttonTerms = view.findViewById<TextView>(R.id.learn_about_terms)

        buttonSourceCode.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_repo))))
        }
        buttonAcknowledgements.setOnClickListener{
            bundle.putCharSequence("article", getText(R.string.article_acknowledgements))
            findNavController().navigate(R.id.navigation_article, bundle)
        }
        buttonTerms.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_terms))))
        }

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.toolbar_main_feedback,
                    R.id.toolbar_main_report -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.link_issue))))
                        true
                    }
                    R.id.toolbar_main_rate -> { true }
                    else -> true
                }
            }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(false)
                menu.findItem(R.id.toolbar_main_report).setVisible(true)
                menu.findItem(R.id.toolbar_main_rate).setVisible(true)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(true)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}