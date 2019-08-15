package com.johnqualls.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johnqualls.BindableAdapter
import com.johnqualls.R
import com.johnqualls.item.GroceryItem

class GrocerListAdapter : RecyclerView.Adapter<GroceryItemViewHolder>(), BindableAdapter<GroceryListViewState> {
    private val items = mutableListOf<GroceryItem>()

    override fun swap(items: GroceryListViewState) {
        this.items.clear()
        this.items.addAll(items.retrievedItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.grocery_item, parent, false)
        return GroceryItemViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GroceryItemViewHolder, position: Int) {
        holder.bind(items[position])
    }
}