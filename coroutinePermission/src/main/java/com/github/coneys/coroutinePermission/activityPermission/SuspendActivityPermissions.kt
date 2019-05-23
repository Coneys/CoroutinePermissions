package com.github.coneys.coroutinePermission.activityPermission

import com.github.coneys.coroutinePermission.PermissionRequestState
import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions

interface SuspendActivityPermissions: SuspendPermissions {

    suspend fun getRequestResult(permission: String): PermissionRequestState

    suspend fun getRequestLocation(): PermissionRequestState

    suspend fun getRequestCamera(): PermissionRequestState

    suspend fun getRequestExternalStorageRead(): PermissionRequestState

    suspend fun getRequestExternalStorageWrite(): PermissionRequestState


}