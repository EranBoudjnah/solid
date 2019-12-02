package com.mitteloupe.solid.activity.handler.common

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Gravity
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mitteloupe.solid.activity.R
import com.mitteloupe.solid.activity.handler.LifecycleHandler
import com.mitteloupe.solid.activity.handler.NavigationHandler

class DrawerHandler(
    private val activity: Activity,
    private val lazyDrawerLayout: Lazy<DrawerLayout>,
    private val lazyToolbar: Lazy<Toolbar>,
    private val lazyNavigationView: Lazy<NavigationView>,
    private val onNavigationItemSelectedListener: NavigationView.OnNavigationItemSelectedListener,
    @EdgeGravity private val drawerGravity: Int = GravityCompat.START,
    @StringRes private val openDrawerResourceId: Int = R.string.navigation_drawer_open,
    @StringRes private val closeDrawerResourceId: Int = R.string.navigation_drawer_close
) : LifecycleHandler, NavigationHandler {
    @SuppressLint("RtlHardcoded")
    @IntDef(
        value = [Gravity.LEFT, Gravity.RIGHT, GravityCompat.START, GravityCompat.END],
        flag = true
    )
    @Retention(AnnotationRetention.SOURCE)
    private annotation class EdgeGravity

    private val drawerLayout by lazy { lazyDrawerLayout.value }
    private val toolbar by lazy { lazyToolbar.value }
    private val navigationView by lazy { lazyNavigationView.value }

    override fun onCreate(savedInstanceState: Bundle?) {
        val toggle = ActionBarDrawerToggle(
            activity, drawerLayout, toolbar, openDrawerResourceId, closeDrawerResourceId
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) =
        onCreate(savedInstanceState)

    override fun onBackPressed() =
        if (drawerLayout.isDrawerOpen(drawerGravity)) {
            drawerLayout.closeDrawer(drawerGravity)
            true
        } else {
            false
        }
}
