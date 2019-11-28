package com.mitteloupe.solid.activity

import android.app.Activity
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.ActionMode
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mitteloupe.solid.activity.handler.ActionModeHandler
import com.mitteloupe.solid.activity.handler.ActivityForResultCallbackHandler
import com.mitteloupe.solid.activity.handler.AnimationHandler
import com.mitteloupe.solid.activity.handler.ContextMenuHandler
import com.mitteloupe.solid.activity.handler.GenericMotionHandler
import com.mitteloupe.solid.activity.handler.InstanceStateHandler
import com.mitteloupe.solid.activity.handler.KeyHandler
import com.mitteloupe.solid.activity.handler.LifecycleHandler
import com.mitteloupe.solid.activity.handler.LocalVoiceInteractionHandler
import com.mitteloupe.solid.activity.handler.MemoryHandler
import com.mitteloupe.solid.activity.handler.MenuOpenedHandler
import com.mitteloupe.solid.activity.handler.MetaDataHandler
import com.mitteloupe.solid.activity.handler.NavigationHandler
import com.mitteloupe.solid.activity.handler.NewIntentHandler
import com.mitteloupe.solid.activity.handler.OptionsMenuHandler
import com.mitteloupe.solid.activity.handler.PermissionHandler
import com.mitteloupe.solid.activity.handler.SearchHandler
import com.mitteloupe.solid.activity.handler.TitleHandler
import com.mitteloupe.solid.activity.handler.TouchHandler
import com.mitteloupe.solid.activity.handler.ViewCreationHandler
import com.mitteloupe.solid.activity.handler.WindowAttachmentHandler
import com.mitteloupe.solid.activity.handler.WindowFocusHandler
import com.mitteloupe.solid.activity.handler.WindowModeHandler
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.hamcrest.collection.ArrayMatching.arrayContaining
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.controller.ActivityController

@RunWith(AndroidJUnit4::class)
class SolidActivityTest {
    private lateinit var cutController: ActivityController<TestActivity>

    @Before
    fun setUp() {
        cutController = Robolectric.buildActivity(TestActivity::class.java)
    }

    @Test
    fun `Given lifecycle handlers when activity created then onCreate called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.create()

        // Then
        verify(lifecycleHandler1).onCreate(null)
        verify(lifecycleHandler2).onCreate(null)
        verify(lifecycleHandler3).onCreate(null)
    }

    @Test
    fun `Given lifecycle handlers and bundle when activity created then onCreate called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        val bundle = mock<Bundle>()

        // When
        cutController.create(bundle)

