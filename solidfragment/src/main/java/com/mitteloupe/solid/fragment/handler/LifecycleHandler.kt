package com.mitteloupe.solid.fragment.handler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

interface LifecycleHandler {
    /**
     * @see Fragment.onAttach
     */
    fun onAttach(context: Context) = Unit

    /**
     * @see Fragment.onCreate
     */
    fun onCreate(savedInstanceState: Bundle?) = Unit

    /**
     * @see Fragment.onCreateView
     */
    fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = null

    /**
     * @see Fragment.onViewCreated
     */
    fun onViewCreated(view: View, savedInstanceState: Bundle?) = Unit

    /**
     * @see Fragment.onActivityCreated
     */
    fun onActivityCreated(savedInstanceState: Bundle?) = Unit

    /**
     * @see Fragment.onViewStateRestored
     */
    fun onViewStateRestored(savedInstanceState: Bundle?) = Unit

    /**
     * @see Fragment.onStart
     */
    fun onStart() = Unit

    /**
     * @see Fragment.onResume
     */
    fun onResume() = Unit

    /**
     * @see Fragment.onPause
     */
    fun onPause() = Unit

    /**
     * @see Fragment.onStop
     */
    fun onStop() = Unit

    /**
     * @see Fragment.onDestroy
     */
    fun onDestroy() = Unit

    /**
     * @see Fragment.onDestroyView
     */
    fun onDestroyView() = Unit

    /**
     * @see Fragment.onDetach
     */
    fun onDetach() = Unit
}
