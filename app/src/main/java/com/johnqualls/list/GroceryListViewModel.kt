package com.johnqualls.list

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class GroceryListViewModel(private val groceryListDataSource: GroceryListDataSource) : ViewModel() {
    val items = MutableLiveData<GroceryListViewState>()

    init {
        items.value = GroceryListViewState(loading = View.VISIBLE)
        groceryListDataSource
            .retrieveItems()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                items.value = GroceryListViewState(retrievedItems = it, loading = View.INVISIBLE)
            }, {
                Timber.e(it)
            })
    }

//    fun checkItem(itemId: Int) {
//        val newItem = items.value?.find { it.id == itemId}?.copy(
//            checked = true
//        )
//
//    }
}
