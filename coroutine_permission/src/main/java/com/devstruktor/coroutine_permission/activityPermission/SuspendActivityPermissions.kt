package com.devstruktor.coroutine_permission.activityPermission

import com.devstruktor.PermissionRequestState
import com.devstruktor.coroutine_permission.staticPermission.SuspendPermissions

interface SuspendActivityPermissions: SuspendPermissions {

    suspend fun getRequestResult(permission: String): PermissionRequestState

    suspend fun getRequestLocation(): PermissionRequestState

    suspend fun getRequestCamera(): PermissionRequestState

    suspend fun getRequestExternalStorageRead(): PermissionRequestState

    suspend fun getRequestExternalStorageWrite(): PermissionRequestState


}