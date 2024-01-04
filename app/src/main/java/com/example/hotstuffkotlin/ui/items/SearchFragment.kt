package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root

        var searchItems = ArrayList<Item>()

        val searchView = view.findViewById<SearchView>(R.id.search_search_view)
        val recyclerView = view.findViewById<RecyclerView>(R.id.search_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isNotEmpty()) {
                    searchItems = DatabaseHelper(requireContext()).searchData("'%$newText%'")
                }
                return false
            }

        })

        val adapter = Adapter(searchItems)
        recyclerView.adapter = adapter

//        fun SearchView.getQueryTextChangeStateFlow(): StateFlow<String> {
//            val query = MutableStateFlow("Query")
//            searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return true
//                }
//
//                override fun onQueryTextChange(newText: String): Boolean {
//                    query.value = newText
//                    return true
//                }
//            })
//            return query
//        }
//        // Fake network transaction
//        fun someDataFetch(query: String): Flow<String> {
//            return flow {
//                delay(1500)
//                emit(query)
//            }
//        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}