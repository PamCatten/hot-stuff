package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}