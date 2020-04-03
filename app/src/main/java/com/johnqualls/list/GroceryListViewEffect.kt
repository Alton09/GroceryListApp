package com.johnqualls.list

sealed class GroceryListViewEffect {
    object SyncComplete: GroceryListViewEffect()
}