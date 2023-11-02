package com.example.hotstuffkotlin.ui.learn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentLearnBinding

class LearnFragment : Fragment() {

    private var _binding: FragmentLearnBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLearnBinding.inflate(inflater, container, false)
        val view = inflater.inflate(R.layout.fragment_learn, container, false)

        // walkthrough card
        val cardViewWalkthrough = view?.findViewById<ViewGroup>(R.id.learn_walkthrough_card)
        val dropdownWalkthrough = view?.findViewById<ConstraintLayout>(R.id.learn_walkthrough_header)
        val dropdownIconWalkthrough = view?.findViewById<ImageView>(R.id.learn_walkthrough_dropdown_icon)
        val groupWalkthrough = view?.findViewById<Group>(R.id.learn_walkthrough_group)

        dropdownWalkthrough?.setOnClickListener{
            if(groupWalkthrough?.visibility == View.VISIBLE && cardViewWalkthrough != null) {
                groupWalkthrough.visibility = View.GONE
                dropdownIconWalkthrough?.setImageResource(R.drawable.icon_dropdown)
            }
            else if (groupWalkthrough?.visibility == View.GONE && cardViewWalkthrough != null) {
                TransitionManager.beginDelayedTransition(cardViewWalkthrough, AutoTransition())
                groupWalkthrough.visibility = View.VISIBLE
                dropdownIconWalkthrough?.setImageResource(R.drawable.icon_dropdown_close)
            }
        }

        // resources card
        val cardViewResources = view?.findViewById<ViewGroup>(R.id.learn_resources_card)
        val dropdownResources = view?.findViewById<ConstraintLayout>(R.id.learn_resources_header)
        val dropdownIconResources = view?.findViewById<ImageView>(R.id.learn_resources_dropdown_icon)
        val groupResources = view?.findViewById<Group>(R.id.learn_resources_group)

        dropdownResources?.setOnClickListener{
            if(groupResources?.visibility == View.VISIBLE && cardViewResources != null) {
                groupResources.visibility = View.GONE
                dropdownIconResources?.setImageResource(R.drawable.icon_dropdown)
            }
            else if (groupResources?.visibility == View.GONE && cardViewResources != null) {
                TransitionManager.beginDelayedTransition(cardViewResources, AutoTransition())
                groupResources.visibility = View.VISIBLE
                dropdownIconResources?.setImageResource(R.drawable.icon_dropdown_close)
            }
        }

        // about card
        val cardViewAbout = view?.findViewById<ViewGroup>(R.id.learn_about_card)
        val dropdownAbout = view?.findViewById<ConstraintLayout>(R.id.learn_about_header)
        val dropdownIconAbout = view?.findViewById<ImageView>(R.id.learn_about_dropdown_icon)
        val groupAbout = view?.findViewById<Group>(R.id.learn_about_group)

        dropdownAbout?.setOnClickListener{
            if(groupAbout?.visibility == View.VISIBLE && cardViewAbout != null) {
                groupAbout.visibility = View.GONE
                dropdownIconAbout?.setImageResource(R.drawable.icon_dropdown)
            }
            else if (groupAbout?.visibility == View.GONE && cardViewAbout != null) {
                TransitionManager.beginDelayedTransition(cardViewAbout, AutoTransition())
                groupAbout.visibility = View.VISIBLE
                dropdownIconAbout?.setImageResource(R.drawable.icon_dropdown_close)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}