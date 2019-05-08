package com.devstruktor.coroutinepermissionexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.devstruktor.PermissionRequestState
import com.devstruktor.coroutine_permission.CoroutinePermissions
import com.devstruktor.coroutine_permission.staticPermission.SuspendPermissions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ActivityPermissionExample : AppCompatActivity(), CoroutineScope {

    private val permissions by lazy { CoroutinePermissions.createInstanceForActivity(this) }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + Job()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        test_1.setOnClickListener {
            simpleTest(permissions)
        }

        test_2.setOnClickListener {
            scopeTest(permissions)
        }

        test_3.setOnClickListener {
            startActivity(Intent(this, StaticPermissionExample::class.java))
        }

        launch {
            val result = permissions.getRequestExternalStorageRead()
            if (result != PermissionRequestState.NO_REQUEST_PENDING)
                withContext(Dispatchers.Main){
                    Toast.makeText(
                        this@ActivityPermissionExample,
                        "Permission restored after rotation :$result",
                        Toast.LENGTH_SHORT
                    ).show()
                }

        }

    }


    private fun simpleTest(permissions: SuspendPermissions) {

        launch {
            val result = permissions.requestExternalStorageRead()
            withContext(Dispatchers.Main){
                Toast.makeText(
                    this@ActivityPermissionExample,
                    "Permission on activity was accepted : $result",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    /**
     * Result will be showed on logs, but won't be printed in activity, because scope will be closed
     */
    private fun scopeTest(permissions: SuspendPermissions) {

        launch {

            val job = launch {
                val result = permissions.requestExternalStorageRead()
                println("Result on activity $result") //it won't work
            }
            delay(1000)

            job.cancel()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
