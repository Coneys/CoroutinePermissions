package com.devstruktor.coroutine_permission

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

class CoroutinePermissions internal constructor(private val rxPermissions: RxPermissions) {

    constructor(activity: FragmentActivity) : this(RxPermissions(activity))
    constructor(fragment: Fragment) : this(RxPermissions(fragment))


    suspend fun request(permission: String): Boolean {
        return withContext(Dispatchers.Main) {
            suspendCancellableCoroutine<Boolean> { continuation ->
                try {
                    val disposable = rxPermissions.request(permission)
                        .take(1)
                        .onErrorReturnItem(false)
                        .subscribe { continuation.resume(it) }

                    continuation.invokeOnCancellation {
                        disposable.dispose()
                    }
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