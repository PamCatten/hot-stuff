package com.example.hotstuffkotlin.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemDetailBinding
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.example.hotstuffkotlin.utils.SharedPreferenceHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.imageview.ShapeableImageView
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
        val image = view.findViewById<ShapeableImageView>(R.id.item_detail_image)
        val bundle = this.requireArguments()

        fun formatValue(number: Double): String? {
            val df = DecimalFormat("#,###,###.##")
            return df.format(number)
        }

        val valueNumeral = formatValue(bundle.getDouble("value")).toString()
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

        val imageURI = bundle.getString("image")
        if (imageURI != null) image.setImageURI(imageURI.toUri())

        val editButton = view.findViewById<MaterialButton>(R.id.item_detail_edit_button)
        editButton.setOnClickListener{
            childFragmentManager.beginTransaction().addToBackStack(null).commit()
            findNavController().navigate(R.id.action_item_detail_to_edit_item, bundle)
        }

        val deleteButton = view.findViewById<MaterialButton>(R.id.item_detail_delete_button)
        deleteButton.setOnClickListener{
            val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(), R.style.dialog_alert)
            alertDialogBuilder.setTitle("Delete item?")
            alertDialogBuilder.setMessage("${name.text} will be permanently removed from your device")
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

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean { return true }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(false)
                menu.findItem(R.id.toolbar_main_download).setVisible(false)
                menu.findItem(R.id.toolbar_main_report).setVisible(false)
                menu.findItem(R.id.toolbar_main_rate).setVisible(false)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(false)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

}