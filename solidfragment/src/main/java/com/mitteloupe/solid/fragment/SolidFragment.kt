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
import com.mitteloupe.solid.fragment.handler.AnimationHandler
import com.mitteloupe.solid.fragment.handler.ContextMenuHandler
import com.mitteloupe.solid.fragment.handler.InflationHandler
import com.mitteloupe.solid.fragment.handler.LifecycleHandler
import com.mitteloupe.solid.fragment.handler.OptionsMenuHandler

abstract class SolidFragment : Fragment() {
    open val lifecycleHandlers: List<LifecycleHandler> = emptyList()

    open val contextMenuHandlers: List<ContextMenuHandler> = emptyList()

    open val optionsMenuHandlers: List<OptionsMenuHandler> = emptyList()

    open val inflationHandlers: List<InflationHandler> = emptyList()

    open val animationHandlers: List<AnimationHandler> = emptyList()

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
}

private fun <HANDLER, RETURN_VALUE> List<HANDLER>.firstResultOrNull(
    mappingFunction: (HANDLER) -> RETURN_VALUE
) = asSequence()
    .map(mappingFunction)
    .firstOrNull { value -> value != null }
