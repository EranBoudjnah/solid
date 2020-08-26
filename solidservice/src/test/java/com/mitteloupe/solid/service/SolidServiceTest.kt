package com.mitteloupe.solid.service

import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.IBinder
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mitteloupe.solid.service.handler.BindingHandler
import com.mitteloupe.solid.service.handler.ConfigurationChangeHandler
import com.mitteloupe.solid.service.handler.LifecycleHandler
import com.mitteloupe.solid.service.handler.MemoryHandler
import com.mitteloupe.solid.service.handler.START_DEFAULT_BEHAVIOUR
import com.mitteloupe.solid.service.handler.TaskRemovalHandler
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
class SolidServiceTest {
    private lateinit var cut: TestService

    @Before
    fun setUp() {
        cut = TestService()
    }

    @Test
    fun `Given lifecycle handlers when onCreate then delegates to all handlers`() {
        // Given
        val lifecycleHandler1 = mock<LifecycleHandler>()
        val lifecycleHandler2 = mock<LifecycleHandler>()
        val lifecycleHandler3 = mock<LifecycleHandler>()
        cut.testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cut.onCreate()

        // Then
        verify(lifecycleHandler1).onCreate()
        verify(lifecycleHandler2).onCreate()
        verify(lifecycleHandler3).onCreate()
    }

    @Test
    fun `Given lifecycle handlers when onStartCommand then delegates to handlers until expecteed value returned`() {
        // Given
        val intent = mock<Intent>()
        val flags = 1
        val startId = 2
        val lifecycleHandler1 = mock<LifecycleHandler> {
            on { onStartCommand(intent, flags, startId) }.thenReturn(START_DEFAULT_BEHAVIOUR)
        }

        val expected = 3
        val lifecycleHandler2 = mock<LifecycleHandler> {
            on { onStartCommand(intent, flags, startId) }.thenReturn(expected)
        }

        val lifecycleHandler3 = mock<LifecycleHandler> {
            on { onStartCommand(intent, flags, startId) }.thenReturn(START_DEFAULT_BEHAVIOUR)
        }

        cut.testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        val actualValue = cut.onStartCommand(intent, flags, startId)

        // Then
        assertEquals(expected, actualValue)
        verify(lifecycleHandler1).onStartCommand(intent, flags, startId)
        verify(lifecycleHandler2).onStartCommand(intent, flags, startId)
        verify(lifecycleHandler3, never()).onStartCommand(any(), any(), any())
    }

    @Test
    fun `Given lifecycle handlers when onDestroy then delegates to all handlers`() {
        // Given
        val lifecycleHandler1 = mock<LifecycleHandler>()
        val lifecycleHandler2 = mock<LifecycleHandler>()
        val lifecycleHandler3 = mock<LifecycleHandler>()
        cut.testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cut.onDestroy()

        // Then
        verify(lifecycleHandler1).onDestroy()
        verify(lifecycleHandler2).onDestroy()
        verify(lifecycleHandler3).onDestroy()
    }

    @Test
    fun `Given task removal handlers when onTaskRemoved then delegates to all handlers`() {
        // Given
        val taskRemovalHandler1 = mock<TaskRemovalHandler>()
        val taskRemovalHandler2 = mock<TaskRemovalHandler>()
        val taskRemovalHandler3 = mock<TaskRemovalHandler>()
        cut.testTaskRemovalHandlers.addAll(
            listOf(taskRemovalHandler1, taskRemovalHandler2, taskRemovalHandler3)
        )

        val rootIntent = mock<Intent>()

        // When
        cut.onTaskRemoved(rootIntent)

        // Then
        verify(taskRemovalHandler1).onTaskRemoved(rootIntent)
        verify(taskRemovalHandler2).onTaskRemoved(rootIntent)
        verify(taskRemovalHandler3).onTaskRemoved(rootIntent)
    }

    @Test
    fun `Given configuration change handlers when onConfigurationChanged then delegates to all handlers`() {
        // Given
        val configurationChangeHandler1 = mock<ConfigurationChangeHandler>()
        val configurationChangeHandler2 = mock<ConfigurationChangeHandler>()
        val configurationChangeHandler3 = mock<ConfigurationChangeHandler>()
        cut.testConfigurationChangeHandlers.addAll(
            listOf(
                configurationChangeHandler1,
                configurationChangeHandler2,
                configurationChangeHandler3
            )
        )

        val newConfig = mock<Configuration>()

        // When
        cut.onConfigurationChanged(newConfig)

        // Then
        verify(configurationChangeHandler1).onConfigurationChanged(newConfig)
        verify(configurationChangeHandler2).onConfigurationChanged(newConfig)
        verify(configurationChangeHandler3).onConfigurationChanged(newConfig)
    }

