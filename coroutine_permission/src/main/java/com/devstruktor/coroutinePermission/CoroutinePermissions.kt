package com.devstruktor.coroutinePermission

import androidx.fragment.app.FragmentActivity
import com.devstruktor.coroutinePermission.activityPermission.CoroutineActivityPermissions
import com.devstruktor.coroutinePermission.activityPermission.SuspendActivityPermissions
import com.devstruktor.coroutinePermission.staticPermission.CoroutineStaticPermissions
import com.devstruktor.coroutinePermission.staticPermission.SuspendPermissions
import com.devstruktor.initalizer.PermissionsInitProvider
import com.devstruktor.nabinbhandariPermissions.Permissions

object CoroutinePermissions {
    fun getInstance(): SuspendPermissions {
        return CoroutineStaticPermissions(PermissionsInitProvider.appContext)
    }

    fun createInstanceForActivity(fragmentActivity: FragmentActivity): SuspendActivityPermissions {
        return CoroutineActivityPermissions(fragmentActivity, fragmentActivity.lifecycle)
    }


    fun disableLogging() {
        Permissions.disableLogging()
    }

    fun enableLogging() {
        Permissions.enableLogging()
    }
}