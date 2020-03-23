package com.johnqualls.list

import com.johnqualls.item.GroceryItem
import kotlinx.coroutines.delay

class GroceryListDataSource {

    val items = mutableListOf(
        GroceryItem(0, "Bread", true),
        GroceryItem(1, "Cherries", false),
        GroceryItem(2, "Milk", false),
        GroceryItem(3, "Tofu", false)
    )

    suspend fun retrieveItems(): List<GroceryItem> {
        delay(3000)
        return items
    }
}
