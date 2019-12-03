package com.mitteloupe.solid.service.handler.common

import android.app.Service
import android.content.Intent
import android.os.Message
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class IntentHandlerTest {
    private lateinit var cut: IntentHandler

    @Mock
    lateinit var service: Service

    @Mock
    lateinit var onIntentReceived: (intent: Intent?) -> Unit

    private val debuggingName = "debuggingName"

    @Mock
    lateinit var serviceThreadWrapper: ServiceThreadWrapper

    @Before
    fun setUp() {
        cut = IntentHandler(service, onIntentReceived, debuggingName, serviceThreadWrapper)
    }

    @Test
    fun `When onCreate then sets up service thread wrapper`() {
        // When
        cut.onCreate()

        // Then
        verify(serviceThreadWrapper).setUp()
    }

    @Test
    fun `Given message when onStartCommand then sends expected message and returns expected value`() {
        // Given
        val intent = mock<Intent>()
        val flags = 2
        val startId = 3
        val message = mock<Message>()
        given { serviceThreadWrapper.obtainMessage() }.willReturn(message)
        val expected = Service.START_NOT_STICKY

        // When
        val actualValue = cut.onStartCommand(intent, flags, startId)

        // Then
        assertEquals(expected, actualValue)

        val messageCaptor = argumentCaptor<Message>()
        verify(serviceThreadWrapper).sendMessage(messageCaptor.capture())
        val actualMessage = messageCaptor.firstValue
        assertEquals(startId, actualMessage.arg1)
        assertEquals(intent, actualMessage.obj)
    }

    @Test
    fun `Given message and redelivery when onStartCommand then returns expected value`() {
        // Given
        val intent = mock<Intent>()
        val flags = 2
        val startId = 3
        val message = mock<Message>()
        given { serviceThreadWrapper.obtainMessage() }.willReturn(message)
        val expected = Service.START_REDELIVER_INTENT
        cut.isRedelivery = true

        // When
        val actualValue = cut.onStartCommand(intent, flags, startId)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `When onDestroy then quits service thread wrapper`() {
        // When
        cut.onDestroy()

        // Then
        verify(serviceThreadWrapper).quit()
    }
}
