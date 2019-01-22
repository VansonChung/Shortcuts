package com.van.shortcuts

import android.app.Activity
import android.os.Bundle
import android.util.Log

class TwoActivity : Activity() {

    companion object {
        private const val TAG = "TwoActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        Log.i(TAG, intent.action)
    }
}
