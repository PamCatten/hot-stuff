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
import com.example.hotstuffkotlin.models.Item
import com.example.hotstuffkotlin.utils.Adapter

class ItemsFragment : Fragment() {
    private var binding: FragmentItemsBinding? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
//        binding = FragmentItemsBinding.inflate(layoutInflater)
        val view = inflater.inflate(R.layout.fragment_items, container, false)
//        getData(view)

        val recyclerItems = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        recyclerItems.layoutManager = LinearLayoutManager(context)

        val items = ArrayList<Item>()
        for (i in 1..20) {
            items.add(
                Item (
                    itemId = 1, buildingId = 1, name = """65" 4K Smart TV, V-Series UHD LED #$i""",
                    quantity = 1, value = 999.99, category = """Electronics""",
                    room = """Living Room""", make ="""Roku""", imagePath ="""examplePath""",
                    description ="""It is a long established fact that a reader will be distracted by the readable content of a page when looking at it's layout."""
                )
            )
        }
        val adapter = Adapter(items)
        recyclerItems.adapter = adapter

        adapter.setOnItemClickListener(object : Adapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()

                bundle.putString("name", items[position].name)
                bundle.putString("category", items[position].category)
                bundle.putString("room", items[position].room)
                bundle.putString("make", items[position].make)
                bundle.putString("description", items[position].description)
                bundle.putString("image", items[position].imagePath)

                // TODO: I think this can be improved, fix when time
                val valueNumeral = if (items[position].value != null) items[position].value else 0.00
                if (valueNumeral != null) bundle.putDouble("value", valueNumeral)

                val quantityNumeral = items[position].quantity
                if (quantityNumeral > 1) bundle.putString("quantity","$quantityNumeral items")
                else bundle.putString("quantity", "$quantityNumeral item")

                val navController = findNavController()
                childFragmentManager.beginTransaction().addToBackStack(null).commit()
                navController.navigate(R.id.action_items_to_item_detail, bundle)
            }
        })

        return view
    }

    // TODO: Deprecation warning, fix when time
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    private fun getData(view : View) {}

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}