package com.mitteloupe.solid.recyclerview

import com.mitteloupe.solid.recyclerview.data.TestData
import com.mitteloupe.solid.recyclerview.data.TestViewHolder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SimpleViewBinderTest {
    private lateinit var cut: SimpleViewBinder<TestViewHolder, TestData>

    @Before
    fun setUp() {
        cut = mock(defaultAnswer = Mockito.CALLS_REAL_METHODS)
    }

    @Test
    fun `Given payloads when bindView then calls implementation without payloads`() {
        // Given
        val viewHolder = mock<TestViewHolder>()
        val data = mock<TestData>()
        val payloads = setOf<Any>()

        // When
        cut.bindView(viewHolder, data, payloads)

        // Then
        verify(cut).bindView(viewHolder, data)
    }
}
