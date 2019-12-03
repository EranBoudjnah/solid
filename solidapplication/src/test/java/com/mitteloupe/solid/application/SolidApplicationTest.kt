package com.mitteloupe.solid.application

import android.content.res.Configuration
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mitteloupe.solid.application.handler.ConfigurationChangeHandler
import com.mitteloupe.solid.application.handler.LifecycleHandler
import com.mitteloupe.solid.application.handler.MemoryHandler
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SolidApplicationTest {
    private lateinit var cut: TestApplication

    @Before
    fun setUp() {
        cut = TestApplication()
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
    fun `Given lifecycle handlers when onTerminate then delegates to all handlers`() {
        // Given
        val lifecycleHandler1 = mock<LifecycleHandler>()
        val lifecycleHandler2 = mock<LifecycleHandler>()
        val lifecycleHandler3 = mock<LifecycleHandler>()
        cut.testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cut.onTerminate()

        // Then
        verify(lifecycleHandler1).onTerminate()
        verify(lifecycleHandler2).onTerminate()
        verify(lifecycleHandler3).onTerminate()
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

private class TestApplication : SolidApplication() {
    val testLifecycleHandlers = mutableListOf<LifecycleHandler>()
    override val lifecycleHandlers: List<LifecycleHandler>
        get() = testLifecycleHandlers

    val testConfigurationChangeHandlers = mutableListOf<ConfigurationChangeHandler>()
    override val configurationChangeHandlers: List<ConfigurationChangeHandler>
        get() = testConfigurationChangeHandlers

    val testMemoryHandlers = mutableListOf<MemoryHandler>()
    override val memoryHandlers: List<MemoryHandler>
        get() = testMemoryHandlers
}