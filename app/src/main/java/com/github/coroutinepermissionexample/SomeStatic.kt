package com.github.coroutinepermissionexample

import android.app.Application
import android.widget.Toast
import com.github.coneys.coroutinePermission.CoroutinePermissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object SomeStatic {
    lateinit var application: Application
    fun test() {
        GlobalScope.launch {
            val permissions = CoroutinePermissions.getInstance()
            val result = permissions.requestExternalStorageRead()
            withContext(Dispatchers.Main){
                Toast.makeText(
                    application,
                    "Permission on static was accepted : $result",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}