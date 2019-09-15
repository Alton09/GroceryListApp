package com.johnqualls.bindable

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

interface BindableAdapter<T> {
    fun swap(items: T)
}

@BindingAdapter("data")
fun <T> bindDataToRecyclerView(recyclerView: RecyclerView, data: T) {
    if (recyclerView.adapter is BindableAdapter<*>) {
        (recyclerView.adapter as BindableAdapter<T>).swap(data)
    }
}
