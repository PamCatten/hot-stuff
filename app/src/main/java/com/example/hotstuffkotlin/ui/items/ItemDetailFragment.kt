package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)

        val name = view.findViewById<TextView>(R.id.item_detail_name_text)
        val category = view.findViewById<TextView>(R.id.item_detail_category_text)
        val room = view.findViewById<TextView>(R.id.item_detail_room_text)
        val description = view.findViewById<TextView>(R.id.item_detail_description_text)
        val make = view.findViewById<TextView>(R.id.item_detail_make_text)
        val value = view.findViewById<TextView>(R.id.item_detail_value_text)
        val quantity = view.findViewById<TextView>(R.id.item_detail_quantity_text)

        val bundle = this.arguments
        val valueNumeral = bundle?.getDouble("value", 0.00).toString()
        val valueCurrency = "$" // get from stored preference value

        name.text = bundle?.getString("name")
        category.text = bundle?.getString("category")
        room.text = bundle?.getString("room")
        quantity.text = bundle?.getString("quantity")

        if (bundle?.getDouble("value") != null) value.text = "$valueCurrency $valueNumeral"
        else value.text = R.string.unspecified_value_filler.toString()

        if (bundle?.getString("make") != null) make.text = bundle.getString("make")
        else make.text = R.string.unspecified_value_filler.toString()

        if (bundle?.getString("description") != null) description.text = bundle.getString("description")
        else description.text = R.string.unspecified_value_filler.toString()

        // TODO: include image path

        val navIcon = view.findViewById<ImageButton>(R.id.item_detail_appbar_navIcon)
        navIcon.setOnClickListener{
            Log.e("MainActivity", "popping backstack")
            findNavController().navigate(R.id.navigation_items)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        _binding = null
    }
}