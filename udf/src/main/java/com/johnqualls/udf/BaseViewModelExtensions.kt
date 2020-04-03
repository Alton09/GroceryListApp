package com.johnqualls.udf

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

fun <VIEW_EVENT, VIEW_STATE : Any, VIEW_EFFECT> Fragment.observeViewState(
    viewModel: BaseViewModel<VIEW_EVENT, VIEW_STATE, VIEW_EFFECT>,
    action: (viewState: VIEW_STATE) -> Unit
) {

    viewModel.viewState.observe(this, Observer { action(it) })
}
