package com.mitteloupe.solid.activity.handler

import android.app.Activity

interface PermissionHandler {
    /**
     * @see Activity.onRequestPermissionsResult
     */
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    )
}
