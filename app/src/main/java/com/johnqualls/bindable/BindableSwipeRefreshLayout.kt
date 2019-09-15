package com.johnqualls.bindable

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

interface BindableSwipeRefreshLayout {
    fun isRefreshing(isRefreshing: Boolean)
}

@BindingAdapter("isRefreshing")
fun bindIsRefreshing(swipeRefreshLayout: SwipeRefreshLayout, isRefreshing: Boolean) {
    swipeRefreshLayout.isRefreshing = isRefreshing
}