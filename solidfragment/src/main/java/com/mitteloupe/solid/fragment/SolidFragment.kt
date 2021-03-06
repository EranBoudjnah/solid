package com.mitteloupe.solid.fragment

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.AttributeSet
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mitteloupe.solid.fragment.handler.ActivityForResultCallbackHandler
import com.mitteloupe.solid.fragment.handler.AnimationHandler
import com.mitteloupe.solid.fragment.handler.ChildFragmentHandler
import com.mitteloupe.solid.fragment.handler.ContextMenuHandler
import com.mitteloupe.solid.fragment.handler.InflationHandler
import com.mitteloupe.solid.fragment.handler.InstanceStateHandler
import com.mitteloupe.solid.fragment.handler.LayoutInflaterHandler
import com.mitteloupe.solid.fragment.handler.LifecycleHandler
import com.mitteloupe.solid.fragment.handler.MemoryHandler
import com.mitteloupe.solid.fragment.handler.NavigationHandler
import com.mitteloupe.solid.fragment.handler.OptionsMenuHandler
import com.mitteloupe.solid.fragment.handler.PermissionHandler
import com.mitteloupe.solid.fragment.handler.VisibilityHandler
import com.mitteloupe.solid.fragment.handler.WindowModeHandler

abstract class SolidFragment : Fragment() {
    open val lifecycleHandlers: List<LifecycleHandler> = emptyList()

    open val instanceStateHandlers: List<InstanceStateHandler> = emptyList()

    open val visibilityHandlers: List<VisibilityHandler> = emptyList()

    open val contextMenuHandlers: List<ContextMenuHandler> = emptyList()

    open val optionsMenuHandlers: List<OptionsMenuHandler> = emptyList()

    open val inflationHandlers: List<InflationHandler> = emptyList()

    open val animationHandlers: List<AnimationHandler> = emptyList()

    open val windowModeHandlers: List<WindowModeHandler> = emptyList()

    open val activityForResultCallbackHandlers: List<ActivityForResultCallbackHandler> = emptyList()

    open val permissionHandlers: List<PermissionHandler> = emptyList()

    open val memoryHandlers: List<MemoryHandler> = emptyList()

    open val childFragmentHandlers: List<ChildFragmentHandler> = emptyList()

    open val layoutInflaterHandlers: List<LayoutInflaterHandler> = emptyList()

    open val navigationHandlers: List<NavigationHandler> = emptyList()

    // region Lifecycle event functions

    override fun onAttach(context: Context) {
        super.onAttach(context)

        lifecycleHandlers.forEach { handler ->
            handler.onAttach(context)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleHandlers.forEach { handler ->
            handler.onCreate(savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = lifecycleHandlers.firstResultOrNull { handler ->
        handler.onCreateView(inflater, container, savedInstanceState)
    } ?: super.onCreateView(inflater, container, savedInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleHandlers.forEach { handler ->
            handler.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        lifecycleHandlers.forEach { handler ->
            handler.onActivityCreated(savedInstanceState)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        lifecycleHandlers.forEach { handler ->
            handler.onViewStateRestored(savedInstanceState)
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

    override fun onDestroyView() {
        lifecycleHandlers.forEach { handler ->
            handler.onDestroyView()
        }

        super.onDestroyView()
    }

    override fun onDestroy() {
        lifecycleHandlers.forEach { handler ->
            handler.onDestroy()
        }

        super.onDestroy()
    }

    override fun onDetach() {
        lifecycleHandlers.forEach { handler ->
            handler.onDetach()
        }

        super.onDetach()
    }

    // endregion Lifecycle event functions

    // region Context Menu event functions

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        contextMenuHandlers.forEach { handler ->
            handler.onCreateContextMenu(menu, v, menuInfo)
        }
    }

    override fun onContextItemSelected(item: MenuItem) =
        contextMenuHandlers.any { handler ->
            handler.onContextItemSelected(item)
        } || super.onContextItemSelected(item)

    // endregion Context Menu event functions

    // region Options Menu event functions

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        optionsMenuHandlers.forEach { handler ->
            handler.onPrepareOptionsMenu(menu)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        optionsMenuHandlers.forEach { handler ->
            handler.onCreateOptionsMenu(menu, inflater)
        }
    }

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

    override fun onDestroyOptionsMenu() {
        optionsMenuHandlers.forEach { handler ->
            handler.onDestroyOptionsMenu()
        }

        super.onDestroyOptionsMenu()
    }

    // endregion Options Menu event functions

    // region Inflation event function

    override fun onInflate(context: Context, attrs: AttributeSet, savedInstanceState: Bundle?) {
        super.onInflate(context, attrs, savedInstanceState)

        inflationHandlers.forEach { handler ->
            handler.onInflate(context, attrs, savedInstanceState)
        }
    }

    // endregion Inflation event function

    // region Animation event functions

    override fun onCreateAnimator(transit: Int, enter: Boolean, nextAnim: Int) =
        animationHandlers.firstResultOrNull { handler ->
            handler.onCreateAnimator(transit, enter, nextAnim)
        } ?: super.onCreateAnimator(transit, enter, nextAnim)

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int) =
        animationHandlers.firstResultOrNull { handler ->
            handler.onCreateAnimation(transit, enter, nextAnim)
        } ?: super.onCreateAnimation(transit, enter, nextAnim)

    // endregion Animation event functions

    // region Window Mode event functions

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        windowModeHandlers.forEach { handler ->
            handler.onConfigurationChanged(newConfig)
        }
    }

    override fun onMultiWindowModeChanged(isInMultiWindowMode: Boolean) {
        super.onMultiWindowModeChanged(isInMultiWindowMode)

        windowModeHandlers.forEach { handler ->
            handler.onMultiWindowModeChanged(isInMultiWindowMode)
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)

        windowModeHandlers.forEach { handler ->
            handler.onPictureInPictureModeChanged(isInPictureInPictureMode)
        }
    }

    // endregion Window Mode event functions

    // region Activity for result event function

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        activityForResultCallbackHandlers.forEach { handler ->
            handler.onActivityResult(requestCode, resultCode, data)
        }
    }

    // endregion Activity for result event function

    // region Memory event function

    override fun onLowMemory() {
        super.onLowMemory()

        memoryHandlers.forEach { handler ->
            handler.onLowMemory()
        }
    }

    // endregion Memory event function

    // region Child fragment event function

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)

        childFragmentHandlers.forEach { handler ->
            handler.onAttachFragment(childFragment)
        }
    }

    // endregion Child fragment event function

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

    // region Instance state event function

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        instanceStateHandlers.forEach { handler ->
            handler.onSaveInstanceState(outState)
        }
    }

    // endregion Instance state event function

    // region Layout inflater event function

    override fun onGetLayoutInflater(savedInstanceState: Bundle?) =
        layoutInflaterHandlers.firstResultOrNull { handler ->
            handler.onGetLayoutInflater(savedInstanceState)
        } ?: super.onGetLayoutInflater(savedInstanceState)

    // endregion Layout inflater event function

    // region Visibility event function

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        visibilityHandlers.forEach { handler ->
            handler.onHiddenChanged(hidden)
        }
    }

    // endregion Visibility event function

    // region Navigation event function

    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean) {
        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)

        navigationHandlers.forEach { handler ->
            handler.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
        }
    }

    // endregion Navigation event function
}

private fun <HANDLER, RETURN_VALUE> List<HANDLER>.firstResultOrNull(
    mappingFunction: (HANDLER) -> RETURN_VALUE
) = asSequence()
    .map(mappingFunction)
    .firstOrNull { value -> value != null }