        // Then
        verify(lifecycleHandler1).onCreate(bundle)
        verify(lifecycleHandler2).onCreate(bundle)
        verify(lifecycleHandler3).onCreate(bundle)
    }

    @Test
    fun `Given lifecycle handlers when user leaving activity then onUserLeaveHint called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.userLeaving()

        // Then
        verify(lifecycleHandler1).onUserLeaveHint()
        verify(lifecycleHandler2).onUserLeaveHint()
        verify(lifecycleHandler3).onUserLeaveHint()
    }

    @Test
    fun `Given lifecycle handlers when activity started then onStart called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController.create()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.start()

        // Then
        verify(lifecycleHandler1).onStart()
        verify(lifecycleHandler2).onStart()
        verify(lifecycleHandler3).onStart()
    }

    @Test
    fun `Given lifecycle handlers and bundle when activity post created then onPostCreate called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()
            .start()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        val bundle = mock<Bundle>()

        // When
        cutController.postCreate(bundle)

        // Then
        verify(lifecycleHandler1).onPostCreate(bundle)
        verify(lifecycleHandler2).onPostCreate(bundle)
        verify(lifecycleHandler3).onPostCreate(bundle)
    }

    @Test
    fun `Given lifecycle handlers when activity resumed then onResume called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.resume()

        // Then
        verify(lifecycleHandler1).onResume()
        verify(lifecycleHandler2).onResume()
        verify(lifecycleHandler3).onResume()
    }

    @Test
    fun `Given lifecycle handlers when activity paused then onPause called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()
            .visible()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.pause()

        // Then
        verify(lifecycleHandler1).onPause()
        verify(lifecycleHandler2).onPause()
        verify(lifecycleHandler3).onPause()
    }

    @Test
    fun `Given lifecycle handlers when activity stopped then onStop called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()
            .visible()
            .pause()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.stop()

        // Then
        verify(lifecycleHandler1).onStop()
        verify(lifecycleHandler2).onStop()
        verify(lifecycleHandler3).onStop()
    }

    @Test
    fun `Given lifecycle handlers when activity restarted then onRestart called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()
            .visible()
            .pause()
            .stop()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.restart()

        // Then
        inOrder(lifecycleHandler1) {
            verify(lifecycleHandler1).onRestart()
            verify(lifecycleHandler1).onStart()
        }
        inOrder(lifecycleHandler2) {
            verify(lifecycleHandler2).onRestart()
            verify(lifecycleHandler2).onStart()
        }
        inOrder(lifecycleHandler3) {
            verify(lifecycleHandler3).onRestart()
            verify(lifecycleHandler3).onStart()
        }
    }

    @Test
    fun `Given lifecycle handlers when activity destroyed then onDestroy called`() {
        // Given
        val lifecycleHandler1: LifecycleHandler = mock()
        val lifecycleHandler2: LifecycleHandler = mock()
        val lifecycleHandler3: LifecycleHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()
            .visible()
            .pause()
            .stop()

        cutController.get().testLifecycleHandlers.addAll(
            listOf(lifecycleHandler1, lifecycleHandler2, lifecycleHandler3)
        )

        // When
        cutController.destroy()

        // Then
        verify(lifecycleHandler1).onDestroy()
        verify(lifecycleHandler2).onDestroy()
        verify(lifecycleHandler3).onDestroy()
    }

    @Test
    fun `Given view creation handlers when activity created then onCreateView called and expected views returned`() {
        // Given
        val viewCreationHandler1: ViewCreationHandler = mock()
        val activity = cutController.get()
        val view = FrameLayout(activity)
        val viewCreationHandler2: ViewCreationHandler = mock {
            on { onCreateView(eq(null), any(), any(), any()) }
                .thenReturn(view)
        }
        val viewCreationHandler3: ViewCreationHandler = mock()

        activity.testViewCreationHandlers.addAll(
            listOf(viewCreationHandler1, viewCreationHandler2, viewCreationHandler3)
        )

        val expectedView = listOf(view, null, null)

        // When
        cutController.create()
        val actualValues = cutController.get().createdViews

        // Then
        verify(viewCreationHandler1).onCreateView(eq(null), any(), any(), any())
        verify(viewCreationHandler2).onCreateView(eq(null), any(), any(), any())
        verify(viewCreationHandler3, never()).onCreateView(eq(null), any(), any(), any())
        assertEquals(expectedView, actualValues)
    }

    @Test
    fun `Given view creation handlers when content view set then onContentChanged called`() {
        // Given
        val viewCreationHandler1: ViewCreationHandler = mock()
        val viewCreationHandler2: ViewCreationHandler = mock()
        val viewCreationHandler3: ViewCreationHandler = mock()
        val activity = cutController.get()

        cutController.create()

        activity.testViewCreationHandlers.addAll(
            listOf(viewCreationHandler1, viewCreationHandler2, viewCreationHandler3)
        )

        // When
        activity.setContentView(View(activity))

        // Then
        verify(viewCreationHandler1).onContentChanged()
        verify(viewCreationHandler2).onContentChanged()
        verify(viewCreationHandler3).onContentChanged()
    }

    @Test
    fun `Given title handlers when activity post created then onPostCreate called`() {
        // Given
        val titleHandler1: TitleHandler = mock()
        val titleHandler2: TitleHandler = mock()
        val titleHandler3: TitleHandler = mock()

        cutController
            .create()
            .start()

        cutController.get().testTitleHandlers.addAll(
            listOf(titleHandler1, titleHandler2, titleHandler3)
        )

        // When
        cutController.postCreate(null)

        // Then
        verify(titleHandler1).onTitleChanged("com.mitteloupe.solid.activity.TestActivity", 0)
        verify(titleHandler2).onTitleChanged("com.mitteloupe.solid.activity.TestActivity", 0)
        verify(titleHandler3).onTitleChanged("com.mitteloupe.solid.activity.TestActivity", 0)
    }

    @Test
    fun `Given title handlers and parent when title set then onChildTitleChanged called`() {
        // Given
        val titleHandler1: TitleHandler = mock()
        val titleHandler2: TitleHandler = mock()
        val titleHandler3: TitleHandler = mock()

        val cutChildController = Robolectric.buildActivity(TestChildActivity::class.java)

        cutChildController
            .create()
            .start()

        val childActivity = cutChildController.get()
        val parentActivity = cutController.get()

        parentActivity.testTitleHandlers.addAll(
            listOf(titleHandler1, titleHandler2, titleHandler3)
        )

        Activity::class.java.getDeclaredField("mParent").apply {
            isAccessible = true
            set(childActivity, parentActivity)
        }
        val title = "Title"

        // When
        childActivity.title = title

        // Then
        verify(titleHandler1)
            .onChildTitleChanged(childActivity, title)
        verify(titleHandler2)
            .onChildTitleChanged(childActivity, title)
        verify(titleHandler3)
            .onChildTitleChanged(childActivity, title)
    }

    @Test
    fun `Given instance state handlers when activity saves instance state then onSaveInstanceState called`() {
        // Given
        val instanceStateHandler1: InstanceStateHandler = mock()
        val instanceStateHandler2: InstanceStateHandler = mock()
        val instanceStateHandler3: InstanceStateHandler = mock()

        cutController
            .create()

        cutController.get().testInstanceStateHandlers.addAll(
            listOf(instanceStateHandler1, instanceStateHandler2, instanceStateHandler3)
        )

        val bundle = mock<Bundle>()

        // When
        cutController.saveInstanceState(bundle)

        // Then
        verify(instanceStateHandler1).onSaveInstanceState(bundle)
        verify(instanceStateHandler2).onSaveInstanceState(bundle)
        verify(instanceStateHandler3).onSaveInstanceState(bundle)
    }

    @Test
    fun `Given instance state handlers when activity restores instance state then onRestoreInstanceState called`() {
        // Given
        val instanceStateHandler1: InstanceStateHandler = mock()
        val instanceStateHandler2: InstanceStateHandler = mock()
        val instanceStateHandler3: InstanceStateHandler = mock()

        cutController
            .create()

        cutController.get().testInstanceStateHandlers.addAll(
            listOf(instanceStateHandler1, instanceStateHandler2, instanceStateHandler3)
        )

        val bundle = mock<Bundle>()

        // When
        cutController.restoreInstanceState(bundle)

        // Then
        verify(instanceStateHandler1).onRestoreInstanceState(bundle)
        verify(instanceStateHandler2).onRestoreInstanceState(bundle)
        verify(instanceStateHandler3).onRestoreInstanceState(bundle)
    }

    @Test
    fun `Given animation handlers when onEnterAnimationComplete then delegates to handlers`() {
        // Given
        val animationHandler1: AnimationHandler = mock()
        val animationHandler2: AnimationHandler = mock()
        val animationHandler3: AnimationHandler = mock()

        val activity = cutController.get()
        activity.testAnimationHandlers.addAll(
            listOf(animationHandler1, animationHandler2, animationHandler3)
        )

        // When
        activity.onEnterAnimationComplete()

        // Then
        verify(animationHandler1).onEnterAnimationComplete()
        verify(animationHandler1).onEnterAnimationComplete()
        verify(animationHandler1).onEnterAnimationComplete()
    }

    @Test
    fun `Given key handlers when onKeyDown then delegates to handlers and returns expected value`() {
        // Given
        val keyHandler1: KeyHandler = mock()

        val keyCode = 123
        val event = mock<KeyEvent>()
        val expectedHandling = true
        val keyHandler2: KeyHandler = mock {
            on { onKeyDown(keyCode, event) }.thenReturn(expectedHandling)
        }

        val keyHandler3: KeyHandler = mock()

        val activity = cutController.get()
        activity.testKeyHandlers.addAll(
            listOf(keyHandler1, keyHandler2, keyHandler3)
        )

        // When
        val actualValue = activity.onKeyDown(keyCode, event)

        // Then
        verify(keyHandler1).onKeyDown(keyCode, event)
        verify(keyHandler2).onKeyDown(keyCode, event)
        verify(keyHandler3, never()).onKeyDown(any(), any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given key handlers when onKeyLongPress then delegates to handlers and returns expected value`() {
        // Given
        val keyHandler1: KeyHandler = mock()

        val keyCode = 123
        val event = mock<KeyEvent>()
        val expectedHandling = true
        val keyHandler2: KeyHandler = mock {
            on { onKeyLongPress(keyCode, event) }.thenReturn(expectedHandling)
        }

        val keyHandler3: KeyHandler = mock()

        val activity = cutController.get()
        activity.testKeyHandlers.addAll(
            listOf(keyHandler1, keyHandler2, keyHandler3)
        )

        // When
        val actualValue = activity.onKeyLongPress(keyCode, event)

        // Then
        verify(keyHandler1).onKeyLongPress(keyCode, event)
        verify(keyHandler2).onKeyLongPress(keyCode, event)
        verify(keyHandler3, never()).onKeyLongPress(any(), any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given key handlers when onKeyShortcut then delegates to handlers and returns expected value`() {
        // Given
        val keyHandler1: KeyHandler = mock()

        val keyCode = 123
        val event = mock<KeyEvent>()
        val expectedHandling = true
        val keyHandler2: KeyHandler = mock {
            on { onKeyShortcut(keyCode, event) }.thenReturn(expectedHandling)
        }

        val keyHandler3: KeyHandler = mock()

        val activity = cutController.get()
        activity.testKeyHandlers.addAll(
            listOf(keyHandler1, keyHandler2, keyHandler3)
        )

        // When
        val actualValue = activity.onKeyShortcut(keyCode, event)

        // Then
        verify(keyHandler1).onKeyShortcut(keyCode, event)
        verify(keyHandler2).onKeyShortcut(keyCode, event)
        verify(keyHandler3, never()).onKeyShortcut(any(), any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given key handlers when onKeyMultiple then delegates to handlers and returns expected value`() {
        // Given
        val keyHandler1: KeyHandler = mock()

        val keyCode = 123
        val repeatCount = 3
        val event = mock<KeyEvent>()
        val expectedHandling = true
        val keyHandler2: KeyHandler = mock {
            on { onKeyMultiple(keyCode, repeatCount, event) }.thenReturn(expectedHandling)
        }

        val keyHandler3: KeyHandler = mock()

        val activity = cutController.get()
        activity.testKeyHandlers.addAll(
            listOf(keyHandler1, keyHandler2, keyHandler3)
        )

        // When
        val actualValue = activity.onKeyMultiple(keyCode, repeatCount, event)

        // Then
        verify(keyHandler1).onKeyMultiple(keyCode, repeatCount, event)
        verify(keyHandler2).onKeyMultiple(keyCode, repeatCount, event)
        verify(keyHandler3, never()).onKeyMultiple(any(), any(), any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given key handlers when onKeyUp then delegates to handlers and returns expected value`() {
        // Given
        val keyHandler1: KeyHandler = mock()

        val keyCode = 123
        val event = mock<KeyEvent>()
        val expectedHandling = true
        val keyHandler2: KeyHandler = mock {
            on { onKeyUp(keyCode, event) }.thenReturn(expectedHandling)
        }

        val keyHandler3: KeyHandler = mock()

        val activity = cutController.get()
        activity.testKeyHandlers.addAll(
            listOf(keyHandler1, keyHandler2, keyHandler3)
        )

        // When
        val actualValue = activity.onKeyUp(keyCode, event)

        // Then
        verify(keyHandler1).onKeyUp(keyCode, event)
        verify(keyHandler2).onKeyUp(keyCode, event)
        verify(keyHandler3, never()).onKeyUp(any(), any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given key handlers when onProvideKeyboardShortcuts then delegates to handlers and returns expected value`() {
        // Given
        val shortcuts = mutableListOf<KeyboardShortcutGroup>()
        val menu = mock<Menu>()
        val deviceId = 0

        val keyHandler1: KeyHandler = mock()

        val keyboardShortcut1 = mock<KeyboardShortcutGroup>()
        val keyHandler2: KeyHandler = mock {
            on { onProvideKeyboardShortcuts(shortcuts, menu, deviceId) }
                .thenAnswer {
                    shortcuts.add(keyboardShortcut1)
                    shortcuts
                }
        }

        val keyboardShortcut2 = mock<KeyboardShortcutGroup>()
        val keyHandler3: KeyHandler = mock {
            on { onProvideKeyboardShortcuts(shortcuts, menu, deviceId) }
                .thenAnswer {
                    shortcuts.add(keyboardShortcut2)
                    shortcuts
                }
        }

        val activity = cutController.get()
        activity.testKeyHandlers.addAll(
            listOf(keyHandler1, keyHandler2, keyHandler3)
        )

        // When
        activity.onProvideKeyboardShortcuts(shortcuts, menu, deviceId)

        // Then
        assertThat(shortcuts.toTypedArray(), arrayContaining(keyboardShortcut1, keyboardShortcut2))
    }

    @Test
    fun `Given touch handlers when onTouchEvent then delegates to handlers and returns expected value`() {
        // Given
        val touchHandler1: TouchHandler = mock()

        val event = mock<MotionEvent>()
        val expectedHandling = true
        val touchHandler2: TouchHandler = mock {
            on { onTouchEvent(event) }.thenReturn(expectedHandling)
        }

        val touchHandler3: TouchHandler = mock()

        val activity = cutController.get()
        activity.testTouchHandlers.addAll(
            listOf(touchHandler1, touchHandler2, touchHandler3)
        )

        // When
        val actualValue = activity.onTouchEvent(event)

        // Then
        verify(touchHandler1).onTouchEvent(event)
        verify(touchHandler2).onTouchEvent(event)
        verify(touchHandler3, never()).onTouchEvent(any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given generic motion handlers when user leaving activity then onUserInteraction called on handlers`() {
        // Given
        val genericMotionHandler1: GenericMotionHandler = mock()
        val genericMotionHandler2: GenericMotionHandler = mock()
        val genericMotionHandler3: GenericMotionHandler = mock()

        cutController
            .create()

        cutController.get().testGenericMotionHandlers.addAll(
            listOf(genericMotionHandler1, genericMotionHandler2, genericMotionHandler3)
        )

        // When
        cutController.userLeaving()

        // Then
        verify(genericMotionHandler1).onUserInteraction()
        verify(genericMotionHandler2).onUserInteraction()
        verify(genericMotionHandler3).onUserInteraction()
    }

    @Test
    fun `Given generic motion handlers when onTrackballEvent then delegates to handlers and returns expected value`() {
        // Given
        val genericMotionHandler1: GenericMotionHandler = mock()

        val event = mock<MotionEvent>()
        val expectedHandling = true
        val genericMotionHandler2: GenericMotionHandler = mock {
            on { onTrackballEvent(event) }.thenReturn(expectedHandling)
        }

        val genericMotionHandler3: GenericMotionHandler = mock()

        val activity = cutController.get()
        activity.testGenericMotionHandlers.addAll(
            listOf(genericMotionHandler1, genericMotionHandler2, genericMotionHandler3)
        )

        // When
        val actualValue = activity.onTrackballEvent(event)

        // Then
        verify(genericMotionHandler1).onTrackballEvent(event)
        verify(genericMotionHandler2).onTrackballEvent(event)
        verify(genericMotionHandler3, never()).onTrackballEvent(any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given generic motion handlers when onGenericMotionEvent then delegates to handlers and returns expected value`() {
        // Given
        val genericMotionHandler1: GenericMotionHandler = mock()

        val event = mock<MotionEvent>()
        val expectedHandling = true
        val genericMotionHandler2: GenericMotionHandler = mock {
            on { onGenericMotionEvent(event) }.thenReturn(expectedHandling)
        }

        val genericMotionHandler3: GenericMotionHandler = mock()

        val activity = cutController.get()
        activity.testGenericMotionHandlers.addAll(
            listOf(genericMotionHandler1, genericMotionHandler2, genericMotionHandler3)
        )

        // When
        val actualValue = activity.onGenericMotionEvent(event)

        // Then
        verify(genericMotionHandler1).onGenericMotionEvent(event)
        verify(genericMotionHandler2).onGenericMotionEvent(event)
        verify(genericMotionHandler3, never()).onGenericMotionEvent(any())
        assertEquals(expectedHandling, actualValue)
    }

    @Test
    fun `Given intercepting navigationHandlers when onBackPressed then delegates to handlers until cancelled`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()

        val expectedHandling = true
        val navigationHandler2: NavigationHandler = mock {
            on { onBackPressed() }.thenReturn(expectedHandling)
        }

        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        activity.onBackPressed()

        // Then
        verify(navigationHandler1).onBackPressed()
        verify(navigationHandler2).onBackPressed()
        verify(navigationHandler3, never()).onBackPressed()
        assertFalse(activity.isFinishing)
    }

    @Test
    fun `Given navigationHandlers when onBackPressed then delegates to handlers and finishes`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        activity.onBackPressed()

        // Then
        verify(navigationHandler1).onBackPressed()
        verify(navigationHandler2).onBackPressed()
        verify(navigationHandler3).onBackPressed()
        assertTrue(activity.isFinishing)
    }

    @Test
    fun `Given navigationHandlers and no parent activity when onNavigateUp then delegates to handlers and does not finish`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        val actualValue = activity.onNavigateUp()

        // Then
        verify(navigationHandler1).onNavigateUp()
        verify(navigationHandler2).onNavigateUp()
        verify(navigationHandler3).onNavigateUp()
        assertFalse(actualValue)
        assertFalse(activity.isFinishing)
    }

    @Test
    fun `Given interrupting navigationHandlers and parent activity when onNavigateUp then delegates to handlers and does not finish`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()

        val navigationHandler2: NavigationHandler = mock {
            on { onNavigateUp() }.thenReturn(true)
        }

        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        Activity::class.java.getDeclaredField("mActivityInfo").apply {
            isAccessible = true
            set(
                activity,
                ActivityInfo().apply {
                    parentActivityName = "com.mitteloupe.solid.activity.TestActivity"
                })
        }

        // When
        val actualValue = activity.onNavigateUp()

        // Then
        verify(navigationHandler1).onNavigateUp()
        verify(navigationHandler2).onNavigateUp()
        verify(navigationHandler3, never()).onNavigateUp()
        assertTrue(actualValue)
        assertFalse(activity.isFinishing)
    }

    @Test
    fun `Given navigationHandlers and parent activity when onNavigateUp then delegates to handlers and finishes`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        Activity::class.java.getDeclaredField("mActivityInfo").apply {
            isAccessible = true
            set(
                activity,
                ActivityInfo().apply {
                    parentActivityName = "com.mitteloupe.solid.activity.TestActivity"
                })
        }

        // When
        val actualValue = activity.onNavigateUp()

        // Then
        verify(navigationHandler1).onNavigateUp()
        verify(navigationHandler2).onNavigateUp()
        verify(navigationHandler3).onNavigateUp()
        assertTrue(actualValue)
        assertTrue(activity.isFinishing)
    }

    @Test
    fun `Given navigationHandlers and no parent activity when onNavigateUpFromChild then delegates to handlers and does not finish`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        val childActivity = mock<Activity>()

        // When
        val actualValue = activity.onNavigateUpFromChild(childActivity)

        // Then
        verify(navigationHandler1).onNavigateUpFromChild(childActivity)
        verify(navigationHandler2).onNavigateUpFromChild(childActivity)
        verify(navigationHandler3).onNavigateUpFromChild(childActivity)
        assertFalse(actualValue)
        assertFalse(activity.isFinishing)
    }

    @Test
    fun `Given interrupting navigationHandlers and parent activity when onNavigateUpFromChild then delegates to handlers and does not finish`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()

        val childActivity = mock<Activity>()
        val navigationHandler2: NavigationHandler = mock {
            on { onNavigateUpFromChild(childActivity) }.thenReturn(true)
        }

        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        Activity::class.java.getDeclaredField("mActivityInfo").apply {
            isAccessible = true
            set(
                activity,
                ActivityInfo().apply {
                    parentActivityName = "com.mitteloupe.solid.activity.TestActivity"
                })
        }

        // When
        val actualValue = activity.onNavigateUpFromChild(childActivity)

        // Then
        verify(navigationHandler1).onNavigateUpFromChild(childActivity)
        verify(navigationHandler2).onNavigateUpFromChild(childActivity)
        verify(navigationHandler3, never()).onNavigateUpFromChild(any())
        assertTrue(actualValue)
        assertFalse(activity.isFinishing)
    }

    @Test
    fun `Given navigationHandlers and parent activity when onNavigateUpFromChild then delegates to handlers and finishes`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        Activity::class.java.getDeclaredField("mActivityInfo").apply {
            isAccessible = true
            set(
                activity,
                ActivityInfo().apply {
                    parentActivityName = "com.mitteloupe.solid.activity.TestActivity"
                })
        }

        val childActivity = mock<Activity>()

        // When
        val actualValue = activity.onNavigateUpFromChild(childActivity)

        // Then
        verify(navigationHandler1).onNavigateUpFromChild(childActivity)
        verify(navigationHandler2).onNavigateUpFromChild(childActivity)
        verify(navigationHandler3).onNavigateUpFromChild(childActivity)
        assertTrue(actualValue)
        assertTrue(activity.isFinishing)
    }

    @Test
    fun `Given navigation handlers when onPrepareNavigateUpTaskStack then delegates to all handlers`() {
        // Given
        val builder = mock<TaskStackBuilder>()
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        activity.onPrepareNavigateUpTaskStack(builder)

        // Then
        verify(navigationHandler1).onPrepareNavigateUpTaskStack(builder)
        verify(navigationHandler2).onPrepareNavigateUpTaskStack(builder)
        verify(navigationHandler3).onPrepareNavigateUpTaskStack(builder)
    }

    @Test
    fun `Given navigation handlers when onCreateNavigateUpTaskStack then delegates to all handlers`() {
        // Given
        val builder = mock<TaskStackBuilder>()
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        activity.onCreateNavigateUpTaskStack(builder)

        // Then
        verify(navigationHandler1).onCreateNavigateUpTaskStack(builder)
        verify(navigationHandler2).onCreateNavigateUpTaskStack(builder)
        verify(navigationHandler3).onCreateNavigateUpTaskStack(builder)
    }

    @Test
    fun `Given navigation handlers with result when onProvideReferrer then delegates to handlers and returns expected value`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()

        val expectedUri = mock<Uri>()
        val navigationHandler2: NavigationHandler = mock {
            on { onProvideReferrer() }.thenReturn(expectedUri)
        }

        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        val actualValue = activity.onProvideReferrer()

        // Then
        verify(navigationHandler1).onProvideReferrer()
        verify(navigationHandler2).onProvideReferrer()
        verify(navigationHandler3, never()).onProvideReferrer()
        assertEquals(expectedUri, actualValue)
    }

    @Test
    fun `Given navigation handlers when onProvideReferrer then delegates to all handlers and returns expected value`() {
        // Given
        val navigationHandler1: NavigationHandler = mock()
        val navigationHandler2: NavigationHandler = mock()
        val navigationHandler3: NavigationHandler = mock()

        val activity = cutController.get()
        activity.testNavigationHandlers.addAll(
            listOf(navigationHandler1, navigationHandler2, navigationHandler3)
        )

        // When
        val actualValue = activity.onProvideReferrer()

        // Then
        verify(navigationHandler1).onProvideReferrer()
        verify(navigationHandler2).onProvideReferrer()
        verify(navigationHandler3).onProvideReferrer()
        assertNull(actualValue)
    }

    @Test
    fun `Given navigation handlers when onNewIntent then delegates to all handlers`() {
        // Given
        val newIntentHandler1: NewIntentHandler = mock()
        val newIntentHandler2: NewIntentHandler = mock()
        val newIntentHandler3: NewIntentHandler = mock()

        val activity = cutController.get()
        activity.testNewIntentHandlers.addAll(
            listOf(newIntentHandler1, newIntentHandler2, newIntentHandler3)
        )

        val intent = mock<Intent>()

        // When
        cutController.newIntent(intent)

        // Then
        verify(newIntentHandler1).onNewIntent(intent)
        verify(newIntentHandler2).onNewIntent(intent)
        verify(newIntentHandler3).onNewIntent(intent)
    }

    @Test
    fun `Given window attachment handlers when activity visible then onAttachedToWindow called`() {
        // Given
        val windowAttachmentHandler1: WindowAttachmentHandler = mock()
        val windowAttachmentHandler2: WindowAttachmentHandler = mock()
        val windowAttachmentHandler3: WindowAttachmentHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()

        cutController.get().testWindowAttachmentHandlers.addAll(
            listOf(windowAttachmentHandler1, windowAttachmentHandler2, windowAttachmentHandler3)
        )

        // When
        cutController.visible()

        // Then
        verify(windowAttachmentHandler1).onAttachedToWindow()
        verify(windowAttachmentHandler2).onAttachedToWindow()
        verify(windowAttachmentHandler3).onAttachedToWindow()
    }

    @Test
    fun `Given window attachment handlers when onDetachedFromWindow then delegates to handlers`() {
        // Given
        val windowAttachmentHandler1: WindowAttachmentHandler = mock()
        val windowAttachmentHandler2: WindowAttachmentHandler = mock()
        val windowAttachmentHandler3: WindowAttachmentHandler = mock()

        cutController.setup()

        val activity = cutController.get()
        activity.testWindowAttachmentHandlers.addAll(
            listOf(windowAttachmentHandler1, windowAttachmentHandler2, windowAttachmentHandler3)
        )

        // When
        activity.onDetachedFromWindow()

        // Then
        verify(windowAttachmentHandler1).onDetachedFromWindow()
        verify(windowAttachmentHandler2).onDetachedFromWindow()
        verify(windowAttachmentHandler3).onDetachedFromWindow()
    }

    @Test
    fun `Given window mode handlers when configuration changes then onConfigurationChanged called`() {
        // Given
        val windowModeHandler1: WindowModeHandler = mock()
        val windowModeHandler2: WindowModeHandler = mock()
        val windowModeHandler3: WindowModeHandler = mock()

        cutController.setup()

        cutController.get().testWindowModeHandlers.addAll(
            listOf(windowModeHandler1, windowModeHandler2, windowModeHandler3)
        )

        val configuration = Configuration()

        // When
        cutController.configurationChange(configuration)

        // Then
        verify(windowModeHandler1).onConfigurationChanged(configuration)
        verify(windowModeHandler2).onConfigurationChanged(configuration)
        verify(windowModeHandler3).onConfigurationChanged(configuration)
    }

    @Test
    fun `Given window mode handlers and single window when onMultiWindowModeChanged then delegates to handlers`() {
        // Given
        val windowModeHandler1: WindowModeHandler = mock()
        val windowModeHandler2: WindowModeHandler = mock()
        val windowModeHandler3: WindowModeHandler = mock()

        cutController.setup()

        val activity = cutController.get()
        activity.testWindowModeHandlers.addAll(
            listOf(windowModeHandler1, windowModeHandler2, windowModeHandler3)
        )

        val isInMultiWindowMode = false
        val configuration = Configuration()

        // When
        activity.onMultiWindowModeChanged(isInMultiWindowMode, configuration)

        // Then
        verify(windowModeHandler1).onMultiWindowModeChanged(isInMultiWindowMode, configuration)
        verify(windowModeHandler2).onMultiWindowModeChanged(isInMultiWindowMode, configuration)
        verify(windowModeHandler3).onMultiWindowModeChanged(isInMultiWindowMode, configuration)
    }

    @Test
    fun `Given window mode handlers and multi-window when onMultiWindowModeChanged then delegates to handlers`() {
        // Given
        val windowModeHandler1: WindowModeHandler = mock()
        val windowModeHandler2: WindowModeHandler = mock()
        val windowModeHandler3: WindowModeHandler = mock()

        cutController.setup()

        val activity = cutController.get()
        activity.testWindowModeHandlers.addAll(
            listOf(windowModeHandler1, windowModeHandler2, windowModeHandler3)
        )

        val isInMultiWindowMode = true
        val configuration = Configuration()

        // When
        activity.onMultiWindowModeChanged(isInMultiWindowMode, configuration)

        // Then
        verify(windowModeHandler1).onMultiWindowModeChanged(isInMultiWindowMode, configuration)
        verify(windowModeHandler2).onMultiWindowModeChanged(isInMultiWindowMode, configuration)
        verify(windowModeHandler3).onMultiWindowModeChanged(isInMultiWindowMode, configuration)
    }

    @Test
    fun `Given window mode handlers and no picture in picture when onPictureInPictureModeChanged then delegates to handlers`() {
        // Given
        val windowModeHandler1: WindowModeHandler = mock()
        val windowModeHandler2: WindowModeHandler = mock()
        val windowModeHandler3: WindowModeHandler = mock()

        cutController.setup()

        val activity = cutController.get()
        activity.testWindowModeHandlers.addAll(
            listOf(windowModeHandler1, windowModeHandler2, windowModeHandler3)
        )

        val isInPictureInPictureMode = false
        val configuration = Configuration()

        // When
        activity.onPictureInPictureModeChanged(isInPictureInPictureMode, configuration)

        // Then
        verify(windowModeHandler1).onPictureInPictureModeChanged(
            isInPictureInPictureMode,
            configuration
        )
        verify(windowModeHandler2).onPictureInPictureModeChanged(
            isInPictureInPictureMode,
            configuration
        )
        verify(windowModeHandler3).onPictureInPictureModeChanged(
            isInPictureInPictureMode,
            configuration
        )
    }

    @Test
    fun `Given window mode handlers and picture in picture when onPictureInPictureModeChanged then delegates to handlers`() {
        // Given
        val windowModeHandler1: WindowModeHandler = mock()
        val windowModeHandler2: WindowModeHandler = mock()
        val windowModeHandler3: WindowModeHandler = mock()

        cutController.setup()

        val activity = cutController.get()
        activity.testWindowModeHandlers.addAll(
            listOf(windowModeHandler1, windowModeHandler2, windowModeHandler3)
        )

        val isInPictureInPictureMode = true
        val configuration = Configuration()

        // When
        activity.onPictureInPictureModeChanged(isInPictureInPictureMode, configuration)

        // Then
        verify(windowModeHandler1).onPictureInPictureModeChanged(
            isInPictureInPictureMode,
            configuration
        )
        verify(windowModeHandler2).onPictureInPictureModeChanged(
            isInPictureInPictureMode,
            configuration
        )
        verify(windowModeHandler3).onPictureInPictureModeChanged(
            isInPictureInPictureMode,
            configuration
        )
    }

    @Test
    fun `Given window mode handlers when activity created then onStart called`() {
        // Given
        val windowModeHandler1: WindowModeHandler = mock()
        val windowModeHandler2: WindowModeHandler = mock()
        val windowModeHandler3: WindowModeHandler = mock()

        val activity = cutController.get()
        activity.testWindowModeHandlers.addAll(
            listOf(windowModeHandler1, windowModeHandler2, windowModeHandler3)
        )

        val layoutParams = WindowManager.LayoutParams(0, 0)

        // When
        activity.onWindowAttributesChanged(layoutParams)

        // Then
        verify(windowModeHandler1).onWindowAttributesChanged(layoutParams)
        verify(windowModeHandler2).onWindowAttributesChanged(layoutParams)
        verify(windowModeHandler3).onWindowAttributesChanged(layoutParams)
    }

    @Test
    fun `Given window focus handlers and no focus when activity created then onWindowFocusChanged called`() {
        // Given
        val windowFocusHandler1: WindowFocusHandler = mock()
        val windowFocusHandler2: WindowFocusHandler = mock()
        val windowFocusHandler3: WindowFocusHandler = mock()

        cutController.setup()

        val activity = cutController.get()

        activity.testWindowFocusHandlers.addAll(
            listOf(windowFocusHandler1, windowFocusHandler2, windowFocusHandler3)
        )

        val hasFocus = false

        // When
        cutController.windowFocusChanged(hasFocus)

        // Then
        verify(windowFocusHandler1).onWindowFocusChanged(hasFocus)
        verify(windowFocusHandler2).onWindowFocusChanged(hasFocus)
        verify(windowFocusHandler3).onWindowFocusChanged(hasFocus)
    }

    @Test
    fun `Given window focus handlers and focus when activity created then onWindowFocusChanged called`() {
        // Given
        val windowFocusHandler1: WindowFocusHandler = mock()
        val windowFocusHandler2: WindowFocusHandler = mock()
        val windowFocusHandler3: WindowFocusHandler = mock()

        cutController.setup()

        val activity = cutController.get()
        activity.testWindowFocusHandlers.addAll(
            listOf(windowFocusHandler1, windowFocusHandler2, windowFocusHandler3)
        )

        val hasFocus = true

        // When
        cutController.windowFocusChanged(hasFocus)

        // Then
        verify(windowFocusHandler1).onWindowFocusChanged(hasFocus)
        verify(windowFocusHandler2).onWindowFocusChanged(hasFocus)
        verify(windowFocusHandler3).onWindowFocusChanged(hasFocus)
    }

    @Test
    fun `Given context menu handlers when onCreateContextMenu then delegates to handlers`() {
        // Given
        val contextMenuHandler1: ContextMenuHandler = mock()
        val contextMenuHandler2: ContextMenuHandler = mock()
        val contextMenuHandler3: ContextMenuHandler = mock()

        val activity = cutController.get()
        activity.testContextMenuHandlers.addAll(
            listOf(contextMenuHandler1, contextMenuHandler2, contextMenuHandler3)
        )

        val menu = mock<ContextMenu>()
        val view = mock<View>()
        val menuInfo = mock<ContextMenuInfo>()

        // When
        activity.onCreateContextMenu(menu, view, menuInfo)

        // Then
        verify(contextMenuHandler1).onCreateContextMenu(menu, view, menuInfo)
        verify(contextMenuHandler2).onCreateContextMenu(menu, view, menuInfo)
        verify(contextMenuHandler3).onCreateContextMenu(menu, view, menuInfo)
    }

    @Test
    fun `Given context menu handlers when onContextItemSelected then delegates to handlers`() {
        // Given
        val contextMenuHandler1: ContextMenuHandler = mock()
        val contextMenuHandler2: ContextMenuHandler = mock()
        val contextMenuHandler3: ContextMenuHandler = mock()

        val activity = cutController.get()
        activity.testContextMenuHandlers.addAll(
            listOf(contextMenuHandler1, contextMenuHandler2, contextMenuHandler3)
        )

        val item = mock<MenuItem>()

        // When
        activity.onContextItemSelected(item)

        // Then
        verify(contextMenuHandler1).onContextItemSelected(item)
        verify(contextMenuHandler2).onContextItemSelected(item)
        verify(contextMenuHandler3).onContextItemSelected(item)
    }

    @Test
    fun `Given context menu handlers when onContextMenuClosed then delegates to handlers`() {
        // Given
        val contextMenuHandler1: ContextMenuHandler = mock()
        val contextMenuHandler2: ContextMenuHandler = mock()
        val contextMenuHandler3: ContextMenuHandler = mock()

        val activity = cutController.get()
        activity.testContextMenuHandlers.addAll(
            listOf(contextMenuHandler1, contextMenuHandler2, contextMenuHandler3)
        )

        val menu = mock<Menu>()

        // When
        activity.onContextMenuClosed(menu)

        // Then
        verify(contextMenuHandler1).onContextMenuClosed(menu)
        verify(contextMenuHandler2).onContextMenuClosed(menu)
        verify(contextMenuHandler3).onContextMenuClosed(menu)
    }

    @Test
    fun `Given options menu handlers when onPrepareOptionsMenu then delegates to handlers until intercepted`() {
        // Given
        val menu = mock<Menu>()
        val optionsMenuHandler1: OptionsMenuHandler = mock {
            on { onPrepareOptionsMenu(menu) }.thenReturn(true)
        }
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()

        val activity = cutController.get()
        activity.testOptionsMenuHandlers.addAll(
            listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
        )

        // When
        activity.onPrepareOptionsMenu(menu)

        // Then
        verify(optionsMenuHandler1).onPrepareOptionsMenu(menu)
        verify(optionsMenuHandler2).onPrepareOptionsMenu(menu)
        verify(optionsMenuHandler3, never()).onPrepareOptionsMenu(menu)
    }

    @Test
    fun `Given options menu handlers when onCreateOptionsMenu then delegates to handlers until intercepted`() {
        // Given
        val menu = mock<Menu>()
        val optionsMenuHandler1: OptionsMenuHandler = mock {
            on { onCreateOptionsMenu(menu) }.thenReturn(true)
        }
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()

        val activity = cutController.get()
        activity.testOptionsMenuHandlers.addAll(
            listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
        )

        // When
        activity.onCreateOptionsMenu(menu)

        // Then
        verify(optionsMenuHandler1).onCreateOptionsMenu(menu)
        verify(optionsMenuHandler2).onCreateOptionsMenu(menu)
        verify(optionsMenuHandler3, never()).onCreateOptionsMenu(menu)
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

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()

        val activity = cutController.get()
        activity.testOptionsMenuHandlers.addAll(
            listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
        )

        // When
        activity.onOptionsItemSelected(item)

        // Then
        verify(optionsMenuHandler1).onOptionsItemSelected(item)
        verify(optionsMenuHandler2).onOptionsItemSelected(item)
        verify(optionsMenuHandler3, never()).onOptionsItemSelected(item)
    }

    @Test
    fun `Given options menu handlers when onOptionsMenuClosed then delegates to all handlers`() {
        // Given
        val optionsMenuHandler1: OptionsMenuHandler = mock()
        val optionsMenuHandler2: OptionsMenuHandler = mock()
        val optionsMenuHandler3: OptionsMenuHandler = mock()

        cutController
            .create()
            .start()
            .postCreate(null)
            .resume()

        val activity = cutController.get()
        activity.testOptionsMenuHandlers.addAll(
            listOf(optionsMenuHandler1, optionsMenuHandler2, optionsMenuHandler3)
        )

        val menu = mock<Menu>()

        // When
        activity.onOptionsMenuClosed(menu)

        // Then
        verify(optionsMenuHandler1).onOptionsMenuClosed(menu)
        verify(optionsMenuHandler2).onOptionsMenuClosed(menu)
        verify(optionsMenuHandler3).onOptionsMenuClosed(menu)
    }

    @Test
    fun `Given menu opened handlers when onMenuOpened then delegates to handlers until intercepted`() {
        // Given
        val featureId = 321
        val menu = mock<Menu>()
        val menuOpenedHandler1: MenuOpenedHandler = mock {
            on { onMenuOpened(featureId, menu) }.thenReturn(true)
        }

        val menuOpenedHandler2: MenuOpenedHandler = mock()
        val menuOpenedHandler3: MenuOpenedHandler = mock()

        val activity = cutController.get()
        activity.testMenuOpenedHandlers.addAll(
            listOf(menuOpenedHandler1, menuOpenedHandler2, menuOpenedHandler3)
        )

        // When
        val actualValue = activity.onMenuOpened(featureId, menu)

        // Then
        verify(menuOpenedHandler1).onMenuOpened(featureId, menu)
        verify(menuOpenedHandler2).onMenuOpened(featureId, menu)
        verify(menuOpenedHandler3, never()).onMenuOpened(featureId, menu)
        assertFalse(actualValue)
    }

    @Test
    fun `Given action mode handlers when onWindowStartingActionMode then returns expected ActionMode`() {
        // Given
        val actionModeHandler1: ActionModeHandler = mock()

        val callback = mock<ActionMode.Callback>()
        val expected = mock<ActionMode>()
        val actionModeHandler2: ActionModeHandler = mock {
            on { onWindowStartingActionMode(callback) }.thenReturn(expected)
        }

        val actionModeHandler3: ActionModeHandler = mock()

        val activity = cutController.get()
        activity.testActionModeHandlers.addAll(
            listOf(actionModeHandler1, actionModeHandler2, actionModeHandler3)
        )

        // When
        val actualValue = activity.onWindowStartingActionMode(callback)

        // Then
        assertEquals(expected, actualValue)
        verify(actionModeHandler1).onWindowStartingActionMode(callback)
        verify(actionModeHandler3, never()).onWindowStartingActionMode(any())
    }

    @Test
    fun `Given action mode handlers and type when onWindowStartingActionMode then returns expected ActionMode`() {
        // Given
        val actionModeHandler1: ActionModeHandler = mock()

        val callback = mock<ActionMode.Callback>()
        val type = 1337
        val expected = mock<ActionMode>()
        val actionModeHandler2: ActionModeHandler = mock {
            on { onWindowStartingActionMode(callback, type) }.thenReturn(expected)
        }

        val actionModeHandler3: ActionModeHandler = mock()

        val activity = cutController.get()
        activity.testActionModeHandlers.addAll(
            listOf(actionModeHandler1, actionModeHandler2, actionModeHandler3)
        )

        // When
        val actualValue = activity.onWindowStartingActionMode(callback, type)

        // Then
        assertEquals(expected, actualValue)
        verify(actionModeHandler1).onWindowStartingActionMode(callback, type)
        verify(actionModeHandler3, never()).onWindowStartingActionMode(any(), any())
    }

    @Test
    fun `Given action mode handlers when onActionModeStarted then delegates to all handlers`() {
        // Given
        val actionModeHandler1: ActionModeHandler = mock()
        val actionModeHandler2: ActionModeHandler = mock()
        val actionModeHandler3: ActionModeHandler = mock()

        val activity = cutController.get()
        activity.testActionModeHandlers.addAll(
            listOf(actionModeHandler1, actionModeHandler2, actionModeHandler3)
        )

        val mode = mock<ActionMode>()

        // When
        activity.onActionModeStarted(mode)

        // Then
        verify(actionModeHandler1).onActionModeStarted(mode)
        verify(actionModeHandler2).onActionModeStarted(mode)
        verify(actionModeHandler3).onActionModeStarted(mode)
    }

    @Test
    fun `Given action mode handlers when onActionModeFinished then delegates to all handlers`() {
        // Given
        val actionModeHandler1: ActionModeHandler = mock()
        val actionModeHandler2: ActionModeHandler = mock()
        val actionModeHandler3: ActionModeHandler = mock()

        val activity = cutController.get()
        activity.testActionModeHandlers.addAll(
            listOf(actionModeHandler1, actionModeHandler2, actionModeHandler3)
        )

        val mode = mock<ActionMode>()

        // When
        activity.onActionModeFinished(mode)

        // Then
        verify(actionModeHandler1).onActionModeFinished(mode)
        verify(actionModeHandler2).onActionModeFinished(mode)
        verify(actionModeHandler3).onActionModeFinished(mode)
    }

    @Test
    fun `Given activity for result callback handlers when receive result then delegates to all handlers`() {
        // Given
        val activityForResultCallbackHandler1: ActivityForResultCallbackHandler = mock()
        val activityForResultCallbackHandler2: ActivityForResultCallbackHandler = mock()
        val activityForResultCallbackHandler3: ActivityForResultCallbackHandler = mock()

        val activity = cutController.get()
        activity.testActivityForResultCallbackHandlers.addAll(
            listOf(
                activityForResultCallbackHandler1,
                activityForResultCallbackHandler2,
                activityForResultCallbackHandler3
            )
        )

        val requestCode = 17
        val resultCode = 7
        val resultIntent = mock<Intent>()

        // When
        shadowOf(activity).callOnActivityResult(requestCode, resultCode, resultIntent)

        // Then
        verify(activityForResultCallbackHandler1)
            .onActivityResult(requestCode, resultCode, resultIntent)
        verify(activityForResultCallbackHandler2)
            .onActivityResult(requestCode, resultCode, resultIntent)
        verify(activityForResultCallbackHandler3)
            .onActivityResult(requestCode, resultCode, resultIntent)
    }

    @Test
    fun `Given activity for result callback handlers when onActivityReenter then delegates to all handlers`() {
        // Given
        val activityForResultCallbackHandler1: ActivityForResultCallbackHandler = mock()
        val activityForResultCallbackHandler2: ActivityForResultCallbackHandler = mock()
        val activityForResultCallbackHandler3: ActivityForResultCallbackHandler = mock()

        val activity = cutController.get()
        activity.testActivityForResultCallbackHandlers.addAll(
            listOf(
                activityForResultCallbackHandler1,
                activityForResultCallbackHandler2,
                activityForResultCallbackHandler3
            )
        )

        val resultCode = 7
        val data = mock<Intent>()

        // When
        activity.onActivityReenter(resultCode, data)

        // Then
        verify(activityForResultCallbackHandler1).onActivityReenter(resultCode, data)
        verify(activityForResultCallbackHandler2).onActivityReenter(resultCode, data)
        verify(activityForResultCallbackHandler3).onActivityReenter(resultCode, data)
    }

    @Test
    fun `Given permission handlers when receive permission result then delegates to all handlers`() {
        // Given
        val permissionHandler1: PermissionHandler = mock()
        val permissionHandler2: PermissionHandler = mock()
        val permissionHandler3: PermissionHandler = mock()

        val activity = cutController.get()
        activity.testPermissionHandlers.addAll(
            listOf(permissionHandler1, permissionHandler2, permissionHandler3)
        )

        val requestCode = 3
        val permissions = arrayOf("Permission1", "Permission2")
        val grantResults = intArrayOf(1, 1)

        // When
        activity.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Then
        verify(permissionHandler1)
            .onRequestPermissionsResult(requestCode, permissions, grantResults)
        verify(permissionHandler2)
            .onRequestPermissionsResult(requestCode, permissions, grantResults)
        verify(permissionHandler3)
            .onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}

