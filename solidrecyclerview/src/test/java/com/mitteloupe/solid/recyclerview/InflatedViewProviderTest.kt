package com.mitteloupe.solid.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InflatedViewProviderTest {
    @Mock
    lateinit var layoutInflater: LayoutInflater

    private val layoutResourceId = 123

    @Test
    fun `Given single layout resource when getView then returns expected view`() {
        // Given
        val cut = InflatedViewProvider(layoutInflater, layoutResourceId)
        val parent = mock<ViewGroup>()
        val viewType = 2
        val expected = mock<View>()
        given { layoutInflater.inflate(layoutResourceId, parent, false) }
            .willReturn(expected)

        // When
        val actualValue = cut.getView(parent, viewType)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given multiple layout resources when getView then returns expected view`() {
        // Given
        val viewTypeToLayoutResourceId = mock<(Int) -> Int>()
        val cut = InflatedViewProvider(layoutInflater, viewTypeToLayoutResourceId)
        val parent = mock<ViewGroup>()
        val viewType = 3
        val expected = mock<View>()
        doReturn(layoutResourceId).whenever(viewTypeToLayoutResourceId)(viewType)
        given { layoutInflater.inflate(layoutResourceId, parent, false) }
            .willReturn(expected)

        // When
        val actualValue = cut.getView(parent, viewType)

        // Then
        assertEquals(expected, actualValue)
    }
}
