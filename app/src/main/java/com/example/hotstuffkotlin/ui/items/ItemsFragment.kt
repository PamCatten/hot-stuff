package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
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
    private var binding: FragmentItemsBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentItemsBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_items, container, false)
//        getData(view)
//        val items = ArrayList<Item>()
        val items = DatabaseHelper(requireContext()).getItems()
        val recyclerItems = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        recyclerItems.layoutManager = LinearLayoutManager(context)
//        for (i in 1..5) {
//            items.add(
//                Item (
//                    itemId = i, buildingId = i, name = """65" 4K Smart TV, V-Series UHD LED #$i""",
//                    quantity = 1, value = 999.99, category = """Electronics""",
//                    room = """Living Room""", make ="""Roku""", imagePath ="""examplePath""",
//                    description ="""It is a long established fact that a reader will be distracted by the readable content of a page when looking at it's layout."""
//                )
//            )
//        }

        val adapter = Adapter(items)
        recyclerItems.adapter = adapter

        val bundle = Bundle()
        adapter.setOnItemClickListener(object : Adapter.onItemClickListener {
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

    // TODO: Deprecation warning, fix when time
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar_main, menu)
    }

    private fun getData(view : View) {}

//    override fun onResume() {
//        super.onResume()
//        val item = bundle.getInt("delete")
//        if (item != -1) items.remove(items[bundle.getInt("position")])
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}