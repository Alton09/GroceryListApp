package com.johnqualls.list

sealed class GroceryListViewEvent {
    object ItemCheck: GroceryListViewEvent()
    object SwipeRefresh: GroceryListViewEvent()
}