class TestChildActivity : TestActivity()

open class TestActivity : SolidActivity() {
    val createdViews = mutableListOf<View?>()

    val testLifecycleHandlers = mutableListOf<LifecycleHandler>()
    override val lifecycleHandlers: List<LifecycleHandler>
        get() = testLifecycleHandlers

    val testViewCreationHandlers = mutableListOf<ViewCreationHandler>()
    override val viewCreationHandlers: List<ViewCreationHandler>
        get() = testViewCreationHandlers

    val testTitleHandlers = mutableListOf<TitleHandler>()
    override val titleHandlers: List<TitleHandler>
        get() = testTitleHandlers

    val testInstanceStateHandlers = mutableListOf<InstanceStateHandler>()
    override val instanceStateHandlers: List<InstanceStateHandler>
        get() = testInstanceStateHandlers

    val testAnimationHandlers = mutableListOf<AnimationHandler>()
    override val animationHandlers: List<AnimationHandler>
        get() = testAnimationHandlers

    val testKeyHandlers = mutableListOf<KeyHandler>()
    override val keyHandlers: List<KeyHandler>
        get() = testKeyHandlers

    val testTouchHandlers = mutableListOf<TouchHandler>()
    override val touchHandlers: List<TouchHandler>
        get() = testTouchHandlers

