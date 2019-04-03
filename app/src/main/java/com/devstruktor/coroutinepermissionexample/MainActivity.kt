package com.devstruktor.coroutinepermissionexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devstruktor.coroutine_permission.CoroutinePermissions
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val permissions = CoroutinePermissions(this)
        GlobalScope.launch {

            launch {
                val result = permissions.requestExternalStorageRead()
                println(result)
            }

            launch {
                val result2 = permissions.requestExternalStorageRead()
                println(result2)
            }


            launch {
                val result3 = permissions.requestExternalStorageRead()
                println(result3)

            }

        }

    }
}
