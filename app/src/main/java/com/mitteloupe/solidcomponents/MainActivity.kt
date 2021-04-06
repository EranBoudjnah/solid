package com.mitteloupe.solidcomponents

import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mitteloupe.solid.activity.SolidActivity
import com.mitteloupe.solid.activity.handler.common.DrawerHandler
import com.mitteloupe.solid.activity.handler.common.FragmentHandler
import com.mitteloupe.solid.activity.handler.common.LayoutLifecycleHandler
import com.mitteloupe.solid.activity.handler.common.SimpleOptionsMenuHandler
import com.mitteloupe.solid.activity.handler.common.ToolbarHandler
import com.mitteloupe.solid.fragment.SolidFragmentFactory
import com.mitteloupe.solidcomponents.hander.KoinActivityScopeHandler
import com.mitteloupe.solidcomponents.hander.ResponseReceiverHandler
import org.koin.android.scope.currentScope

class MainActivity : SolidActivity() {
    private val toolbar: Toolbar by lazy { findViewById(R.id.toolbar) }
    private val drawerLayout: DrawerLayout by lazy { findViewById(R.id.main_drawer_layout) }
    private val navigationView: NavigationView by lazy { findViewById(R.id.main_navigation_view) }

    private val fragmentFactory: SolidFragmentFactory by currentScope.inject()

    private val drawerHandler = DrawerHandler(
        this,
        lazy { drawerLayout },
        lazy { toolbar },
        lazy { navigationView },
        MainDrawerItemSelectedListener(this)
    )

    override val lifecycleHandlers = listOf(
        KoinActivityScopeHandler(this, currentScope),
        LayoutLifecycleHandler(this, R.layout.activity_main),
        ToolbarHandler(this, lazy { toolbar }),
        drawerHandler,
        FragmentHandler(
            supportFragmentManager,
            R.id.main_fragment_container,
            MainFragment::class.java,
            lazy { fragmentFactory }
        ),
        ResponseReceiverHandler(this, ResponseReceiver())
    )

    override val navigationHandlers = listOf(
        drawerHandler
    )

    override val optionsMenuHandlers = listOf(
        SimpleOptionsMenuHandler(
            lazy { menuInflater },
            R.menu.main_options,
            MainOptionsMenuListener(this)
        )
    )
}
