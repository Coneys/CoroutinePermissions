package com.github.coneys.coroutinePermission

import androidx.fragment.app.FragmentActivity
import com.github.coneys.coroutinePermission.activityPermission.CoroutineActivityPermissions
import com.github.coneys.coroutinePermission.activityPermission.SuspendActivityPermissions
import com.github.coneys.coroutinePermission.staticPermission.CoroutineStaticPermissions
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions
import com.github.coneys.coroutinePermission.initalizer.PermissionsInitProvider
import com.github.coneys.coroutinePermission.nabinbhandariPermissions.Permissions

object CoroutinePermissions {
    fun getInstance(): SuspendPermissions {
        return CoroutineStaticPermissions(PermissionsInitProvider.appContext)
    }

    fun createInstanceForActivity(fragmentActivity: FragmentActivity): SuspendActivityPermissions {
        return CoroutineActivityPermissions(
            fragmentActivity,
            fragmentActivity.lifecycle
        )
    }


    fun disableLogging() {
        Permissions.disableLogging()
    }

    fun enableLogging() {
        Permissions.enableLogging()
    }
}