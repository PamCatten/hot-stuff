package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentSearchBinding
import com.example.hotstuffkotlin.models.Item
import com.example.hotstuffkotlin.utils.Adapter
import com.example.hotstuffkotlin.utils.DatabaseHelper

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private var loading = false
    private var searchItems = ArrayList<Item>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        val searchView = view.findViewById<SearchView>(R.id.search_search_view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchItems.clear()
        val adapter = Adapter(searchItems)
        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (!loading && visibleItemCount + pastVisibleItems >= totalItemCount) {
                        loading = true
                        val moreItems = DatabaseHelper(requireContext()).getDataRange(totalItemCount)
                        for (i in moreItems) searchItems.add(i)
                        adapter.notifyItemRangeInserted(totalItemCount, layoutManager.itemCount)
                        loading = false
                    }
                }
            }
        })

        val bundle = Bundle()
        adapter.setOnItemClickListener(object : Adapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                bundle.putInt("id", searchItems[position].itemId)
                bundle.putInt("buildingId", searchItems[position].buildingId)
                bundle.putInt("quantity", searchItems[position].quantity)
                bundle.putString("name", searchItems[position].name)
                bundle.putString("category", searchItems[position].category)
                bundle.putString("room", searchItems[position].room)
                bundle.putString("make", searchItems[position].make)
                bundle.putString("description", searchItems[position].description)
                bundle.putString("image", searchItems[position].imagePath)
                bundle.putDouble("value", searchItems[position].value ?: 0.00)
                bundle.putInt("position", position)
                bundle.putInt("delete", -1)

                childFragmentManager.beginTransaction().addToBackStack(null).commit()
                findNavController().navigate(R.id.action_search_to_item_detail, bundle)
            }
        })

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    searchItems.clear()
                    adapter.update(searchItems)
                } else {
                    searchItems.clear()
                    val items = DatabaseHelper(requireContext()).searchData("'%$newText%'")
                    for (i in items) {
                        searchItems.add(i)
                        adapter.update(searchItems)
                    }
                }
                return false
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}