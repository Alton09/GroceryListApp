package com.johnqualls.udf

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.johnqualls.udf.BaseViewModelTest.TestViewEffect.SomeViewEffect
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test

class BaseViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()
    private val viewModel = TestViewModel()

    @Test
    fun `constructor should set the initial view state`() {
        assertThat(viewModel.viewState.value, equalTo(TestViewState()))
    }

    @Test
    fun `updateState should update the old view state with a new view state`() {
        assertThat(viewModel.viewState.value, equalTo(TestViewState(someBool = true)))

        viewModel.setSomeBoolToFalse()

        assertThat(viewModel.viewState.value,
            equalTo(TestViewState("Test",false)))
    }

    @Test
    fun `viewEffect should emit a single event and not persist it`() {
        val viewModel = TestViewModel()

        viewModel.sendTestViewEffect()

        assertThat(viewModel.viewEffects.value!!.getContentIfNotHandled() is SomeViewEffect,
            equalTo(true))
        assertThat(viewModel.viewEffects.value!!.getContentIfNotHandled(), `is`(nullValue()))
    }

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

        fun updateStateTest() = updateState { it.copy() }

        fun updateViewEffectTest() = updateState { it.copy() }

        fun withStateTest(action: (currentState: TestViewState) -> Unit) = withState(action)

        fun sendTestViewEffect() {
            viewEffect { SomeViewEffect }
        }
    }
}
