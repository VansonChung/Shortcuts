package com.van.shortcuts

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class ShortcutReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "ShortcutReceiver"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "onReceive : $intent.toString()")
    }
}
