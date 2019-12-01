package com.mitteloupe.solid.fragment.handler

import androidx.fragment.app.Fragment

interface PermissionHandler {
    /**
     * @see Fragment.onRequestPermissionsResult
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    )
}