    val testGenericMotionHandlers = mutableListOf<GenericMotionHandler>()
    override val genericMotionHandlers: List<GenericMotionHandler>
        get() = testGenericMotionHandlers

    val testNavigationHandlers = mutableListOf<NavigationHandler>()
    override val navigationHandlers: List<NavigationHandler>
        get() = testNavigationHandlers

    val testNewIntentHandlers = mutableListOf<NewIntentHandler>()
    override val newIntentHandlers: List<NewIntentHandler>
        get() = testNewIntentHandlers

    val testWindowAttachmentHandlers = mutableListOf<WindowAttachmentHandler>()
    override val windowAttachmentHandlers: List<WindowAttachmentHandler>
        get() = testWindowAttachmentHandlers

    val testWindowModeHandlers = mutableListOf<WindowModeHandler>()
    override val windowModeHandlers: List<WindowModeHandler>
        get() = testWindowModeHandlers

    val testWindowFocusHandlers = mutableListOf<WindowFocusHandler>()
    override val windowFocusHandlers: List<WindowFocusHandler>
        get() = testWindowFocusHandlers

    val testContextMenuHandlers = mutableListOf<ContextMenuHandler>()
    override val contextMenuHandlers: List<ContextMenuHandler>
        get() = testContextMenuHandlers

