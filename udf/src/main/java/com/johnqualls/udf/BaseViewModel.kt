package com.johnqualls.udf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T, U : Any, V>(initialViewState: U) : ViewModel() {
    private val mutableViewState = MutableLiveData(initialViewState)
    private val mutableViewEffects = MutableLiveData<ViewEffect<V>>()
    val viewState: LiveData<U> = mutableViewState
    val viewEffects: LiveData<ViewEffect<V>> = mutableViewEffects

    abstract fun processInput(viewEvent: T)

    protected fun updateState(action: (oldState: U) -> U) {
        withState { currentState ->
            mutableViewState.value = action(currentState)
        }
    }

    protected fun <R> withState(action: (currentState: U) -> R): R =
        // Force unwrap used since an initial view state is always set
        action(mutableViewState.value!!)


    protected fun viewEffect(action: () -> V) {
        mutableViewEffects.value = ViewEffect(action())
    }
}
