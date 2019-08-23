package com.johnqualls.list

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.johnqualls.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

class GroceryListViewModel(private val groceryListDataSource: GroceryListDataSource) : BaseViewModel() {
    val items = MutableLiveData<GroceryListViewState>()

    init {
        items.value = GroceryListViewState(loading = View.VISIBLE)
        disposables.add(
            groceryListDataSource
                .retrieveItems()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    items.value = GroceryListViewState(retrievedItems = it, loading = View.INVISIBLE)
                }, {
                    Timber.e(it)
                })
        )

    }

    fun checkItem(itemId: Int) {
        val newItem = items.value?.run {
            val matchedItem = retrievedItems.find { it.id == itemId }
            val newItem = matchedItem?.let { it.copy(checked = !it.checked) }
            listOf(retrievedItems.filter { it.id != matchedItem!!.id })
        }


    }
}
