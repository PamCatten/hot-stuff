package com.example.hotstuffkotlin.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hotstuffkotlin.R
import com.example.hotstuffkotlin.models.Item

//class Adapter(private val mList: List<CardViewModel>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
//class Adapter(private val mList: List<ItemCardViewModel>) : RecyclerView.Adapter<Adapter.ViewHolder>() {
class Adapter(private var items : List<Item>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private lateinit var mListener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position : Int)
    }
    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview_item, parent, false)

        return ViewHolder(view, mListener)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

//        val itemCardViewModel = mList[position]
        val item = items[position]

//        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource(itemCardViewModel.image)
//            holder.itemName.text = itemCardViewModel.textName
//            holder.itemCategory.text = itemCardViewModel.textCategory
//            holder.itemRoom.text = itemCardViewModel.textRoom
//            holder.itemQuantity.text = itemCardViewModel.textQuantity

        val quantityNumeral = item.quantity
        val quantityString = if (quantityNumeral > 1) "$quantityNumeral items" else "$quantityNumeral item"

        holder.itemName.text = item.name
        holder.itemCategory.text = item.category
        holder.itemRoom.text = item.room
        holder.itemQuantity.text = quantityString
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
//        return mList.size
        return items.size
    }

    // Holds the views for adding it to image and text
//    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
//        val textView: TextView = itemView.findViewById(R.id.textView)
//
//        init {
//            itemView.setOnClickListener{
//                listener.onItemClick(bindingAdapterPosition)
//            }
//        }
//    }
    class ViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
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