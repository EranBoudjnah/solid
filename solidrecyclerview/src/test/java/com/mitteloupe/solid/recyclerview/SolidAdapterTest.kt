package com.mitteloupe.solid.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.recyclerview.data.TestData
import com.mitteloupe.solid.recyclerview.data.TestViewHolder
import com.nhaarman.mockitokotlin2.UseConstructor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

private const val ITEM_TYPE_DEFAULT = 0

@RunWith(MockitoJUnitRunner::class)
class SolidAdapterTest {
    private lateinit var cut: SolidAdapter<TestViewHolder, TestData>

    @Mock
    lateinit var viewProvider: ViewProvider

    @Mock
    lateinit var viewBinder: ViewBinder<TestViewHolder, TestData>

    @Mock
    lateinit var itemsSynchronizer: ItemsSynchronizer<TestViewHolder, TestData>

    @Mock
    lateinit var itemsSynchronizerProvider: (RecyclerView.Adapter<TestViewHolder>) -> ItemsSynchronizer<TestViewHolder, TestData>

    @Mock
    lateinit var positionToType: (ItemsSynchronizer<TestViewHolder, TestData>, Int) -> Int

    @Before
    fun setUp() {
        cut = mock(
            defaultAnswer = Mockito.CALLS_REAL_METHODS,
            useConstructor = UseConstructor.withArguments(
                viewProvider,
                viewBinder,
                itemsSynchronizerProvider,
                positionToType
            )
        )

        doReturn(itemsSynchronizer).whenever(itemsSynchronizerProvider)(cut)
    }

    @Test
    fun `Given list of items when setItems then calls itemsSynchronizer#setItems`() {
        // Given
        val items = listOf<TestData>()

        // When
        cut.setItems(items)

        // Then
        verify(itemsSynchronizer).setItems(items)
    }

    @Test
    fun `Given list of items when onBindViewHolder then binds expected data`() {
        // Given
        val item = mock<TestData>()
        val viewHolder = mock<TestViewHolder>()
        val position = 1
        doReturn(item).whenever(itemsSynchronizer).getItemAt(position)

        // When
        cut.onBindViewHolder(viewHolder, position)

        // Then
        verify(viewBinder).bindView(viewHolder, item)
    }

    @Test
    fun `Given list of items and payloads when onBindViewHolder then binds expected data`() {
        // Given
        val item = mock<TestData>()
        val viewHolder = mock<TestViewHolder>()
        val position = 1
        val payload = mock<Any>()
        val payloads = listOf(payload)
        val expectedPayloads = setOf(payload)
        doReturn(item).whenever(itemsSynchronizer).getItemAt(position)

        // When
        cut.onBindViewHolder(viewHolder, position, payloads)

        // Then
        verify(viewBinder).bindView(viewHolder, item, expectedPayloads)
    }

    @Test
    fun `Given items when setItems(vararg) then calls itemsSynchronizer#setItems`() {
        // Given
        val item1 = mock<TestData>()
        val item2 = mock<TestData>()

        // When
        cut.setItems(item1, item2)

        // Then
        verify(itemsSynchronizer).setItems(listOf(item1, item2))
    }

    @Test
    fun `Given item when insertItem then calls itemsSynchronizer#insertItem`() {
        // Given
        val item = mock<TestData>()
        val position = 0

        // When
        cut.insertItem(position, item)

        // Then
        verify(itemsSynchronizer).insertItem(position, item)
    }

    @Test
    fun `Given items when insertItems then calls itemsSynchronizer#insertItems`() {
        // Given
        val items = listOf<TestData>()
        val positionStart = 0

        // When
        cut.insertItems(positionStart, items)

        // Then
        verify(itemsSynchronizer).insertItems(positionStart, items)
    }

    @Test
    fun `Given item when changeItem then calls itemsSynchronizer#changeItem`() {
        // Given
        val item = mock<TestData>()
        val position = 0

        // When
        cut.changeItem(position, item)

        // Then
        verify(itemsSynchronizer).changeItem(position, item)
    }

    @Test
    fun `Given item and payload when changeItem then calls itemsSynchronizer#changeItem with payload`() {
        // Given
        val item = mock<TestData>()
        val position = 0
        val payload = mock<Any>()

        // When
        cut.changeItem(position, item, payload)

        // Then
        verify(itemsSynchronizer).changeItem(position, item, payload)
    }

