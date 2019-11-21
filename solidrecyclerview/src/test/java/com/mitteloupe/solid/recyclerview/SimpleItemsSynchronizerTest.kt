package com.mitteloupe.solid.recyclerview

import androidx.recyclerview.widget.RecyclerView.Adapter
import com.mitteloupe.solid.recyclerview.data.TestData
import com.mitteloupe.solid.recyclerview.data.TestViewHolder
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SimpleItemsSynchronizerTest {
    private lateinit var cut: SimpleItemsSynchronizer<TestViewHolder, TestData>

    @Mock
    lateinit var adapter: Adapter<TestViewHolder>

    @Before
    fun setUp() {
        cut = SimpleItemsSynchronizer(adapter)
    }

    @Test
    fun `Given items and position when getItemAt then returns expected item`() {
        // Given
        val position = 1
        val item1 = mock<TestData>()
        val item2 = mock<TestData>()
        cut.setItems(listOf(item1, item2))

        // When
        val actualValue = cut.getItemAt(position)

        // Then
        assertEquals(item2, actualValue)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items when getItemAt then throws IndexOutOfBoundsException`() {
        // Given
        val position = 0

        // When
        cut.getItemAt(position)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given new items when setItems then sets expected items and notifies adapter`() {
        // Given
        val item1 = mock<TestData>()
        val item2 = mock<TestData>()
        val item3 = mock<TestData>()
        val items = listOf(item1, item2, item3)
        val expectedSize = 3

        // When
        cut.setItems(items)
        val actualSize = cut.itemsSize
        val actualItem1 = cut.getItemAt(0)
        val actualItem2 = cut.getItemAt(1)
        val actualItem3 = cut.getItemAt(2)

        // Then
        assertEquals(expectedSize, actualSize)
        assertEquals(item1, actualItem1)
        assertEquals(item2, actualItem2)
        assertEquals(item3, actualItem3)
        inOrder(adapter) {
            verify(adapter).notifyItemRangeRemoved(0, 0)
            verify(adapter).notifyItemRangeInserted(0, expectedSize)
        }
    }

    @Test
    fun `Given old items and new item when insertItem then item inserted and adapter notified`() {
        // Given
        val oldItem1 = mock<TestData>()
        val oldItem2 = mock<TestData>()
        cut.setItems(listOf(oldItem1, oldItem2))
        val position = 1
        val newItem = mock<TestData>()
        val expectedSize = 3

        // When
        cut.insertItem(position, newItem)
        val actualSize = cut.itemsSize
        val actualItem1 = cut.getItemAt(0)
        val actualItem2 = cut.getItemAt(1)
        val actualItem3 = cut.getItemAt(2)

        // Then
        assertEquals(expectedSize, actualSize)
        assertEquals(oldItem1, actualItem1)
        assertEquals(newItem, actualItem2)
        assertEquals(oldItem2, actualItem3)
        verify(adapter).notifyItemInserted(position)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items and positive index when insertItem then throws IndexOutOfBoundsException`() {
        // Given
        val position = 1
        val item = mock<TestData>()

        // When
        cut.insertItem(position, item)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given old and new items when insertItems then inserts items and notifies adapter`() {
        // Given
        val oldItem1 = mock<TestData>()
        val oldItem2 = mock<TestData>()
        cut.setItems(listOf(oldItem1, oldItem2))
        val position = 1
        val newItem1 = mock<TestData>()
        val newItem2 = mock<TestData>()
        val items = listOf(newItem1, newItem2)
        val expectedInserted = 2
        val expectedSize = 4

        // When
        cut.insertItems(position, items)
        val actualSize = cut.itemsSize
        val actualItem1 = cut.getItemAt(0)
        val actualItem2 = cut.getItemAt(1)
        val actualItem3 = cut.getItemAt(2)
        val actualItem4 = cut.getItemAt(3)

        // Then
        assertEquals(expectedSize, actualSize)
        assertEquals(oldItem1, actualItem1)
        assertEquals(newItem1, actualItem2)
        assertEquals(newItem2, actualItem3)
        assertEquals(oldItem2, actualItem4)
        verify(adapter).notifyItemRangeInserted(position, expectedInserted)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items and positive index when insertItems then throws IndexOutOfBoundsException`() {
        // Given
        val position = 1
        val items = listOf<TestData>()

        // When
        cut.insertItems(position, items)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given old items and new item when changeItem then updates item and notifies adapter`() {
        // Given
        val oldItem1 = mock<TestData>()
        val oldItem2 = mock<TestData>()
        val oldItem3 = mock<TestData>()
        cut.setItems(listOf(oldItem1, oldItem2, oldItem3))
        val position = 1
        val newItem = mock<TestData>()
        val expectedSize = 3

        // When
        cut.changeItem(position, newItem)
        val actualSize = cut.itemsSize
        val actualItem1 = cut.getItemAt(0)
        val actualItem2 = cut.getItemAt(1)
        val actualItem3 = cut.getItemAt(2)

        // Then
        assertEquals(expectedSize, actualSize)
        assertEquals(oldItem1, actualItem1)
        assertEquals(newItem, actualItem2)
        assertEquals(oldItem3, actualItem3)
        verify(adapter).notifyItemChanged(position)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items when changeItem then throws IndexOutOfBoundsException`() {
        // Given
        val position = 0
        val item = mock<TestData>()

        // When
        cut.changeItem(position, item)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given item and payload when changeItem then changes item`() {
        // Given
        val position = 1
        val oldItem1 = mock<TestData>()
        val oldItem2 = mock<TestData>()
        cut.setItems(listOf(oldItem1, oldItem2))
        val newItem = mock<TestData>()
        val payload = mock<Any>()

        // When
        cut.changeItem(position, newItem, payload)
        val actual1 = cut.getItemAt(0)
        val actual2 = cut.getItemAt(1)

        // Then
        assertEquals(oldItem1, actual1)
        assertEquals(newItem, actual2)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given item and payload when changeItem then throws IndexOutOfBoundsException`() {
        // Given
        val position = 0
        val item = mock<TestData>()
        val payload = mock<Any>()

        // When
        cut.changeItem(position, item, payload)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given old and new items when changeItems then updates items and notifies adapter`() {
        // Given
        val position = 1
        val oldItem1 = mock<TestData>()
        cut.setItems(listOf(oldItem1, mock(), mock()))
        val newItem1 = mock<TestData>()
        val newItem2 = mock<TestData>()
        val newItems = listOf(newItem1, newItem2)
        val expectedChangeSize = 2

        // When
        cut.changeItems(position, newItems)
        val actual1 = cut.getItemAt(0)
        val actual2 = cut.getItemAt(1)
        val actual3 = cut.getItemAt(2)

        // Then
        assertEquals(oldItem1, actual1)
        assertEquals(newItem1, actual2)
        assertEquals(newItem2, actual3)
        verify(adapter).notifyItemRangeChanged(position, expectedChangeSize)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items when changeItems then throws IndexOutOfBoundsException`() {
        // Given
        val position = 0
        val items = listOf<TestData>(mock())

        // When
        cut.changeItems(position, items)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given old and new items and payload when changeItems then updates items and notifies adapter`() {
        // Given
        val position = 1
        val oldItem1 = mock<TestData>()
        cut.setItems(listOf(oldItem1, mock(), mock()))
        val newItem1 = mock<TestData>()
        val newItem2 = mock<TestData>()
        val newItems = listOf(newItem1, newItem2)
        val payload = mock<Any>()
        val expectedChangeSize = 2

        // When
        cut.changeItems(position, newItems, payload)
        val actual1 = cut.getItemAt(0)
        val actual2 = cut.getItemAt(1)
        val actual3 = cut.getItemAt(2)

        // Then
        assertEquals(oldItem1, actual1)
        assertEquals(newItem1, actual2)
        assertEquals(newItem2, actual3)
        verify(adapter).notifyItemRangeChanged(position, expectedChangeSize, payload)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given items and payload when changeItems then throws exception`() {
        // Given
        val position = 0
        val items = listOf<TestData>(mock())
        val payload = mock<Any>()

        // When
        cut.changeItems(position, items, payload)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given from and to positions when moveItem then moves item and notifies adapter`() {
        // Given
        val item1 = mock<TestData>()
        val item2 = mock<TestData>()
        val item3 = mock<TestData>()
        val item4 = mock<TestData>()
        cut.setItems(listOf(item1, item2, item3, item4))
        val fromPosition = 0
        val toPosition = 2

        // When
        cut.moveItem(fromPosition, toPosition)
        val actual1 = cut.getItemAt(0)
        val actual2 = cut.getItemAt(1)
        val actual3 = cut.getItemAt(2)
        val actual4 = cut.getItemAt(3)

        // Then
        assertEquals(item2, actual1)
        assertEquals(item3, actual2)
        assertEquals(item1, actual3)
        assertEquals(item4, actual4)
        verify(adapter).notifyItemMoved(fromPosition, toPosition)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items when moveItem then throws IndexOutOfBoundsException`() {
        // Given
        val fromPosition = 0
        val toPosition = 0

        // When
        cut.moveItem(fromPosition, toPosition)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given items when removeItem then removes expected item and notifies adapter`() {
        // Given
        val item1 = mock<TestData>()
        val item2 = mock<TestData>()
        val item3 = mock<TestData>()
        cut.setItems(listOf(item1, item2, item3))
        val position = 1
        val expectedSize = 2

        // When
        cut.removeItem(position)
        val actualSize = cut.itemsSize
        val actualItem1 = cut.getItemAt(0)
        val actualItem2 = cut.getItemAt(1)

        // Then
        assertEquals(expectedSize, actualSize)
        assertEquals(item1, actualItem1)
        assertEquals(item3, actualItem2)
        verify(adapter).notifyItemRemoved(position)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items when removeItem then throws IndexOutOfBoundsException`() {
        // Given
        val position = 0

        // When
        cut.removeItem(position)

        // Then
        // Exception is thrown
    }

    @Test
    fun `Given position and count when removeItems then removes items`() {
        // Given
        val item1 = mock<TestData>()
        val item2 = mock<TestData>()
        val item3 = mock<TestData>()
        val item4 = mock<TestData>()
        cut.setItems(listOf(item1, item2, item3, item4))
        val position = 1
        val itemCount = 2
        val expectedSize = 2

        // When
        cut.removeItems(position, itemCount)
        val actualSize = cut.itemsSize
        val actualItem1 = cut.getItemAt(0)
        val actualItem2 = cut.getItemAt(1)

        // Then
        assertEquals(expectedSize, actualSize)
        assertEquals(item1, actualItem1)
        assertEquals(item4, actualItem2)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `Given no items when removeItems then throws IndexOutOfBoundsException`() {
        // Given
        val position = 0
        val itemCount = 1

        // When
        cut.removeItems(position, itemCount)

        // Then
        // Exception is thrown
    }
}
