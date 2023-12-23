package com.example.hotstuffkotlin.ui.items

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemDetailBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
import com.google.android.material.button.MaterialButton
import java.text.DecimalFormat

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        val name = view.findViewById<TextView>(R.id.item_detail_name_text)
        val category = view.findViewById<TextView>(R.id.item_detail_category_text)
        val room = view.findViewById<TextView>(R.id.item_detail_room_text)
        val description = view.findViewById<TextView>(R.id.item_detail_description_text)
        val make = view.findViewById<TextView>(R.id.item_detail_make_text)
        val value = view.findViewById<TextView>(R.id.item_detail_value_text)
        val quantity = view.findViewById<TextView>(R.id.item_detail_quantity_text)

        val bundle = this.requireArguments()

        fun formatValue(number: Double): String? {
            val df = DecimalFormat("#,###,###.##")
            return df.format(number)
        }

        val valueNumeral = formatValue(bundle.getDouble("value")).toString()
//        val valueCurrency = "$" // TODO: get from stored preference value
        val valueCurrency = SharedPreferenceHelper.getInstance(requireContext()).getCurrencyPref(requireContext())

        name.text = bundle.getString("name")
        category.text = bundle.getString("category")
        room.text = bundle.getString("room")

        val quantityNumeral = bundle.getInt("quantity")
        if (quantityNumeral == 1) quantity.text = "$quantityNumeral item"
        else quantity.text = "$quantityNumeral items"

        value.text = "$valueCurrency $valueNumeral"

        make.text = bundle.getString("make") ?: R.string.unspecified_value_filler.toString()
        description.text = bundle.getString("description") ?: R.string.unspecified_value_filler.toString()

        // TODO: include image path

        val editButton = view.findViewById<MaterialButton>(R.id.item_detail_edit_button)
        editButton.setOnClickListener{
            childFragmentManager.beginTransaction().addToBackStack(null).commit()
            findNavController().navigate(R.id.action_item_detail_to_edit_item, bundle)
        }

        val deleteButton = view.findViewById<MaterialButton>(R.id.item_detail_delete_button)
        deleteButton.setOnClickListener{

//            val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.dialog_alert)
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.setTitle("Delete item?")
            alertDialogBuilder.setMessage("${name.text} will be permanently removed from your device.")
            alertDialogBuilder.setPositiveButton("Delete") { dialog, _ ->
                DatabaseHelper(requireContext()).deleteItem(bundle.getInt("id"))
                bundle.putInt("delete", bundle.getInt("position"))
                findNavController().navigate(R.id.action_item_detail_to_items, bundle)
                dialog.dismiss()
            }
            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        return view
    }

}