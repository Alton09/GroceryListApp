package com.johnqualls.udf

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

fun <VIEW_EVENT, VIEW_STATE : Any> Fragment.observeViewState(
    viewModel: BaseViewModel<VIEW_EVENT, VIEW_STATE>,
    action: (viewState: VIEW_STATE) -> Unit
) {

    viewModel.viewState.observe(this, Observer { action(it) })
}
