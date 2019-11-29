package com.mitteloupe.solid.activity

import android.app.Activity
import android.app.TaskStackBuilder
import android.app.assist.AssistContent
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.ActionMode
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.KeyEvent
import android.view.KeyboardShortcutGroup
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.SearchEvent
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
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

abstract class SolidActivity : AppCompatActivity() {
    open val lifecycleHandlers: List<LifecycleHandler> = emptyList()

    open val viewCreationHandlers: List<ViewCreationHandler> = emptyList()

    open val titleHandlers: List<TitleHandler> = emptyList()

    open val instanceStateHandlers: List<InstanceStateHandler> = emptyList()

    open val animationHandlers: List<AnimationHandler> = emptyList()

    open val keyHandlers: List<KeyHandler> = emptyList()

    open val touchHandlers: List<TouchHandler> = emptyList()

    open val genericMotionHandlers: List<GenericMotionHandler> = emptyList()

    open val navigationHandlers: List<NavigationHandler> = emptyList()

    open val newIntentHandlers: List<NewIntentHandler> = emptyList()

    open val windowAttachmentHandlers: List<WindowAttachmentHandler> = emptyList()

    open val windowModeHandlers: List<WindowModeHandler> = emptyList()

    open val windowFocusHandlers: List<WindowFocusHandler> = emptyList()

    open val contextMenuHandlers: List<ContextMenuHandler> = emptyList()

    open val optionsMenuHandlers: List<OptionsMenuHandler> = emptyList()

    open val menuOpenedHandlers: List<MenuOpenedHandler> = emptyList()

    open val actionModeHandlers: List<ActionModeHandler> = emptyList()

    open val activityForResultCallbackHandlers: List<ActivityForResultCallbackHandler> = emptyList()

    open val permissionHandlers: List<PermissionHandler> = emptyList()

    open val memoryHandlers: List<MemoryHandler> = emptyList()

    open val searchHandlers: List<SearchHandler> = emptyList()

    open val localVoiceInteractionHandlers: List<LocalVoiceInteractionHandler> = emptyList()

    open val metaDataHandlers: List<MetaDataHandler> = emptyList()

    // region Lifecycle event functions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleHandlers.forEach { handler ->
            handler.onCreate(savedInstanceState)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        lifecycleHandlers.forEach { handler ->
            handler.onPostCreate(savedInstanceState)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        lifecycleHandlers.forEach { handler ->
            handler.onCreate(savedInstanceState, persistentState)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)

        lifecycleHandlers.forEach { handler ->
            handler.onPostCreate(savedInstanceState, persistentState)
        }
    }

    override fun onRestart() {
        super.onRestart()

        lifecycleHandlers.forEach { handler ->
            handler.onRestart()
        }
    }

