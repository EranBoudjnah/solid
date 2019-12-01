package com.mitteloupe.solid.fragment

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mitteloupe.solid.fragment.handler.ContextMenuHandler
import com.mitteloupe.solid.fragment.handler.InflationHandler
import com.mitteloupe.solid.fragment.handler.LifecycleHandler
import com.mitteloupe.solid.fragment.handler.OptionsMenuHandler
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.clearInvocations
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SolidFragmentTest {
    private lateinit var fragmentScenario: FragmentScenario<TestFragment>
    private lateinit var cut: TestFragment

    @Before
    fun setUp() {
    }

    @Test
    fun `Given lifecycle handlers when fragment created then onAttach called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        // When
        launchFragmentInContainer {
            cut = TestFragment().apply {
                testLifecycleHandlers.addAll(
                    listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
                )
            }
            cut
        }

        // Then
        verify(lifecycleHandler1).onAttach(any())
        verify(lifecycleHandler2).onAttach(any())
        verify(lifecycleHandler3).onAttach(any())
    }

    @Test
    fun `Given lifecycle handlers when fragment created then onCreate called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        // When
        launchFragmentInContainer {
            cut = TestFragment().apply {
                testLifecycleHandlers.addAll(
                    listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
                )
            }
            cut
        }

        // Then
        verify(lifecycleHandler1).onCreate(null)
        verify(lifecycleHandler2).onCreate(null)
        verify(lifecycleHandler3).onCreate(null)
    }

    @Test
    fun `Given lifecycle handlers and bundle when fragment created then onCreate called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        val fragmentArgs = mock<Bundle>()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }
        clearInvocations(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)

        // When
        cut.onCreate(fragmentArgs)

        // Then
        verify(lifecycleHandler1).onCreate(fragmentArgs)
        verify(lifecycleHandler2).onCreate(fragmentArgs)
        verify(lifecycleHandler3).onCreate(fragmentArgs)
    }

    @Test
    fun `Given lifecycle handlers and bundle when fragment creating view then onCreateView called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()

        val inflater = mock<LayoutInflater>()
        val container = mock<ViewGroup>()
        val savedInstanceState = mock<Bundle>()
        val expectedValue = mock<View>()
        val lifecycleHandler2: LifecycleHandler = mock {
            on { onCreateView(inflater, container, savedInstanceState) }
                .thenReturn(expectedValue)
        }

        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }
        clearInvocations(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)

        // When
        val actualValue = cut.onCreateView(inflater, container, savedInstanceState)

        // Then
        assertEquals(expectedValue, actualValue)
        verify(lifecycleHandler1).onCreateView(inflater, container, savedInstanceState)
        verify(lifecycleHandler2).onCreateView(inflater, container, savedInstanceState)
        verify(lifecycleHandler3, never()).onCreateView(any(), any(), any())
    }

    @Test
    fun `Given lifecycle handlers and bundle when fragment view created then onCreateView called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }
        clearInvocations(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)

        val view = mock<View>()
        val savedInstanceState = mock<Bundle>()

        // When
        cut.onViewCreated(view, savedInstanceState)

        // Then
        verify(lifecycleHandler1).onViewCreated(view, savedInstanceState)
        verify(lifecycleHandler2).onViewCreated(view, savedInstanceState)
        verify(lifecycleHandler3).onViewCreated(view, savedInstanceState)
    }

    @Test
    fun `Given lifecycle handlers and bundle when activity created then onActivityCreated called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }
        clearInvocations(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)

        val savedInstanceState = mock<Bundle>()

        // When
        cut.onActivityCreated(savedInstanceState)

        // Then
        verify(lifecycleHandler1).onActivityCreated(savedInstanceState)
        verify(lifecycleHandler2).onActivityCreated(savedInstanceState)
        verify(lifecycleHandler3).onActivityCreated(savedInstanceState)
    }

    @Test
    fun `Given lifecycle handlers and bundle when view state restored then onViewStateRestored called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }
        clearInvocations(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)

        val savedInstanceState = mock<Bundle>()

        // When
        cut.onViewStateRestored(savedInstanceState)

        // Then
        verify(lifecycleHandler1).onViewStateRestored(savedInstanceState)
        verify(lifecycleHandler2).onViewStateRestored(savedInstanceState)
        verify(lifecycleHandler3).onViewStateRestored(savedInstanceState)
    }

    @Test
    fun `Given lifecycle handlers when fragment started then onStart called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }

        // When
        fragmentScenario.moveToState(Lifecycle.State.STARTED)

        // Then
        verify(lifecycleHandler1).onStart()
        verify(lifecycleHandler2).onStart()
        verify(lifecycleHandler3).onStart()
    }

    @Test
    fun `Given lifecycle handlers when fragment resumed then onResume called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }

        // When
        fragmentScenario.moveToState(Lifecycle.State.RESUMED)

        // Then
        verify(lifecycleHandler1).onResume()
        verify(lifecycleHandler2).onResume()
        verify(lifecycleHandler3).onResume()
    }

    @Test
    fun `Given lifecycle handlers when fragment paused then onPause called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }

        // When
        cut.onPause()

        // Then
        verify(lifecycleHandler1).onPause()
        verify(lifecycleHandler2).onPause()
        verify(lifecycleHandler3).onPause()
    }

    @Test
    fun `Given lifecycle handlers when fragment stopped then onStop called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }

        // When
        cut.onStop()

        // Then
        verify(lifecycleHandler1).onStop()
        verify(lifecycleHandler2).onStop()
        verify(lifecycleHandler3).onStop()
    }

    @Test
    fun `Given lifecycle handlers when view destroyed then onDestroyView and onDestroy called in order`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testLifecycleHandlers.addAll(
                listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
            )
        }

        // When
        fragmentScenario.moveToState(Lifecycle.State.DESTROYED)

        // Then
        inOrder(lifecycleHandler1) {
            verify(lifecycleHandler1).onDestroyView()
            verify(lifecycleHandler1).onDestroy()
        }
        inOrder(lifecycleHandler2) {
            verify(lifecycleHandler2).onDestroyView()
            verify(lifecycleHandler2).onDestroy()
        }
        inOrder(lifecycleHandler3) {
            verify(lifecycleHandler3).onDestroyView()
            verify(lifecycleHandler3).onDestroy()
        }
    }

    @Test
    fun `Given context menu handlers when onCreateContextMenu then delegates to handlers`() {
        // Given
        val contextMenuHandler1: ContextMenuHandler = mock()
        val contextMenuHandler2: ContextMenuHandler = mock()
        val contextMenuHandler3: ContextMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testContextMenuHandlers.addAll(
                listOf(contextMenuHandler1, contextMenuHandler2, contextMenuHandler3)
            )
        }

        val menu = mock<ContextMenu>()
        val view = mock<View>()
        val menuInfo = mock<ContextMenu.ContextMenuInfo>()

        // When
        cut.onCreateContextMenu(menu, view, menuInfo)

        // Then
        verify(contextMenuHandler1).onCreateContextMenu(menu, view, menuInfo)
        verify(contextMenuHandler2).onCreateContextMenu(menu, view, menuInfo)
        verify(contextMenuHandler3).onCreateContextMenu(menu, view, menuInfo)
    }

    @Test
    fun `Given context menu handlers when onContextItemSelected then delegates to handlers until consumed`() {
        // Given
        val contextMenuHandler1: ContextMenuHandler = mock()

        val item = mock<MenuItem>()
        val contextMenuHandler2: ContextMenuHandler = mock {
            on { onContextItemSelected(item) }.thenReturn(true)
        }

        val contextMenuHandler3: ContextMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testContextMenuHandlers.addAll(
                listOf(contextMenuHandler1, contextMenuHandler2, contextMenuHandler3)
            )
        }

        // When
        val actualValue = cut.onContextItemSelected(item)

        // Then
        assertTrue(actualValue)
        verify(contextMenuHandler1).onContextItemSelected(item)
        verify(contextMenuHandler2).onContextItemSelected(item)
        verify(contextMenuHandler3, never()).onContextItemSelected(any())
    }

    @Test
    fun `Given options menu handlers when onPrepareOptionsMenu then delegates to all handlers`() {
        // Given
        val menu = mock<Menu>()
        val optionsMenuHandler1: OptionsMenuHandler = mock()
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testOptionsMenuHandlers.addAll(
                listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
            )
        }

        // When
        cut.onPrepareOptionsMenu(menu)

        // Then
        verify(optionsMenuHandler1).onPrepareOptionsMenu(menu)
        verify(optionsMenuHandler2).onPrepareOptionsMenu(menu)
        verify(optionsMenuHandler3).onPrepareOptionsMenu(menu)
    }

    @Test
    fun `Given options menu handlers when onCreateOptionsMenu then delegates to all handlers`() {
        // Given
        val menu = mock<Menu>()
        val inflater = mock<MenuInflater>()
        val optionsMenuHandler1: OptionsMenuHandler = mock()
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testOptionsMenuHandlers.addAll(
                listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
            )
        }

        // When
        cut.onCreateOptionsMenu(menu, inflater)

        // Then
        verify(optionsMenuHandler1).onCreateOptionsMenu(menu, inflater)
        verify(optionsMenuHandler2).onCreateOptionsMenu(menu, inflater)
        verify(optionsMenuHandler3).onCreateOptionsMenu(menu, inflater)
    }

    @Test
    fun `Given options menu handlers when onOptionsItemSelected then delegates to handlers until handled`() {
        // Given
        val optionsMenuHandler1: OptionsMenuHandler = mock()
        val item = mock<MenuItem>()
        val optionsMenuHandler2: OptionsMenuHandler = mock {
            on { onOptionsItemSelected(item) }.thenReturn(true)
        }
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testOptionsMenuHandlers.addAll(
                listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
            )
        }

        // When
        cut.onOptionsItemSelected(item)

        // Then
        verify(optionsMenuHandler1).onOptionsItemSelected(item)
        verify(optionsMenuHandler2).onOptionsItemSelected(item)
        verify(optionsMenuHandler3, never()).onOptionsItemSelected(any())
    }

    @Test
    fun `Given options menu handlers when onOptionsMenuClosed then delegates to all handlers`() {
        // Given
        val optionsMenuHandler1: OptionsMenuHandler = mock()
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testOptionsMenuHandlers.addAll(
                listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
            )
        }

        val menu = mock<Menu>()

        // When
        cut.onOptionsMenuClosed(menu)

        // Then
        verify(optionsMenuHandler1).onOptionsMenuClosed(menu)
        verify(optionsMenuHandler2).onOptionsMenuClosed(menu)
        verify(optionsMenuHandler3).onOptionsMenuClosed(menu)
    }

    @Test
    fun `Given options menu handlers when onDestroyOptionsMenu then delegates to all handlers`() {
        // Given
        val optionsMenuHandler1: OptionsMenuHandler = mock()
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testOptionsMenuHandlers.addAll(
                listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
            )
        }

        // When
        cut.onDestroyOptionsMenu()

        // Then
        verify(optionsMenuHandler1).onDestroyOptionsMenu()
        verify(optionsMenuHandler2).onDestroyOptionsMenu()
        verify(optionsMenuHandler3).onDestroyOptionsMenu()
    }

    @Test
    fun `Given inflation handlers when onInflate then delegates to all handlers`() {
        // Given
        val inflationHandler1: InflationHandler = mock()
        val inflationHandler2: InflationHandler = mock()
        val inflationHandler3: InflationHandler = mock()

        givenFragmentWithSetup { testFragment ->
            testFragment.testInflationHandlers.addAll(
                listOf(inflationHandler1, inflationHandler2, inflationHandler3)
            )
        }

        val context = mock<Context>()
        val attributes = mock<AttributeSet>()
        val savedInstanceState = mock<Bundle>()

        // When
        cut.onInflate(context, attributes, savedInstanceState)

        // Then
        verify(inflationHandler1).onInflate(context, attributes, savedInstanceState)
        verify(inflationHandler2).onInflate(context, attributes, savedInstanceState)
        verify(inflationHandler3).onInflate(context, attributes, savedInstanceState)
    }

    private fun givenFragmentWithSetup(setup: (TestFragment) -> Unit) {
        fragmentScenario = launchFragmentInContainer {
            cut = TestFragment().apply {
                setup(this)
            }
            cut
        }
    }
}

class TestFragment : SolidFragment() {
    val testLifecycleHandlers = mutableListOf<LifecycleHandler>()
    override val lifecycleHandlers: List<LifecycleHandler>
        get() = testLifecycleHandlers

    val testContextMenuHandlers = mutableListOf<ContextMenuHandler>()
    override val contextMenuHandlers: List<ContextMenuHandler>
        get() = testContextMenuHandlers

    val testOptionsMenuHandlers = mutableListOf<OptionsMenuHandler>()
    override val optionsMenuHandlers: List<OptionsMenuHandler>
        get() = testOptionsMenuHandlers

    val testInflationHandlers = mutableListOf<InflationHandler>()
    override val inflationHandlers: List<InflationHandler>
        get() = testInflationHandlers
}
