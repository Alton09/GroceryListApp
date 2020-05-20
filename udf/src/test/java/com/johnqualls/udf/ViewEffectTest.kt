package com.johnqualls.udf

import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class ViewEffectTest {

    @Test
    fun `viewEffect should return the content on first time access`() {
        val content = Content()
        val viewEffect = ViewEffect(content)

        assertThat(viewEffect.viewEffect, equalTo(content))
    }

    @Test
    fun `viewEffect should return null if called twice`() {
        val content = Content()
        val viewEffect = ViewEffect(content)

        viewEffect.viewEffect

        assertThat(viewEffect.viewEffect, `is`(nullValue()))
    }

    class Content

}