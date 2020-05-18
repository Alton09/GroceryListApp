package com.johnqualls.udf

/**
 * This class allows for single event [view effects][BaseViewModel.viewEffects] for a LiveData
 * stream.
 */
class ViewEffect<T> internal constructor(private val content: T) {

    /** The emitted view effect, or null if it has already been retrieved **/
    val viewEffect: T? get() = getContentIfNotHandled()
    private var hasBeenHandled = false

    private fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) { null
        } else {
            hasBeenHandled = true
            content
        }
}