package com.devstruktor.coroutine_permission

import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nabinbhandari.android.permissions.Permissions

interface SuspendPermissions {
    suspend fun request(permission: String): Boolean

    suspend fun requestLocation(): Boolean

    suspend fun requestCamera(): Boolean

    suspend fun requestExternalStorageRead(): Boolean

    suspend fun requestExternalStorageWrite(): Boolean

    companion object {

        fun from(activity: FragmentActivity) = CoroutinePermissions(activity as Context)
        fun from(fragment: Fragment) = CoroutinePermissions(fragment.requireContext())
        fun from(application: Application) = CoroutinePermissions(application.applicationContext)
        fun disableLogging() {
            Permissions.disableLogging()
        }

    }

}