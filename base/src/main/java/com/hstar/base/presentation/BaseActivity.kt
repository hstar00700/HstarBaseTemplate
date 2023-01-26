package com.hstar.base.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity

abstract class BaseActivity() : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    protected abstract fun init()
}
