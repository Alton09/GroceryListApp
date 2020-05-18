package com.johnqualls.udf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * This class provides the boilerplate code needed for a UDF implementation in the ViewModel layer.
 *
 * **Note:** This class must only be accessed from the main UI thread.
 *
 * @param T The type used for incoming view events in [processInput]
 * @param U The type used for the view state to later be rendered by the UI
 * @param V The type used to represent view effects, which are one time view events that are not
 * persisted
 * @param initialViewState The initial [view state][U]
 */
abstract class BaseViewModel<T, U : Any, V>(initialViewState: U) : ViewModel() {
    private val mutableViewState = MutableLiveData(initialViewState)
    private val mutableViewEffects = MutableLiveData<ViewEffect<V>>()

    /** The LiveData stream that emits each [view state][U]. **/
    val viewState: LiveData<U> = mutableViewState

    /** The LiveData stream that emits each [view effect][V]. **/
    val viewEffects: LiveData<ViewEffect<V>> = mutableViewEffects

    /**
     * The single entry point to process all incoming [view events][T] coming from the UI layer
     * (i.e. Activity, Fragment).
     **/
    abstract fun processInput(viewEvent: T)

    /**
     * Notifies the UI [view state][U] observers that a new [view state][U] is available.
     *
     * @param action the action that returns the new [view state][U]
     **/
    protected fun updateState(action: (oldState: U) -> U) {
        withState { currentState ->
            mutableViewState.value = action(currentState)
        }
    }


    /**
     * Notifies the UI [view effect][V] observers that a new [view effect][V] is available.
     *
     * @param action the action that returns the new [view effect][V]
     **/
    protected fun viewEffect(action: () -> V) {
        mutableViewEffects.value = ViewEffect(action())
    }


    /**
     * Convenience function to execute an action with the current [view state][U].
     *
     * @param R the return type of the [action]
     * @param action the action to execute with the current [view state][U] passed in as a parameter
     *
     **/
    protected fun <R> withState(action: (currentState: U) -> R): R =
        // Force unwrap used since an initial view state is always set
        action(mutableViewState.value!!)
}
