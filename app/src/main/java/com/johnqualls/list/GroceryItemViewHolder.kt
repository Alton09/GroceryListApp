package com.johnqualls.list

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.johnqualls.item.GroceryItem
import kotlinx.android.synthetic.main.grocery_item.view.*

class GroceryItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(groceryItem: GroceryItem) {
        itemView.run {
            grocery_item_checkbox.isChecked = groceryItem.checked
            grocery_item_name.text = groceryItem.name
        }
    }
}