package com.johnqualls.list

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.johnqualls.BaseViewModel
import com.johnqualls.item.GroceryItem
import com.johnqualls.list.GroceryListViewEvent.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import timber.log.Timber

class GroceryListViewModel(
    private val groceryListDataSource: GroceryListDataSource,
    private val initialState: GroceryListViewState = GroceryListViewState()
) : BaseViewModel() {

    val viewState = MutableLiveData<GroceryListViewState>().apply { value = initialState }

    init {
        disposables.add(
            retrieveItems()
        )

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
                val newItems = oldState.retrievedItems.filter { it.id != matchedItem.id }.toMutableList()
                newItems.add(newItem)
                newState = oldState.copy(retrievedItems = newItems.toList().sortedBy { it.name })
            }
            newState
        }
    }

    private fun retrieveItems(): Disposable {
        updateState { it.copy(loading = true) }
        return groceryListDataSource
            .retrieveItems()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items ->
                updateState { oldState ->
                    oldState.copy(
                        retrievedItems = items.sortedBy { it.name },
                        loading = false
                    )
                }
            }, {
                Timber.e(it)
            })
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
