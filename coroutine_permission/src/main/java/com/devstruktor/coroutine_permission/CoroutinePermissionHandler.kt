package com.devstruktor.coroutine_permission

import android.content.Context
import com.nabinbhandari.android.permissions.PermissionHandler
import kotlinx.coroutines.CancellableContinuation
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.coroutines.resume

class CoroutinePermissionHandler(
    val permission: String,
    cancellableContinuation: CancellableContinuation<Boolean>,
    private val onResult: (CoroutinePermissionHandler) -> Unit
) :
    PermissionHandler() {

    private val continuations = CopyOnWriteArrayList<CancellableContinuation<Boolean>>()

    init {
        continuations.add(cancellableContinuation)
    }

    fun addAdditionalContinuation(cancellableContinuation: CancellableContinuation<Boolean>) {
        continuations.add(cancellableContinuation)
    }

    override fun onGranted() {
        continuations.forEach { it.resume(true) }
        onResult(this)
    }

    override fun onDenied(context: Context?, deniedPermissions: ArrayList<String>?) {
        super.onDenied(context, deniedPermissions)
        continuations.forEach { it.resume(false) }
        onResult(this)
    }

    override fun onBlocked(context: Context?, blockedList: ArrayList<String>?): Boolean {
        continuations.forEach { it.resume(false) }
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
        super.onJustBlocked(context, justBlockedList, deniedPermissions)
    }
}