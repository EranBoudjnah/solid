package com.mitteloupe.solidcomponents

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.mitteloupe.solid.activity.SolidActivity
import com.mitteloupe.solid.activity.handler.common.LayoutLifecycleHandler
import com.mitteloupe.solid.fragment.SolidFragmentFactory
import com.mitteloupe.solidcomponents.hander.KoinActivityScopeHandler
import org.koin.android.scope.currentScope

class MainActivity : SolidActivity() {
    private val fragmentFactory: SolidFragmentFactory by currentScope.inject()

    override val lifecycleHandlers = listOf(
        KoinActivityScopeHandler(this, currentScope),
        LayoutLifecycleHandler(this, R.layout.activity_main)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = fragmentFactory

        super.onCreate(savedInstanceState)

        supportFragmentManager.commit {
            replace(R.id.main_fragment_container, getFragmentInstance())
        }
    }

    private fun getFragmentInstance(): Fragment {
        return supportFragmentManager.fragmentFactory.instantiate(
            MainFragment::class.java.classLoader
                ?: throw RuntimeException("ClassLoader unexpectedly missing"),
            MainFragment::class.java.name
        )
    }
}
