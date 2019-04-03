package com.devstruktor.coroutine_permission

import android.Manifest
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.coroutines.resume


class CoroutinePermissions internal constructor(private val context: Context) {

    constructor(activity: FragmentActivity) : this(activity as Context)
    constructor(fragment: Fragment) : this(fragment.requireContext())


    companion object {
        fun disableLogging() {
            Permissions.disableLogging()
        }
    }

    suspend fun request(permission: String): Boolean {
        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine<Boolean> { continuation ->
                try {

                    Permissions.check(
                        context/*context*/,
                        permission,
                        null,
                        object : PermissionHandler() {
                            override fun onGranted() {
                                continuation.resume(true)
                            }

                            override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
                                super.onDenied(context, deniedPermissions)
                                continuation.resume(false)
                            }

                            override fun onBlocked(context: Context?, blockedList: ArrayList<String>?): Boolean {
                                continuation.resume(false)
                                return super.onBlocked(context, blockedList)
                            }

                            override fun onJustBlocked(
                                context: Context?,
                                justBlockedList: ArrayList<String>?,
                                deniedPermissions: ArrayList<String>?
                            ) {
                                continuation.resume(false)
                                super.onJustBlocked(context, justBlockedList, deniedPermissions)
                            }
                        })


                } catch (t: Throwable) {
                    t.printStackTrace()
                    continuation.cancel(t)
                }

            }
        }


    }

    suspend fun requestLocation() = request(Manifest.permission.ACCESS_FINE_LOCATION)
    suspend fun requestCamera() = request(Manifest.permission.CAMERA)
    suspend fun requestExternalStorageRead() = request(Manifest.permission.READ_EXTERNAL_STORAGE)
    suspend fun requestExternalStorageWrite() = request(Manifest.permission.WRITE_EXTERNAL_STORAGE)

}