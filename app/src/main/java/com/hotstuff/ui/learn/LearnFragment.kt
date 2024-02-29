package com.hotstuff.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.hotstuff.R
import com.hotstuff.databinding.FragmentLearnBinding

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
            if (groupWalkthrough?.visibility == View.VISIBLE && cardViewWalkthrough != null) {
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
            if (groupResources?.visibility == View.VISIBLE && cardViewResources != null) {
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

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}