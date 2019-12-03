package com.mitteloupe.solid.service.handler.common

import android.app.Service
import android.content.Intent
import android.os.HandlerThread
import android.os.Looper
import android.os.Message
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
class ServiceThreadWrapperTest {
    private lateinit var cut: ServiceThreadWrapper

    private val debuggingName = "debuggingName"

    @Mock
    lateinit var service: Service

    @Mock
    lateinit var onIntentReceived: (intent: Intent?) -> Unit

    @Mock
    lateinit var thread: HandlerThread

    @Mock
    lateinit var looper: Looper

    @Mock
    lateinit var serviceHandlerProvider: (Looper, ServiceHandler.Callback) -> ServiceHandler

    @Mock
    lateinit var serviceHandler: ServiceHandler

    @Before
    fun setUp() {
        cut = ServiceThreadWrapper(
            debuggingName,
            service,
            onIntentReceived,
            lazy { thread },
            serviceHandlerProvider
        )

        given { thread.looper }.willReturn(looper)
        given { serviceHandlerProvider(looper, cut) }.willReturn(serviceHandler)
    }

    @Test
    fun `When setUp then starts thread`() {
        // When
        cut.setUp()

        // Then
        verify(thread).start()
    }

    @Test
    fun `Given handler with message when obtainMessage then returns message`() {
        // Given
        val expected = mock<Message>()
        given { serviceHandler.obtainMessage() }.willReturn(expected)

        cut.setUp()

        // When
        val actualValue = cut.obtainMessage()

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `Given message successfully sent when sendMessage then return true`() {
        // Given
        val message = mock<Message>()
        val expected = true
        given { serviceHandler.sendMessage(message) }.willReturn(expected)

        cut.setUp()

        // When
        val actualValue = cut.sendMessage(message)

        // Then
        assertEquals(expected, actualValue)
    }

    @Test
    fun `When quit then quits looper`() {
        // Given
        cut.setUp()

        // When
        cut.quit()

        // Then
        verify(looper).quit()
    }

    @Test
    fun `Given intent when handleIntent then delegates intent to listener`() {
        // Given
        val intent = mock<Intent>()

        cut.setUp()

        // When
        cut.handleIntent(intent)

        // Then
        verify(onIntentReceived)(intent)
    }

    @Test
    fun `When stopService then stops service`() {
        // When
        cut.stopService()

        // Then
        verify(service).stopSelf()
    }
}
