package com.mitteloupe.solid.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class SolidFragmentFactory(
    private val fragmentProviders: Map<Class<out Fragment>, () -> Fragment> = emptyMap(),
    private val onProviderMissing: (className: String) -> Unit = {}
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        val fragmentClass = loadFragmentClass(classLoader, className)
        val provider = fragmentProviders[fragmentClass]
            ?: let {
                notifyProviderMissing(className)
                return super.instantiate(classLoader, className)
            }

        return provider()
    }

    private fun notifyProviderMissing(className: String) {
        onProviderMissing(className)
    }
}
