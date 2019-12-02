package com.mitteloupe.solid.activity.handler.common

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.mitteloupe.solid.activity.handler.LifecycleHandler

class FragmentHandler(
    private val supportFragmentManager: FragmentManager,
    @IdRes private val mainFragmentContainer: Int,
    private val fragmentClass: Class<out Fragment>
) : LifecycleHandler {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.commit {
            replace(mainFragmentContainer, getFragmentInstance(fragmentClass))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) =
        onCreate(savedInstanceState)

    private fun getFragmentInstance(fragmentClass: Class<out Fragment>) =
        supportFragmentManager.fragmentFactory.instantiate(
            fragmentClass.classLoader
                ?: throw RuntimeException("ClassLoader unexpectedly missing"),
            fragmentClass.name
        )
}
