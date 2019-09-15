package com.johnqualls.list

sealed class GroceryListViewEvent {
    data class ItemCheck(val groceryItemId: Int) : GroceryListViewEvent()
    object SwipeRefresh : GroceryListViewEvent()
}