package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.hotstuffkotlin.databinding.FragmentItemsBinding

class ItemsFragment : Fragment() {

    private var _binding: FragmentItemsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val itemsViewModel =
            ViewModelProvider(this)[ItemsViewModel::class.java]

        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textItems
        itemsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}