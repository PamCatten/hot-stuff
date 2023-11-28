package com.example.hotstuffkotlin.ui.items

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemsBinding
import com.example.hotstuffkotlin.models.Item
import com.example.hotstuffkotlin.ui.ItemDetailActivity
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.appbar_menu, menu)
    }

    private fun getData(view : View) {
        val recyclerItems = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
//        val data = ArrayList<ItemCardViewModel>()
        val items = ArrayList<Item>()
        recyclerItems.layoutManager = LinearLayoutManager(context)
//        for (i in 1..20) {
//            data.add(
//                ItemCardViewModel(
//                "Roku 55' 4K Smart TV",
//                "Electronics",
//                "Living Room",
//                "$i items")
//            )
//        }
        for (i in 1..20) {
            items.add(
                Item(
                1,
                1,
                """ROKU 65 Inch 4K Smart TV, V-Series UHD LED HDR Television #$i""",
                1,
                """Electronics""",
                """Living Room""",
                """Roku""",
                999.99,
                """examplePath""",
                """It is a long established fact that a reader will be distracted by the readable content of a page when looking at it's layout.""")
            )
        }
//        val adapter = Adapter(data)
        val adapter = Adapter(items)
        recyclerItems.adapter = adapter

        adapter.setOnItemClickListener(object : Adapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, ItemDetailActivity::class.java)
//                intent.putExtra("name", data[position].textName)
//                intent.putExtra("category", data[position].textCategory)
//                intent.putExtra("room", data[position].textRoom)
//                intent.putExtra("quantity", data[position].textQuantity)

//                intent.putExtra("item", items[position] as Parcelable)
                intent.putExtra("name", items[position].name)
                intent.putExtra("category", items[position].category)
                intent.putExtra("room", items[position].room)
                intent.putExtra("make", items[position].make)
                intent.putExtra("value", items[position].value)
                intent.putExtra("description", items[position].description)
                intent.putExtra("image", items[position].imagePath)
                val quantityNumeral = items[position].quantity
                val quantityString = if (quantityNumeral > 1) "$quantityNumeral items" else "$quantityNumeral item"
                intent.putExtra("quantity", quantityString)
                startActivity(intent)

                //Toast.makeText(activity, "You clicked on item $position", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}