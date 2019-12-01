package com.mitteloupe.solidcomponents.di

import android.util.Log
import androidx.fragment.app.Fragment
import com.mitteloupe.solid.fragment.SolidFragmentFactory
import com.mitteloupe.solid.recyclerview.SolidAdapter
import com.mitteloupe.solid.recyclerview.ViewBinder
import com.mitteloupe.solid.recyclerview.ViewProvider
import com.mitteloupe.solidcomponents.MainActivity
import com.mitteloupe.solidcomponents.MainFragment
import com.mitteloupe.solidcomponents.adapter.MoodViewBinder
import com.mitteloupe.solidcomponents.adapter.MoodViewHolder
import com.mitteloupe.solidcomponents.adapter.MoodViewProvider
import com.mitteloupe.solidcomponents.model.MoodUiModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    scope(named<MainActivity>()) {
        scoped<Map<Class<out Fragment>, () -> Fragment>> {
            mapOf(MainFragment::class.java to { MainFragment() })
        }

        scoped<(className: String) -> Unit> {
            { className ->
                Log.d("FragmentFactory", "Missing provider for $className")
            }
        }

        scoped {
            SolidFragmentFactory(get(), get())
        }
    }

    scope(named<MainFragment>()) {
        scoped {
            get<Fragment>().layoutInflater
        }

        scoped<ViewProvider> {
            MoodViewProvider(get())
        }

        scoped<ViewBinder<MoodViewHolder, MoodUiModel>> {
            MoodViewBinder(get())
        }

        scoped {
            SolidAdapter(
                get(),
                { view, _ -> MoodViewHolder(view) },
                get<ViewBinder<MoodViewHolder, MoodUiModel>>()
            )
        }
    }
}
