package com.github.coroutinepermissionexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.devstruktor.coroutinepermissionexample.R
import kotlinx.android.synthetic.main.activity_main.*


class StaticPermissionExample : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.static_permission_activity)

        SomeStatic.application = application

        test_1.setOnClickListener {
            SomeStatic.test()
        }
    }

}
