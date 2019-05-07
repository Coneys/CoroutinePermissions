package com.devstruktor.coroutine_permission

interface SuspendPermissions {
    suspend fun request(permission: String): Boolean

    suspend fun requestLocation(): Boolean

    suspend fun requestCamera(): Boolean

    suspend fun requestExternalStorageRead(): Boolean

    suspend fun requestExternalStorageWrite(): Boolean
}