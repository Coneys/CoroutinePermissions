package com.devstruktor.coroutine_permission

import android.content.Context
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import kotlinx.coroutines.CancellableContinuation
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.resume

internal class CoroutinePermissionHandler(
    val permission: String,
    cancellableContinuation: CancellableContinuation<Boolean>,
    private val onResult: (CoroutinePermissionHandler) -> Unit
) :
    PermissionHandler() {


    private val continuations = CopyOnWriteArrayList<CancellableContinuation<Boolean>>()

    init {
        continuations.add(cancellableContinuation)
    }

    override fun onActivityRecreation() {
        Permissions.log("Activity recreation")
    }

    fun addAdditionalContinuation(cancellableContinuation: CancellableContinuation<Boolean>) {
        continuations.add(cancellableContinuation)

    }

    override fun onGranted() {
        continuations.forEach { it.resume(true) }
        continuations.clear()
        onResult(this)
    }

    override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
        super.onDenied(context, deniedPermissions)
        continuations.forEach { it.resume(false) }
        continuations.clear()
        onResult(this)
    }

    override fun onBlocked(context: Context?, blockedList: ArrayList<String>?): Boolean {
        continuations.forEach { it.resume(false) }
        continuations.clear()
        onResult(this)
        return super.onBlocked(context, blockedList)
    }

    override fun onJustBlocked(
        context: Context?,
        justBlockedList: ArrayList<String>?,
        deniedPermissions: ArrayList<String>?
    ) {
        onResult(this)
        continuations.forEach { it.resume(false) }
        continuations.clear()
        super.onJustBlocked(context, justBlockedList, deniedPermissions)
    }

    fun removeListeners(): Int {
        val size = continuations.size
        continuations.clear()
        return size
    }
}
