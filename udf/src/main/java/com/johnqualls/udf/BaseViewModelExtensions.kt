package com.johnqualls.udf

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

/**
 * Convenience function for observing [view state][BaseViewModel.viewState] changes.
 *
 * See [BaseViewModel] for generic type meaning.
 */
fun <T, U : Any, V> Fragment.observeViewState(
    viewModel: BaseViewModel<T, U, V>,
    action: (viewState: U) -> Unit
) = viewModel.viewState.observe(viewLifecycleOwner, Observer { action(it) })

/**
 * Convenience function for observing single [view effect][BaseViewModel.viewEffects] changes.
 *
 * See [BaseViewModel] for generic type meaning.
 */
fun <T, U : Any, V> Fragment.observeViewEffects(
    viewModel: BaseViewModel<T, U, V>,
    action: (viewEffect: V) -> Unit
) =
    viewModel.viewEffects.observe(viewLifecycleOwner, Observer {
        it.viewEffect?.let { viewEffect -> action(viewEffect) }
    })
