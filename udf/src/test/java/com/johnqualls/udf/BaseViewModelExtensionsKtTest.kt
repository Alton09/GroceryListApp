package com.johnqualls.udf

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.testing.launchFragmentInContainer
import com.johnqualls.udf.BaseViewModelExtensionsKtTest.TestFragment.TestFragmentFactory
import com.johnqualls.udf.util.TestViewEffect
import com.johnqualls.udf.util.TestViewEffect.SomeViewEffect
import com.johnqualls.udf.util.TestViewModel
import com.johnqualls.udf.util.TestViewState
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class BaseViewModelExtensionsKtTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val viewModel = TestViewModel(TestViewState())
    private val scenario = launchFragmentInContainer<TestFragment>(
        factory = TestFragmentFactory(viewModel)
    )

    @Test
    fun `observeViewState should invoke the action when a new view state is emitted`() {
        viewModel.setSomeBoolToFalse()

        scenario.onFragment {
            assertThat(it.currentViewState, equalTo(TestViewState(someBool = false)))
        }
    }

    @Test
    fun `observeViewEffects should invoke the action when a new view effect is emitted`() {
        viewModel.sendTestViewEffect()

        scenario.onFragment {
            assertThat(it.currentViewEffect is SomeViewEffect, equalTo(true))
        }
    }

    @Test
    fun `observeViewEffects should not invoke the action when an existing view effect is emitted`() {
        viewModel.sendTestViewEffect()
        scenario.recreate()

        scenario.onFragment {
            assertThat(it.currentViewEffect, `is`(nullValue()))
        }
    }

    class TestFragment(private val viewModel: TestViewModel): Fragment() {

        var currentViewState: TestViewState? = null
        var currentViewEffect: TestViewEffect? = null

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            observeViewState(viewModel) { testViewStateAction(it) }
            observeViewEffects(viewModel) { testViewEffectAction(it) }
        }

        override fun onStart() {
            super.onStart()
            currentViewEffect = null
        }

        private fun testViewStateAction(testViewState: TestViewState) {
            currentViewState = testViewState
        }

        private fun testViewEffectAction(testViewEffect: TestViewEffect) {
            currentViewEffect = testViewEffect
        }

        class TestFragmentFactory(private val viewModel: TestViewModel): FragmentFactory() {

            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return TestFragment(viewModel)
            }
        }
    }
}
