package com.van.shortcuts

import android.app.Activity
import android.os.Bundle
import android.util.Log

class OneActivity : Activity() {

    companion object {
        private const val TAG = "OneActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)
        Log.i(TAG, intent.action)
    }
}
