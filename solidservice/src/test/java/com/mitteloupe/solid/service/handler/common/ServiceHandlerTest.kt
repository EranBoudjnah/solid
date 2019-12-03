package com.mitteloupe.solid.service.handler.common

import android.content.Intent
import android.os.Looper
import android.os.Message
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ServiceHandlerTest {
    private lateinit var cut: ServiceHandler

    @Mock
    lateinit var looper: Looper

    @Mock
    lateinit var callback: ServiceHandler.Callback

    @Before
    fun setUp() {
        cut = ServiceHandler(looper, callback)
    }

    @Test
    fun `Given message with intent when handleMessage then calls handleIntent and stopService on callback`() {
        // Given
        val intent = mock<Intent>()
        val message = mock<Message>().apply {
            obj = intent
        }

        // When
        cut.handleMessage(message)

        // Then
        inOrder(callback) {
            verify(callback).handleIntent(intent)
            verify(callback).stopService()
        }
    }
}
