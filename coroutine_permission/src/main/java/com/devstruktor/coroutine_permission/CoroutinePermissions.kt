package com.devstruktor.coroutine_permission

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext


class CoroutinePermissions internal constructor(private val context: Context) {

    constructor(activity: FragmentActivity) : this(activity as Context)
    constructor(fragment: Fragment) : this(fragment.requireContext())
    constructor(application: Application) : this(application.applicationContext)

    private val currentRequests = ArrayList<CoroutinePermissionHandler>()

    companion object {
        fun disableLogging() {
            Permissions.disableLogging()
        }
    }


    suspend fun request(permission: String): Boolean {


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
                currentRequests.remove(it)
                if(currentRequests.isEmpty()){
                    if (context is FragmentActivity) {
                        context.apply {
                            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
                        }

                    }
                }
            }

            try {

                if (context is FragmentActivity) {
                    context.apply {
                        requestedOrientation = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        } else ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    }

                }

                currentRequests.add(handler)
                Permissions.check(context, permission, null, handler)

            } catch (t: Throwable) {
                t.printStackTrace()
                currentRequests.remove(handler)
                continuation.cancel(t)
            }

        }
    }

    private suspend fun addContinuationToCurrentRequest(currentPermissionRequest: CoroutinePermissionHandler): Boolean {
        return suspendCancellableCoroutine {
            currentPermissionRequest.addAdditionalContinuation(it)
        }
    }

    suspend fun requestLocation() = request(Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun requestCamera() = request(Manifest.permission.CAMERA)
    suspend fun requestExternalStorageRead() = request(Manifest.permission.READ_EXTERNAL_STORAGE)
    suspend fun requestExternalStorageWrite() = request(Manifest.permission.WRITE_EXTERNAL_STORAGE)

}