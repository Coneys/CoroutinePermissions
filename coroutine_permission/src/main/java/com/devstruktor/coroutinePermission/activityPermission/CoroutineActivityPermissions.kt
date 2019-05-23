package com.devstruktor.coroutinePermission.activityPermission

import android.Manifest
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.devstruktor.PermissionRequestState
import com.devstruktor.coroutinePermission.staticPermission.CoroutineStaticPermissions
import com.devstruktor.nabinbhandariPermissions.Permissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class CoroutineActivityPermissions(activityContext: Context, lifecycle: Lifecycle) :
    CoroutineStaticPermissions(activityContext), SuspendActivityPermissions, LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }

    override suspend fun getRequestResult(permission: String): PermissionRequestState {
        return withContext(Dispatchers.Main) {

            val currentPermissionRequest = currentRequests.find { it.permission == permission }

            if (currentPermissionRequest != null) {
                val wasPermissionGranted = addContinuationToCurrentRequest(currentPermissionRequest)
                mapBooleanToState(wasPermissionGranted)

            } else PermissionRequestState.NO_REQUEST_PENDING

        }
    }

    private fun mapBooleanToState(wasPermissionGranted: Boolean): PermissionRequestState {
        return if (wasPermissionGranted)
            PermissionRequestState.ACCEPTED
        else PermissionRequestState.DENIED
    }

    override suspend fun getRequestLocation() = getRequestResult(Manifest.permission.ACCESS_FINE_LOCATION)
    override suspend fun getRequestCamera() = getRequestResult(Manifest.permission.CAMERA)
    override suspend fun getRequestExternalStorageRead() = getRequestResult(Manifest.permission.READ_EXTERNAL_STORAGE)
    override suspend fun getRequestExternalStorageWrite() = getRequestResult(Manifest.permission.WRITE_EXTERNAL_STORAGE)


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        Permissions.log("onDestroy called")
        currentRequests.forEach {
            val removedListeners = it.removeListeners()
            if (removedListeners > 0)
                Permissions.log("Removed $removedListeners from ${it.permission} handler")
        }
    }
}