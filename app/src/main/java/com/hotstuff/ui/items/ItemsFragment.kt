package com.hotstuff.ui.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hotstuff.R
import com.hotstuff.databinding.FragmentItemsBinding
import com.hotstuff.utils.Adapter
import com.hotstuff.utils.DatabaseHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ItemsFragment : Fragment() {
    private var _binding: FragmentItemsBinding? = null
    private val binding get() = _binding!!
    private var loading = false
    private var query: String? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentItemsBinding.inflate(inflater, container, false)
        val view = binding.root
        val items = DatabaseHelper(requireContext()).getDataRange()
        val recyclerItems = view.findViewById<RecyclerView>(R.id.items_recycler_view)
        recyclerItems.layoutManager = LinearLayoutManager(context)
        recyclerItems.itemAnimator = null

        val adapter = Adapter(items)
        recyclerItems.adapter = adapter

        val emptyContainer = view.findViewById<ConstraintLayout>(R.id.items_empty)
        if (items.size == 0) emptyContainer.visibility = View.VISIBLE
        else emptyContainer.visibility = View.GONE
        
        recyclerItems.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerItems.layoutManager as LinearLayoutManager
                if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItems = layoutManager.findFirstVisibleItemPosition()
                    val totalItemCount = layoutManager.itemCount

                    if (!loading && visibleItemCount + pastVisibleItems >= totalItemCount && query == null) {
                        loading = true
                        val moreItems = DatabaseHelper(requireContext()).getDataRange(totalItemCount)
                        for (i in moreItems) items.add(i)
                        adapter.notifyItemRangeInserted(totalItemCount, layoutManager.itemCount)
                        loading = false
                    }
                    else if (!loading && visibleItemCount + pastVisibleItems >= totalItemCount && query != null) {
                        loading = true
                        val moreItems = DatabaseHelper(requireContext()).getDataRange(totalItemCount, true, query)
                        for (i in moreItems) items.add(i)
                        adapter.notifyItemRangeInserted(totalItemCount, layoutManager.itemCount)
                        loading = false
                    }
                }
            }
        })

        val bundle = Bundle()
        adapter.setOnItemClickListener(object: Adapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                bundle.putInt("id", items[position].id)
                bundle.putInt("buildingId", items[position].buildingId)
                bundle.putInt("quantity", items[position].quantity)
                bundle.putString("name", items[position].name)
                bundle.putString("category", items[position].category)
                bundle.putString("room", items[position].room)
                bundle.putString("make", items[position].make)
                bundle.putString("description", items[position].description)
                bundle.putString("image", items[position].imageUri)
                bundle.putDouble("value", items[position].value ?: 0.00)
                bundle.putInt("position", position)
                bundle.putInt("delete", -1)

                childFragmentManager.beginTransaction().addToBackStack(null).commit()
                findNavController().navigate(R.id.action_items_to_item_detail, bundle)
            }
        })

        requireActivity().addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                if (!menu.hasVisibleItems()) menuInflater.inflate(R.menu.menu_toolbar_main, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.toolbar_main_search -> {
                        val searchView = menuItem.actionView as SearchView
                        // TODO: Find a way to remove auto-generated Search Icon in the query
                        searchView.isIconified = false
                        searchView.queryHint = getText(R.string.label_search_prompt)
                        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                searchView.clearFocus()
                                return false
                            }
                            override fun onQueryTextChange(newText: String): Boolean {
                                if (newText.isEmpty()) {
                                    items.clear()
                                    adapter.searchClear()
                                    query = null
                                    for (i in DatabaseHelper(requireContext())
                                        .getDataRange()) items.add(i)
                                } else {
                                    items.clear()
                                    query = "'%$newText%'"
                                    val retrievedItems = DatabaseHelper(requireContext())
                                        .getDataRange(items.size, true, query)
                                    for (i in retrievedItems) {
                                        items.add(i)
                                        adapter.searchInsert(retrievedItems.indexOf(i), items)
                                    }
                                }
                                return false
                            }
                        })
                        return true
                    }
                    R.id.toolbar_main_download -> {
                        val alertDialogBuilder = MaterialAlertDialogBuilder(requireContext(),
                            R.style.dialog_alert)
                        alertDialogBuilder.setTitle(getText(R.string.dialog_export_title))
                        alertDialogBuilder.setMessage(getString(R.string.dialog_export_message))
                        alertDialogBuilder.setPositiveButton(getText(R.string.dialog_positive)) {
                            dialog, _ ->
                            DatabaseHelper(requireContext()).exportCSV()
                            dialog.dismiss()
                        }
                        alertDialogBuilder.setNegativeButton(getText(R.string.dialog_negative)) {
                            dialog, _ ->
                            dialog.dismiss()
                        }
                        val alertDialog = alertDialogBuilder.create()
                        alertDialog.show()
                        return true
                    }
                    else -> return true
                }
            }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(true)
                menu.findItem(R.id.toolbar_main_download).setVisible(true)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}