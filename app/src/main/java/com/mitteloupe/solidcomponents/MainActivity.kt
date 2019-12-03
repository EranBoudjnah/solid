package com.mitteloupe.solidcomponents

import android.os.Bundle
import com.mitteloupe.solid.activity.SolidActivity
import com.mitteloupe.solid.activity.handler.common.DrawerHandler
import com.mitteloupe.solid.activity.handler.common.FragmentHandler
import com.mitteloupe.solid.activity.handler.common.LayoutLifecycleHandler
import com.mitteloupe.solid.activity.handler.common.SimpleOptionsMenuHandler
import com.mitteloupe.solid.activity.handler.common.ToolbarHandler
import com.mitteloupe.solid.fragment.SolidFragmentFactory
import com.mitteloupe.solidcomponents.hander.KoinActivityScopeHandler
import kotlinx.android.synthetic.main.activity_main.main_drawer_layout as drawerLayout
import kotlinx.android.synthetic.main.activity_main.main_navigation_view as navigationView
import kotlinx.android.synthetic.main.include_toolbar.toolbar
import org.koin.android.scope.currentScope

class MainActivity : SolidActivity() {
    private val fragmentFactory: SolidFragmentFactory by currentScope.inject()

    private val drawerHandler = DrawerHandler(
        this, lazy { drawerLayout }, lazy { toolbar }, lazy { navigationView },
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
        )
    )

    override val navigationHandlers = listOf(
        drawerHandler
    )

    override val optionsMenuHandlers = listOf(
        SimpleOptionsMenuHandler(
            lazy { menuInflater }, R.menu.main_options, MainOptionsMenuListener(this)
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)
    }
}
