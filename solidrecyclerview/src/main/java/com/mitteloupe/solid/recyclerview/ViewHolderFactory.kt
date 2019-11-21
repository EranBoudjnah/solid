package com.mitteloupe.solid.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KClass

open class ViewHolderFactory<VIEW_HOLDER : RecyclerView.ViewHolder> {
    inline fun <reified CLASS_TYPE : VIEW_HOLDER> holdView(view: View): VIEW_HOLDER {
        @Suppress("UNCHECKED_CAST")
        val viewHolderClass = CLASS_TYPE::class as KClass<VIEW_HOLDER>
        val constructor = viewHolderClass.constructors
            .firstOrNull { constructor -> constructor.parameters.size == 1 }
            ?: throw NoUsableConstructorException()

        try {
            return constructor.call(view)
        } catch (exception: Exception) {
            throw ViewHolderCreationException()
        }
    }
}

class NoUsableConstructorException : Exception()

class ViewHolderCreationException : Exception()
