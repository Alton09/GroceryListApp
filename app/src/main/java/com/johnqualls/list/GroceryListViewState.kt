package com.johnqualls.list

import android.view.View
import com.johnqualls.item.GroceryItem

data class GroceryListViewState(
    val retrievedItems : List<GroceryItem> = emptyList(),
    val loading: Int = View.VISIBLE,
    val retrievalError: Boolean = false
)