    @Test
    fun `Given binding handlers when onBind then delegates calls to handlers until expected value returned`() {
        // Given
        val bindingHandler1 = mock<BindingHandler>()

        val intent = mock<Intent>()
        val expected = mock<IBinder>()
        val bindingHandler2 = mock<BindingHandler> {
            on { onBind(intent) }.thenReturn(expected)
        }

        val bindingHandler3 = mock<BindingHandler>()

        cut.testBindingHandlers.addAll(
            listOf(bindingHandler1, bindingHandler2, bindingHandler3)
        )

        // When
        val actualValue = cut.onBind(intent)

        // Then
        assertEquals(expected, actualValue)
        verify(bindingHandler1).onBind(intent)
        verify(bindingHandler2).onBind(intent)
        verify(bindingHandler3, never()).onBind(any())
    }

    @Test
    fun `Given binding handlers when onRebind then delegates to all handlers`() {
        // Given
        val bindingHandler1 = mock<BindingHandler>()
        val bindingHandler2 = mock<BindingHandler>()
        val bindingHandler3 = mock<BindingHandler>()
        cut.testBindingHandlers.addAll(
            listOf(bindingHandler1, bindingHandler2, bindingHandler3)
        )

        val intent = mock<Intent>()

        // When
        cut.onRebind(intent)

        // Then
        verify(bindingHandler1).onRebind(intent)
        verify(bindingHandler2).onRebind(intent)
        verify(bindingHandler3).onRebind(intent)
    }

    @Test
    fun `Given binding handlers when onUnbind then delegates to all handlers and returns expected value`() {
        // Given
        val bindingHandler1 = mock<BindingHandler>()

        val intent = mock<Intent>()
        val expected = true
        val bindingHandler2 = mock<BindingHandler> {
            on { onUnbind(intent) }.thenReturn(expected)
        }

        val bindingHandler3 = mock<BindingHandler>()
        cut.testBindingHandlers.addAll(
            listOf(bindingHandler1, bindingHandler2, bindingHandler3)
        )

        // When
        val actualValue = cut.onUnbind(intent)

        // Then
        assertEquals(expected, actualValue)
        verify(bindingHandler1).onUnbind(intent)
        verify(bindingHandler2).onUnbind(intent)
        verify(bindingHandler3).onUnbind(intent)
    }

    @Test
    fun `Given memory handlers when onLowMemory then delegates to all handlers`() {
        // Given
        val memoryHandler1 = mock<MemoryHandler>()
        val memoryHandler2 = mock<MemoryHandler>()
        val memoryHandler3 = mock<MemoryHandler>()
        cut.testMemoryHandlers.addAll(
            listOf(memoryHandler1, memoryHandler2, memoryHandler3)
        )

        // When
        cut.onLowMemory()

        // Then
        verify(memoryHandler1).onLowMemory()
        verify(memoryHandler2).onLowMemory()
        verify(memoryHandler3).onLowMemory()
    }

    @Test
    fun `Given memory handlers when onTrimMemory then delegates to all handlers`() {
        // Given
        val memoryHandler1 = mock<MemoryHandler>()
        val memoryHandler2 = mock<MemoryHandler>()
        val memoryHandler3 = mock<MemoryHandler>()
        cut.testMemoryHandlers.addAll(
            listOf(memoryHandler1, memoryHandler2, memoryHandler3)
        )

        val level = 123

        // When
        cut.onTrimMemory(level)

        // Then
        verify(memoryHandler1).onTrimMemory(level)
        verify(memoryHandler2).onTrimMemory(level)
        verify(memoryHandler3).onTrimMemory(level)
    }
}

class TestService : SolidService() {
    val testBindingHandlers = mutableListOf<BindingHandler>()
    override val bindingHandlers: List<BindingHandler>
        get() = testBindingHandlers

    val testConfigurationChangeHandlers = mutableListOf<ConfigurationChangeHandler>()
    override val configurationChangeHandlers: List<ConfigurationChangeHandler>
        get() = testConfigurationChangeHandlers

    val testLifecycleHandlers = mutableListOf<LifecycleHandler>()
    override val lifecycleHandlers: List<LifecycleHandler>
        get() = testLifecycleHandlers

    val testMemoryHandlers = mutableListOf<MemoryHandler>()
    override val memoryHandlers: List<MemoryHandler>
        get() = testMemoryHandlers

    val testTaskRemovalHandlers = mutableListOf<TaskRemovalHandler>()
    override val taskRemovalHandlers: List<TaskRemovalHandler>
        get() = testTaskRemovalHandlers
}