    val testOptionsMenuHandlers = mutableListOf<OptionsMenuHandler>()
    override val optionsMenuHandlers: List<OptionsMenuHandler>
        get() = testOptionsMenuHandlers

    val testMenuOpenedHandlers = mutableListOf<MenuOpenedHandler>()
    override val menuOpenedHandlers: List<MenuOpenedHandler>
        get() = testMenuOpenedHandlers

    val testActionModeHandlers = mutableListOf<ActionModeHandler>()
    override val actionModeHandlers: List<ActionModeHandler>
        get() = testActionModeHandlers

    val testActivityForResultCallbackHandlers = mutableListOf<ActivityForResultCallbackHandler>()
    override val activityForResultCallbackHandlers: List<ActivityForResultCallbackHandler>
        get() = testActivityForResultCallbackHandlers

    val testPermissionHandlers = mutableListOf<PermissionHandler>()
    override val permissionHandlers: List<PermissionHandler>
        get() = testPermissionHandlers

    val testMemoryHandlers = mutableListOf<MemoryHandler>()
    override val memoryHandlers: List<MemoryHandler>
        get() = testMemoryHandlers

    val testSearchHandlers = mutableListOf<SearchHandler>()
    override val searchHandlers: List<SearchHandler>
        get() = testSearchHandlers

    val testLocalVoiceInteractionHandlers = mutableListOf<LocalVoiceInteractionHandler>()
    override val localVoiceInteractionHandlers: List<LocalVoiceInteractionHandler>
        get() = testLocalVoiceInteractionHandlers

    val testMetaDataHandlers = mutableListOf<MetaDataHandler>()
    override val metaDataHandlers: List<MetaDataHandler>
        get() = testMetaDataHandlers

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View? {
        val view = super.onCreateView(parent, name, context, attrs)
        createdViews.add(view)
        return view
    }
}
