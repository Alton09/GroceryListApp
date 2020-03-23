package com.johnqualls.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.johnqualls.BaseViewModel
import com.johnqualls.item.GroceryItem
import com.johnqualls.list.GroceryListViewEvent.ItemCheck
import com.johnqualls.list.GroceryListViewEvent.SwipeRefresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class GroceryListViewModel(
    private val groceryListDataSource: GroceryListDataSource,
    private val initialState: GroceryListViewState = GroceryListViewState(),
    private val ioCoroutineContext: CoroutineContext = Dispatchers.IO
) : BaseViewModel() {

    val viewState = MutableLiveData<GroceryListViewState>().apply { value = initialState }

    init {
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
        updateState { oldState ->
            var newState = oldState
            oldState.retrievedItems.find { it.id == itemId }?.let { matchedItem ->
                val newItem = matchedItem.copy(checked = !matchedItem.checked)
                val newItems =
                    oldState.retrievedItems.filter { it.id != matchedItem.id }.toMutableList()
                newItems.add(newItem)
                newState = oldState.copy(retrievedItems = newItems.toList().sortedBy { it.name })
            }
            newState
        }
    }

    private fun retrieveItems() {
        updateState { it.copy(loading = true) }
        viewModelScope.launch {
            var items = emptyList<GroceryItem>()
            withContext(ioCoroutineContext) {
                items = groceryListDataSource.retrieveItems()
            }
            updateState { it.copy(retrievedItems = items, loading = false) }
        }
    }

    private fun updateState(action: (oldState: GroceryListViewState) -> GroceryListViewState) {
        val oldState = viewState.value ?: GroceryListViewState()
        val newState = action(oldState)
        viewState.value = newState
    }

    private fun currentState(action: (currentState: GroceryListViewState) -> Unit) {
        action(viewState.value ?: GroceryListViewState())
    }
}
