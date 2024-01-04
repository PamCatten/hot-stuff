package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemsBinding
import com.example.hotstuffkotlin.utils.Adapter
import com.example.hotstuffkotlin.utils.DatabaseHelper

class ItemsFragment : Fragment() {
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!
    private var loading = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val view = binding.root
//        val items = DatabaseHelper(requireContext()).getItems()
        val items = DatabaseHelper(requireContext()).getDataRange()
        val recyclerItems = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        recyclerItems.layoutManager = LinearLayoutManager(context)

        val adapter = Adapter(items)
        recyclerItems.adapter = adapter

        recyclerItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerItems.layoutManager as LinearLayoutManager
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (!loading && visibleItemCount + pastVisibleItems >= totalItemCount) {
                        loading = true
                        val moreItems = DatabaseHelper(requireContext()).getDataRange(totalItemCount)
                        for (i in moreItems) items.add(i)
                        adapter.notifyItemRangeInserted(totalItemCount, layoutManager.itemCount)
                        loading = false
                    }
                }
            }
        })

        val bundle = Bundle()
        adapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                bundle.putInt("id", items[position].itemId)
                bundle.putInt("buildingId", items[position].buildingId)
                bundle.putInt("quantity", items[position].quantity)
                bundle.putString("name", items[position].name)
                bundle.putString("category", items[position].category)
                bundle.putString("room", items[position].room)
                bundle.putString("make", items[position].make)
                bundle.putString("description", items[position].description)
                bundle.putString("image", items[position].imagePath)
                bundle.putDouble("value", items[position].value ?: 0.00)
                bundle.putInt("position", position)
                bundle.putInt("delete", -1)

                childFragmentManager.beginTransaction().addToBackStack(null).commit()
                findNavController().navigate(R.id.action_items_to_item_detail, bundle)
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}