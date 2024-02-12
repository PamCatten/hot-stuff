package com.example.hotstuffkotlin.ui.items

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.databinding.FragmentItemsBinding
import com.example.hotstuffkotlin.utils.Adapter
import com.example.hotstuffkotlin.utils.DatabaseHelper
import com.google.android.material.imageview.ShapeableImageView

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
        val emptyImage = view.findViewById<ShapeableImageView>(R.id.items_empty_image)
        val emptyText = view.findViewById<TextView>(R.id.items_empty_text)
        if (items.size == 0) {
            emptyContainer.visibility = View.VISIBLE
//            emptyImage.visibility =  View.VISIBLE
//            emptyText.visibility = View.VISIBLE
        } else {
            emptyContainer.visibility = View.GONE
//            emptyImage.visibility = View.GONE
//            emptyText.visibility = View.GONE
        }

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
                        val moreItems = DatabaseHelper(requireContext()).getDataRange(totalItemCount, "SEARCH", query)
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

        // TODO: Obliterates DRY, cannot customize menu items visibility w/ activity based menu providers, find another workaround
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
                        searchView.queryHint = "Search Hot Stuff"

                        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                searchView.clearFocus()
                                return false
                            }

                            override fun onQueryTextChange(newText: String): Boolean {
                                if (newText.isEmpty()) {
                                    items.clear()
                                    adapter.clearSearchResults()
                                    query = null
                                    for (i in DatabaseHelper(requireContext()).getDataRange()) items.add(i)
                                } else {
                                    items.clear()
                                    query = "'%$newText%'"
                                    val retrievedItems = DatabaseHelper(requireContext()).getDataRange(items.size, "SEARCH", query)
                                    for (i in retrievedItems) {
                                        items.add(i)
                                        adapter.searchInsert(retrievedItems.indexOf(i), retrievedItems)
                                    }
                                }
                                return false
                            }
                        })
                        return true
                    }
                    R.id.toolbar_main_download -> {
                        DatabaseHelper(requireContext()).exportCSV()
                        return true
                    }
                    R.id.toolbar_main_report -> {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.toolbar_issue_link))))
                        return true
                    }
                    R.id.toolbar_main_rate -> { return true }
                    R.id.toolbar_main_feedback -> {
                        val intent = Intent(Intent.ACTION_VIEW)
                        // TODO: Find an alternative way to extract these
                        intent.data = Uri.parse(
                            "mailto:campatten.dev@outlook.com" +
                                    "?subject=FEEDBACK: (Your Suggestion)" +
                                    "&body=Hey! Thanks for helping me improve Hot Stuff. Just a quick heads up, please make sure 'feedback' is somewhere in the subject of your suggestion so it ends up where I can see it! \n\n Much love, \nCam"
                        )
                        startActivity(intent)
                        return true
                    }
                    else -> return true
                }
            }
            override fun onPrepareMenu(menu: Menu) {
                menu.findItem(R.id.toolbar_main_search).setVisible(true)
                menu.findItem(R.id.toolbar_main_download).setVisible(true)
                menu.findItem(R.id.toolbar_main_report).setVisible(true)
                menu.findItem(R.id.toolbar_main_rate).setVisible(true)
                menu.findItem(R.id.toolbar_main_feedback).setVisible(true)
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}