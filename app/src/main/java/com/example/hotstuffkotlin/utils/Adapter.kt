package com.example.hotstuffkotlin.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.models.Item
class Adapter(private var items: MutableList<Item>): RecyclerView.Adapter<Adapter.ViewHolder>() {

    private lateinit var mListener: OnItemClickListener
    interface OnItemClickListener { fun onItemClick(position : Int) }
    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item, parent, false)
        return ViewHolder(view, mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val quantityNumeral = item.quantity
        val quantityString = if (quantityNumeral > 1) "$quantityNumeral items" else "$quantityNumeral item"
        holder.itemName.text = item.name
        holder.itemCategory.text = item.category
        holder.itemRoom.text = item.room
        holder.itemQuantity.text = quantityString
    }

    /**
     * Clears search results stored in the [Adapter] and notifies the RecyclerView ViewHolder of the
     * search results removal.
     */
    fun clearSearchResults() {
        items.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    fun searchInsert(insertPosition: Int, updatedItemsArray: ArrayList<Item>) {
        items = updatedItemsArray
        notifyItemInserted(insertPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val itemName : TextView = itemView.findViewById(R.id.itemName)
        val itemCategory : TextView = itemView.findViewById(R.id.itemCategory)
        val itemRoom : TextView = itemView.findViewById(R.id.itemRoom)
        val itemQuantity : TextView = itemView.findViewById(R.id.itemQuantity)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(bindingAdapterPosition)
            }
        }
    }
}