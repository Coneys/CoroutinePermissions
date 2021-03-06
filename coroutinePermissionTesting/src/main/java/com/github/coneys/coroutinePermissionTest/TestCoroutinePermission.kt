package com.github.coneys.coroutinePermissionTest

import com.github.coneys.coroutinePermission.staticPermission.SuspendPermissions

class TestCoroutinePermission(private val returnSuccess: Boolean) : SuspendPermissions {
    override suspend fun request(permission: String) = returnSuccess

    override suspend fun requestLocation() = returnSuccess

    override suspend fun requestCamera() = returnSuccess

    override suspend fun requestExternalStorageRead() = returnSuccess

    override suspend fun requestExternalStorageWrite() = returnSuccess

}