package com.johnqualls.list

import com.johnqualls.item.GroceryItem
import io.uniflow.core.flow.UIState

data class GroceryListState(
    val retrievedItems: List<GroceryItem> = emptyList(),
    val loading: Boolean = false,
    val retrievalError: Boolean = false
) : UIState()