    @Test
    fun `Given item swhen changeItems then calls itemsSynchronizer#changeItems`() {
        // Given
        val items = listOf<TestData>()
        val position = 0

        // When
        cut.changeItems(position, items)

        // Then
        verify(itemsSynchronizer).changeItems(position, items)
    }

    @Test
    fun `Given items and payload when changeItems then calls itemsSynchronizer#changeItems with payload`() {
        // Given
        val items = listOf<TestData>()
        val position = 0
        val payload = mock<Any>()

        // When
        cut.changeItems(position, items, payload)

        // Then
        verify(itemsSynchronizer).changeItems(position, items, payload)
    }

    @Test
    fun `Given from and to positions when moveItem then calls itemsSynchronizer#moveItem`() {
        // Given
        val fromPosition = 0
        val toPosition = 1

        // When
        cut.moveItem(fromPosition, toPosition)

        // Then
        verify(itemsSynchronizer).moveItem(fromPosition, toPosition)
    }

    @Test
    fun `Given position when removeItem then calls itemsSynchronizer#removeItem`() {
        // Given
        val position = 0

        // When
        cut.removeItem(position)

        // Then
        verify(itemsSynchronizer).removeItem(position)
    }

    @Test
    fun `Given from and to positions when removeItems then calls itemsSynchronizer#removeItems`() {
        // Given
        val fromPosition = 0
        val toPosition = 1

        // When
        cut.removeItems(fromPosition, toPosition)

        // Then
        verify(itemsSynchronizer).removeItems(fromPosition, toPosition)
    }

    @Test
    fun `Given parent ViewGroup and view type when onCreateViewHolder then returns expected ViewHolder`() {
        // Given
        val parent = mock<ViewGroup>()
        val viewType = 123
        val view = mock<View>()
        given { viewProvider.getView(parent, viewType) }.willReturn(view)
        val expected = mock<TestViewHolder>()
        given { cut.createViewHolder(view) }.willReturn(expected)

        // When
        val actualValue = cut.onCreateViewHolder(parent, viewType)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given set items when getItemCount then returns expected count`() {
        // Given
        val expected = 4
        given { itemsSynchronizer.itemsSize }.willReturn(expected)

        // When
        val actualValue = cut.itemCount

        // Then
        assertEquals(actualValue, expected)
    }

    @Test
    fun `Given no items set when getItemCount then returns count of 0`() {
        // Given
        val expected = 0

        // When
        val actualValue = cut.itemCount

        // Then
        assertEquals(actualValue, expected)
    }

    @Test
    fun `When getItemViewType then returns expected type`() {
        // Given
        val position = 4
        val expected = 3
        doReturn(expected).whenever(positionToType)(itemsSynchronizer, position)

        // When
        val actualValue = cut.getItemViewType(position)

        // Then
        assertEquals(actualValue, expected)
    }

    @Test
    fun `Given default itemsSynchronization when itemCount then returns 0`() {
        // Given
        val cutWithDefaultItemsSynchronization = mock<SolidAdapter<TestViewHolder, TestData>>(
            defaultAnswer = Mockito.CALLS_REAL_METHODS,
            useConstructor = UseConstructor.withArguments(
                viewProvider,
                viewBinder,
                positionToType
            )
        )
        val expected = 0

        // When
        val actualValue = cutWithDefaultItemsSynchronization.itemCount

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given no optional arguments when itemCount then returns 0`() {
        // Given
        val cutWithNoOptionals = mock<SolidAdapter<TestViewHolder, TestData>>(
            defaultAnswer = Mockito.CALLS_REAL_METHODS,
            useConstructor = UseConstructor.withArguments(
                viewProvider,
                viewBinder
            )
        )
        val expected = 0

        // When
        val actualValue = cutWithNoOptionals.itemCount

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given default positionToType when getItemViewType then returns default type`() {
        // Given
        val cutWithDefaultPositionToType = mock<SolidAdapter<TestViewHolder, TestData>>(
            defaultAnswer = Mockito.CALLS_REAL_METHODS,
            useConstructor = UseConstructor.withArguments(
                viewProvider,
                viewBinder,
                itemsSynchronizerProvider
            )
        )
        doReturn(itemsSynchronizer).whenever(itemsSynchronizerProvider)(cutWithDefaultPositionToType)

        val position = 4
        val expected = ITEM_TYPE_DEFAULT

        // When
        val actualValue = cutWithDefaultPositionToType.getItemViewType(position)

        // Then
        assertEquals(expected, actualValue)
    }

}
