package com.johnqualls.list

import com.johnqualls.item.GroceryItem
import com.johnqualls.list.GroceryListViewEvent.ItemCheck
import com.johnqualls.list.GroceryListViewEvent.SwipeRefresh
import io.uniflow.androidx.flow.AndroidDataFlow
import io.uniflow.core.flow.getStateAs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GroceryListViewModel(
    private val groceryListDataSource: GroceryListDataSource,
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) : AndroidDataFlow() {

    init {
        setState { GroceryListState() }
        retrieveItems()
    }

    fun processInputs(event: GroceryListViewEvent) {
        when (event) {
            is ItemCheck -> {
                checkItem(event.groceryItemId)
            }
            SwipeRefresh -> {
                retrieveItems()
            }
        }
    }

    fun checkItem(itemId: Int) {
//        updateState { oldState ->
//            var newState = oldState
//            oldState.retrievedItems.find { it.id == itemId }?.let { matchedItem ->
//                val newItem = matchedItem.copy(checked = !matchedItem.checked)
//                val newItems =
//                    oldState.retrievedItems.filter { it.id != matchedItem.id }.toMutableList()
//                newItems.add(newItem)
//                newState = oldState.copy(retrievedItems = newItems.toList().sortedBy { it.name })
//            }
//            newState
//        }
    }

    private fun retrieveItems() {
        setState {
            getStateAs<GroceryListState>().copy(loading = true)
        }
        setState {
            var items = emptyList<GroceryItem>()
            withContext(ioCoroutineContext) {
                items = groceryListDataSource.retrieveItems()
            }
            getStateAs<GroceryListState>().copy(retrievedItems = items, loading = false)
        }
    }
}