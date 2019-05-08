package com.devstruktor.coroutine_permission

import androidx.fragment.app.FragmentActivity
import com.devstruktor.coroutine_permission.activityPermission.CoroutineActivityPermissions
import com.devstruktor.coroutine_permission.activityPermission.SuspendActivityPermissions
import com.devstruktor.coroutine_permission.staticPermission.CoroutineStaticPermissions
import com.devstruktor.coroutine_permission.staticPermission.SuspendPermissions
import com.devstruktor.initalizer.PermissionsInitProvider
import com.nabinbhandari.android.permissions.Permissions

object CoroutinePermissions {
    fun getInstance(): SuspendPermissions {
        return CoroutineStaticPermissions(PermissionsInitProvider.appContext)
    }

    fun createInstanceForActivity(fragmentActivity: FragmentActivity): SuspendActivityPermissions {
        return CoroutineActivityPermissions(fragmentActivity,fragmentActivity.lifecycle)
    }


    fun disableLogging() {
        Permissions.disableLogging()
    }

    fun enableLogging() {
        Permissions.enableLogging()
    }
}