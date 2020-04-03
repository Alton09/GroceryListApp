package com.johnqualls.udf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val NULL_VIEW_STATE_ERROR = "ViewState can never be null"

abstract class BaseViewModel<VIEW_EVENT, VIEW_STATE : Any, VIEW_EFFECT>(initialViewState: VIEW_STATE) : ViewModel() {
    protected val mutableViewState = MutableLiveData<VIEW_STATE>(initialViewState)
    protected val mutableViewEffects = MutableLiveData<ViewEffect<VIEW_EFFECT>>()
    val viewState: LiveData<VIEW_STATE> = mutableViewState
    val viewEffects: LiveData<ViewEffect<VIEW_EFFECT>> = mutableViewEffects

    abstract fun processInput(viewEvent: VIEW_EVENT)

    protected fun updateState(action: (oldState: VIEW_STATE) -> VIEW_STATE) {
        withState { currentState ->
            mutableViewState.value = action(currentState)
        }
    }

    protected fun <R> withState(action: (currentState: VIEW_STATE) -> R): R {
        val oldState = mutableViewState.value
        oldState?.let {
            return action(oldState)
        } ?: throw IllegalStateException(NULL_VIEW_STATE_ERROR)
    }

    protected fun viewEffect(action: () -> VIEW_EFFECT) {
        mutableViewEffects.value = ViewEffect(action())
    }
}
