package com.devstruktor.coroutine_permission.staticPermission

import android.Manifest
import android.content.Context
import com.devstruktor.coroutine_permission.CoroutinePermissionHandler
import com.devstruktor.nabinbhandariPermissions.Permissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

internal open class CoroutineStaticPermissions internal constructor(private val applicationContext: Context) :
    SuspendPermissions {

    companion object {
        internal val currentRequests = ArrayList<CoroutinePermissionHandler>()

    }

    override suspend fun request(permission: String): Boolean {

        return withContext(Dispatchers.Main) {

            val currentPermissionRequest = currentRequests.find { it.permission == permission }

            if (currentPermissionRequest != null) {
                addContinuationToCurrentRequest(currentPermissionRequest)
            } else {
                createNewRequestAndContinuation(permission)
            }

        }
    }


    private suspend fun createNewRequestAndContinuation(permission: String): Boolean {
        return suspendCancellableCoroutine { continuation ->

            val handler = CoroutinePermissionHandler(permission, continuation) {
                currentRequests.remove(
                    it
                )
            }

            try {
                currentRequests.add(handler)
                Permissions.check(applicationContext, permission, null, handler)

            } catch (t: Throwable) {
                t.printStackTrace()
                currentRequests.remove(handler)
                continuation.cancel(t)
            }

        }
    }


    protected suspend fun addContinuationToCurrentRequest(currentPermissionRequest: CoroutinePermissionHandler): Boolean {
        return suspendCancellableCoroutine {
            currentPermissionRequest.addAdditionalContinuation(it)
        }
    }

    override suspend fun requestLocation() = request(Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun requestCamera() = request(Manifest.permission.CAMERA)
    override suspend fun requestExternalStorageRead() = request(Manifest.permission.READ_EXTERNAL_STORAGE)
    override suspend fun requestExternalStorageWrite() = request(Manifest.permission.WRITE_EXTERNAL_STORAGE)

}