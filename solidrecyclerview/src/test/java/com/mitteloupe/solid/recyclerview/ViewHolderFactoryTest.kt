package com.mitteloupe.solid.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.solid.recyclerview.data.TestViewHolder
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert.assertEquals
import org.junit.Test

class ViewHolderFactoryTest {
    @Test
    fun `Given view when holdView then returns ViewHolder with provided view`() {
        // Given
        val cut = ViewHolderFactory<TestViewHolder>()
        val expectedView = mock<View>()

        // When
        val actualValue = cut.holdView<TestViewHolder>(expectedView)

        // Then
        assertEquals(expectedView, actualValue.itemView)
    }

    @Test(expected = NoUsableConstructorException::class)
    fun `Given constructor with no parameters when holdView then throws NoUsableConstructorException`() {
        // Given
        val cut = ViewHolderFactory<ViewHolderWithNoConstructorParameters>()
        val view = mock<View>()

        // When
        cut.holdView<ViewHolderWithNoConstructorParameters>(view)

        // Then
        // Exception is thrown
    }

    @Test(expected = NoUsableConstructorException::class)
    fun `Given constructor with two parameters when holdView then throws NoUsableConstructorException`() {
        // Given
        val cut = ViewHolderFactory<ViewHolderWithTwoConstructorParameters>()
        val view = mock<View>()

        // When
        cut.holdView<ViewHolderWithTwoConstructorParameters>(view)

        // Then
        // Exception is thrown
    }

    @Test(expected = ViewHolderCreationException::class)
    fun `Given unexpected constructor parameters when holdView then throws ViewHolderCreationException`() {
        // Given
        val cut = ViewHolderFactory<ViewHolderWithWrongConstructorParameters>()
        val view = mock<View>()

        // When
        cut.holdView<ViewHolderWithWrongConstructorParameters>(view)

        // Then
        // Exception is thrown
    }

    private class ViewHolderWithNoConstructorParameters : RecyclerView.ViewHolder(mock())

    private class ViewHolderWithTwoConstructorParameters(
        @Suppress("UNUSED_PARAMETER") view: View,
        @Suppress("UNUSED_PARAMETER") value: Int
    ) : RecyclerView.ViewHolder(mock())

    private class ViewHolderWithWrongConstructorParameters(
        @Suppress("UNUSED_PARAMETER") value: Int
    ) : RecyclerView.ViewHolder(mock())
}