    override fun onStart() {
        super.onStart()

        lifecycleHandlers.forEach { handler ->
            handler.onStart()
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleHandlers.forEach { handler ->
            handler.onResume()
        }
    }

    override fun onPostResume() {
        super.onPostResume()

        lifecycleHandlers.forEach { handler ->
            handler.onPostResume()
        }
    }

    override fun onUserLeaveHint() {
        lifecycleHandlers.forEach { handler ->
            handler.onUserLeaveHint()
        }

        super.onUserLeaveHint()
    }

    override fun onPause() {
        lifecycleHandlers.forEach { handler ->
            handler.onPause()
        }

        super.onPause()
    }

    override fun onStop() {
        lifecycleHandlers.forEach { handler ->
            handler.onStop()
        }

        super.onStop()
    }

    override fun onDestroy() {
        lifecycleHandlers.forEach { handler ->
            handler.onDestroy()
        }

        super.onDestroy()
    }

    // endregion Lifecycle event functions

    // region View creation event functions

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet) =
        viewCreationHandlers
            .firstResultOrNull { handler -> handler.onCreateView(name, context, attrs) }
            ?: super.onCreateView(name, context, attrs)

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ) = viewCreationHandlers
        .firstResultOrNull { handler -> handler.onCreateView(parent, name, context, attrs) }
        ?: super.onCreateView(parent, name, context, attrs)

    override fun onContentChanged() {
        super.onContentChanged()

        viewCreationHandlers.forEach { handler ->
            handler.onContentChanged()
        }
    }

    // endregion View creation event functions

    // region Title event functions

    override fun onTitleChanged(title: CharSequence, color: Int) {
        super.onTitleChanged(title, color)

        titleHandlers.forEach { handler ->
            handler.onTitleChanged(title, color)
        }
    }

    override fun onChildTitleChanged(childActivity: Activity, title: CharSequence) {
        super.onChildTitleChanged(childActivity, title)

        titleHandlers.forEach { handler ->
            handler.onChildTitleChanged(childActivity, title)
        }
    }

    // endregion Title event functions

    // region Instance State event functions

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        instanceStateHandlers.forEach { handler ->
            handler.onSaveInstanceState(outState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)

        instanceStateHandlers.forEach { handler ->
            handler.onSaveInstanceState(outState, outPersistentState)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        instanceStateHandlers.forEach { handler ->
            handler.onRestoreInstanceState(savedInstanceState)
        }

        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onRestoreInstanceState(
        savedInstanceState: Bundle,
        persistentState: PersistableBundle
    ) {
        instanceStateHandlers.forEach { handler ->
            handler.onRestoreInstanceState(savedInstanceState, persistentState)
        }

        super.onRestoreInstanceState(savedInstanceState, persistentState)
    }

    // endregion Instance State event functions

    // region Animation event function

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

        animationHandlers.forEach { handler ->
            handler.onEnterAnimationComplete()
        }
    }

    // endregion Animation event function

    // region Key event functions

    override fun onKeyDown(keyCode: Int, event: KeyEvent) =
        keyHandlers.any { handler ->
            handler.onKeyDown(keyCode, event)
        } || super.onKeyDown(keyCode, event)

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent) =
        keyHandlers.any { handler ->
            handler.onKeyLongPress(keyCode, event)
        } || super.onKeyLongPress(keyCode, event)

    override fun onKeyShortcut(keyCode: Int, event: KeyEvent) =
        keyHandlers.any { handler ->
            handler.onKeyShortcut(keyCode, event)
        } || super.onKeyShortcut(keyCode, event)

    override fun onKeyMultiple(keyCode: Int, repeatCount: Int, event: KeyEvent) =
        keyHandlers.any { handler ->
            handler.onKeyMultiple(keyCode, repeatCount, event)
        } || super.onKeyMultiple(keyCode, repeatCount, event)

    override fun onKeyUp(keyCode: Int, event: KeyEvent) =
        keyHandlers.any { handler ->
            handler.onKeyUp(keyCode, event)
        } || super.onKeyUp(keyCode, event)

    override fun onProvideKeyboardShortcuts(
        data: MutableList<KeyboardShortcutGroup>,
        menu: Menu?,
        deviceId: Int
    ) {
        super.onProvideKeyboardShortcuts(data, menu, deviceId)

        keyHandlers.forEach { handler ->
            handler.onProvideKeyboardShortcuts(data, menu, deviceId)
        }
    }

    // endregion Key event functions

    // region Touch event function

    override fun onTouchEvent(event: MotionEvent) =
        touchHandlers.any { handler ->
            handler.onTouchEvent(event)
        } || super.onTouchEvent(event)

    // endregion Touch event function

    // region Generic Motion event functions

    override fun onUserInteraction() {
        genericMotionHandlers.forEach { handler ->
            handler.onUserInteraction()
        }

        super.onUserInteraction()
    }

    override fun onTrackballEvent(event: MotionEvent) =
        genericMotionHandlers.any { handler ->
            handler.onTrackballEvent(event)
        } || super.onTrackballEvent(event)

    override fun onGenericMotionEvent(event: MotionEvent) =
        genericMotionHandlers.any { handler ->
            handler.onGenericMotionEvent(event)
        } || super.onGenericMotionEvent(event)

    // endregion Generic Motion event functions

    // region Navigation event functions

    override fun onBackPressed() {
        if (navigationHandlers.none { handler -> handler.onBackPressed() }) {
            super.onBackPressed()
        }
    }

    override fun onNavigateUp() =
        navigationHandlers.any { handler ->
            handler.onNavigateUp()
        } || super.onNavigateUp()

    override fun onNavigateUpFromChild(child: Activity) =
        navigationHandlers.any { handler ->
            handler.onNavigateUpFromChild(child)
        } || super.onNavigateUpFromChild(child)

    override fun onPrepareNavigateUpTaskStack(builder: TaskStackBuilder) {
        super.onPrepareNavigateUpTaskStack(builder)

        navigationHandlers.forEach { handler ->
            handler.onPrepareNavigateUpTaskStack(builder)
        }
    }

    override fun onCreateNavigateUpTaskStack(builder: TaskStackBuilder) {
        super.onCreateNavigateUpTaskStack(builder)

        navigationHandlers.forEach { handler ->
            handler.onCreateNavigateUpTaskStack(builder)
        }
    }

    override fun onProvideReferrer(): Uri? =
        navigationHandlers
            .firstResultOrNull { handler -> handler.onProvideReferrer() }
            ?: super.onProvideReferrer()

    // endregion Navigation event functions

    // region New Intent event function

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        newIntentHandlers.forEach { handler ->
            handler.onNewIntent(intent)
        }
    }

    // endregion New Intent event function

    // region Window attachment event functions

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        windowAttachmentHandlers.forEach { handler ->
            handler.onAttachedToWindow()
        }
    }

    override fun onDetachedFromWindow() {
        windowAttachmentHandlers.forEach { handler ->
            handler.onDetachedFromWindow()
        }

        super.onDetachedFromWindow()
    }

    // endregion Window attachment event functions

    // region Window Mode event functions

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        windowModeHandlers.forEach { handler ->
            handler.onConfigurationChanged(newConfig)
        }
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean, newConfig: Configuration) {
        super.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)

        windowModeHandlers.forEach { handler ->
            handler.onMultiWindowModeChanged(isInMultiWindowMode, newConfig)
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        windowModeHandlers.forEach { handler ->
            handler.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        }
    }

    override fun onWindowAttributesChanged(params: WindowManager.LayoutParams) {
        super.onWindowAttributesChanged(params)

        windowModeHandlers.forEach { handler ->
            handler.onWindowAttributesChanged(params)
        }
    }

    // endregion Window Mode event functions

    // region Window Focus event function

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        windowFocusHandlers.forEach { handler ->
            handler.onWindowFocusChanged(hasFocus)
        }
    }

    // endregion Window Focus event function

    // region Context Menu event functions

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)

        contextMenuHandlers.forEach { handler ->
            handler.onCreateContextMenu(menu, v, menuInfo)
        }
    }

    override fun onContextItemSelected(item: MenuItem) =
        contextMenuHandlers.any { handler ->
            handler.onContextItemSelected(item)
        } || super.onContextItemSelected(item)

    override fun onContextMenuClosed(menu: Menu) {
        contextMenuHandlers.forEach { handler ->
            handler.onContextMenuClosed(menu)
        }

        super.onContextMenuClosed(menu)
    }

    // endregion Context Menu event functions

    // region Options Menu event functions

    override fun onPrepareOptionsMenu(menu: Menu) =
        optionsMenuHandlers.all { handler ->
            handler.onPrepareOptionsMenu(menu)
        } && super.onPrepareOptionsMenu(menu)

    override fun onCreateOptionsMenu(menu: Menu) =
        optionsMenuHandlers.all { handler ->
            handler.onCreateOptionsMenu(menu)
        } && super.onCreateOptionsMenu(menu)

    override fun onOptionsItemSelected(item: MenuItem) =
        optionsMenuHandlers.any { handler ->
            handler.onOptionsItemSelected(item)
        } || super.onOptionsItemSelected(item)

    override fun onOptionsMenuClosed(menu: Menu) {
        optionsMenuHandlers.forEach { handler ->
            handler.onOptionsMenuClosed(menu)
        }
        super.onOptionsMenuClosed(menu)
    }

    // endregion Options Menu event functions

    // region Menu Opened event function

    override fun onMenuOpened(featureId: Int, menu: Menu) =
        menuOpenedHandlers.all { handler ->
            handler.onMenuOpened(featureId, menu)
        } && super.onMenuOpened(featureId, menu)

    // endregion Menu Opened event function

    // region Action Mode event functions

    override fun onWindowStartingActionMode(callback: ActionMode.Callback): ActionMode? =
        actionModeHandlers
            .firstResultOrNull { handler -> handler.onWindowStartingActionMode(callback) }
            ?: super.onWindowStartingActionMode(callback)

    override fun onWindowStartingActionMode(callback: ActionMode.Callback, type: Int): ActionMode? =
        actionModeHandlers
            .firstResultOrNull { handler -> handler.onWindowStartingActionMode(callback, type) }
            ?: super.onWindowStartingActionMode(callback)

    override fun onActionModeStarted(mode: ActionMode?) {
        super.onActionModeStarted(mode)

        actionModeHandlers.forEach { handler ->
            handler.onActionModeStarted(mode)
        }
    }

    override fun onActionModeFinished(mode: ActionMode?) {
        actionModeHandlers.forEach { handler ->
            handler.onActionModeFinished(mode)
        }

        super.onActionModeFinished(mode)
    }

    // endregion Action Mode event functions

    // region Activity For Result callback functions

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        activityForResultCallbackHandlers.forEach { handler ->
            handler.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onActivityReenter(resultCode: Int, data: Intent) {
        super.onActivityReenter(resultCode, data)

        activityForResultCallbackHandlers.forEach { handler ->
            handler.onActivityReenter(resultCode, data)
        }
    }

    // endregion Activity For Result callback functions

    // region Permission event function

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        permissionHandlers.forEach { handler ->
            handler.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    // endregion Permission event function

    // region Memory event functions

    override fun onLowMemory() {
        super.onLowMemory()

        memoryHandlers.forEach { handler ->
            handler.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)

        memoryHandlers.forEach { handler ->
            handler.onTrimMemory(level)
        }
    }

    // endregion Memory event functions

    // region Search event functions

    override fun onSearchRequested(searchEvent: SearchEvent?) =
        searchHandlers.any { handler ->
            handler.onSearchRequested(searchEvent)
        } || super.onSearchRequested(searchEvent)

    override fun onSearchRequested() =
        searchHandlers.any { handler ->
            handler.onSearchRequested()
        } || super.onSearchRequested()

    // endregion Search event functions

    // region Local Voice Interaction event functions

    override fun onLocalVoiceInteractionStarted() {
        super.onLocalVoiceInteractionStarted()

        localVoiceInteractionHandlers.forEach { handler ->
            handler.onLocalVoiceInteractionStarted()
        }
    }

    override fun onLocalVoiceInteractionStopped() {
        localVoiceInteractionHandlers.forEach { handler ->
            handler.onLocalVoiceInteractionStopped()
        }

        super.onLocalVoiceInteractionStopped()
    }

    // endregion Local Voice Interaction event functions

    // region MetaData event functions

    override fun onProvideAssistContent(outContent: AssistContent) {
        super.onProvideAssistContent(outContent)

        metaDataHandlers.forEach { handler ->
            handler.onProvideAssistContent(outContent)
        }
    }

    override fun onProvideAssistData(data: Bundle) {
        super.onProvideAssistData(data)

        metaDataHandlers.forEach { handler ->
            handler.onProvideAssistData(data)
        }
    }

    override fun onCreateDescription(): CharSequence? =
        metaDataHandlers.firstResultOrNull { handler ->
            handler.onCreateDescription()
        } ?: super.onCreateDescription()

    // endregion MetaData event functions
}

private fun <HANDLER, RETURN_VALUE> List<HANDLER>.firstResultOrNull(
    mappingFunction: (HANDLER) -> RETURN_VALUE
) = asSequence()
    .map(mappingFunction)
    .firstOrNull { value -> value != null }
