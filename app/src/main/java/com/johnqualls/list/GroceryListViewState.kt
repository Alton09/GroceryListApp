package com.johnqualls.list

import com.johnqualls.item.GroceryItem

data class GroceryListViewState(
    val retrievedItems : List<GroceryItem> = emptyList(),
    val loading: Boolean = true,
    val retrievalError: Boolean = false
)