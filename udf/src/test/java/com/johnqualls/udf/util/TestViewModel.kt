package com.johnqualls.udf.util

import com.johnqualls.udf.BaseViewModel
import com.johnqualls.udf.util.TestViewEffect.SomeViewEffect

class TestViewEvent
data class TestViewState(val someString: String = "Test", val someBool: Boolean = true)
sealed class TestViewEffect {
    object SomeViewEffect: TestViewEffect()
}

class TestViewModel(initialViewState: TestViewState = TestViewState()) : BaseViewModel<TestViewEvent, TestViewState, TestViewEffect>(initialViewState) {

    override fun processInput(viewEvent: TestViewEvent) {
    }

    fun setSomeBoolToFalse() {
        updateState { it.copy(someBool = false) }
    }

    fun sendTestViewEffect() {
        viewEffect { SomeViewEffect }
    }
}