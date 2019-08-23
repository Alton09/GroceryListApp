package com.johnqualls.list

import com.johnqualls.list.GroceryListViewEvent.*

object GrocerListIntentFactory {

    fun createIntent(event: GroceryListViewEvent) {
        when(event) {
            ItemCheck -> {

            }
            SwipeRefresh -> {

            }
        }
    }
}