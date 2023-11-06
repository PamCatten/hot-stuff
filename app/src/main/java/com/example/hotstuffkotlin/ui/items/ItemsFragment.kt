package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemsBinding
import com.example.hotstuffkotlin.utils.Adapter

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
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        // MenuProvider
        val view = inflater.inflate(R.layout.fragment_items, container, false)
        getData(view)

        return view
    }

    private fun getData(view : View) {
        val recyclerItems = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        val data = ArrayList<CardViewModel>()
        recyclerItems.layoutManager = LinearLayoutManager(context)
        for (i in 1..20) {
            data.add(CardViewModel(R.drawable.icon_items, "Item Name #$i"))
        }
        val adapter = Adapter(data)
        recyclerItems.adapter = adapter
        adapter.setOnItemClickListener(object : Adapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(activity, "You clicked on item $position", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}