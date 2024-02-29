package com.hotstuff.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hotstuff.R
import com.hotstuff.models.Item

/**
 * A bridge class that takes retrieved item views from the database and makes them compatible with
 * the [RecyclerView.Adapter] interface.
 * @param items The list of items to be displayed in the RecyclerView.
 * @author Cam Patten
 */
class Adapter(private var items: MutableList<Item>): RecyclerView.Adapter<Adapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener
    interface OnItemClickListener { fun onItemClick(position : Int) }
    fun setOnItemClickListener(itemListener: OnItemClickListener){
        listener = itemListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item, parent, false)
        return ViewHolder(view, listener)
    }
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
     * Clear the stored items array in the [Adapter] and notify [ViewHolder].
     * @author Cam Patten
     */
    fun searchClear() {
        items.clear()
        notifyItemRangeRemoved(0, itemCount)
    }

    /**
     * Insert search result to the stored items array in the [Adapter] and notify [ViewHolder].
     * @param insertPosition the position of the item to be added
     * @param updatedItemsArray the updated item array
     * @author Cam Patten
     */
    fun searchInsert(insertPosition: Int, updatedItemsArray: ArrayList<Item>) {
        items = updatedItemsArray
        notifyItemInserted(insertPosition)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View, clickListener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName)
        val itemCategory: TextView = itemView.findViewById(R.id.itemCategory)
        val itemRoom: TextView = itemView.findViewById(R.id.itemRoom)
        val itemQuantity: TextView = itemView.findViewById(R.id.itemQuantity)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(bindingAdapterPosition)
            }
        }
    }
}