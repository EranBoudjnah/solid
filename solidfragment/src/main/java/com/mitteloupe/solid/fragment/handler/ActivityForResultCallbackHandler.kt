package com.mitteloupe.solid.fragment.handler

import android.content.Intent
import androidx.fragment.app.Fragment

interface ActivityForResultCallbackHandler {
    /**
     * @see Fragment.onActivityResult
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}
