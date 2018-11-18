package com.van.shortcuts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ShortcutReceiver extends BroadcastReceiver {

    private static final String TAG = "ShortcutReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive : " + intent.toString());
    }
}
