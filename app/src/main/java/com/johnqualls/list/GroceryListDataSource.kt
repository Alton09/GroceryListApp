package com.johnqualls.list

import com.johnqualls.item.GroceryItem
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class GroceryListDataSource {

    val items = mutableListOf(
        GroceryItem(0, "Bread", true),
        GroceryItem(1, "Cherries", false),
        GroceryItem(2, "Milk", false),
        GroceryItem(3, "Tofu", false)
    )

    fun retrieveItems() = Single.just(items).delay(3, TimeUnit.SECONDS)
}
