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

        val cardViewAbout = view?.findViewById<ViewGroup>(R.id.learn_about_card)
        val dropdownAbout = view?.findViewById<ConstraintLayout>(R.id.learn_about_header)
        val dropdownIconAbout = view?.findViewById<ImageView>(R.id.learn_about_dropdown_icon)
        val groupAbout = view?.findViewById<Group>(R.id.learn_about_group)

        dropdownAbout?.setOnClickListener{
            if(groupAbout?.visibility == View.VISIBLE && cardViewAbout != null) {
                TransitionManager.beginDelayedTransition(cardViewAbout, AutoTransition())
                groupAbout.visibility = View.GONE
                dropdownIconAbout?.setImageResource(R.drawable.icon_dropdown_close)
            }
            else if (groupAbout?.visibility == View.GONE && cardViewAbout != null) {
                TransitionManager.beginDelayedTransition(cardViewAbout, AutoTransition())
                groupAbout.visibility = View.VISIBLE
                dropdownIconAbout?.setImageResource(R.drawable.icon_dropdown)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}