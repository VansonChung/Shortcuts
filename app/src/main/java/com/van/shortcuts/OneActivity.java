package com.van.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class OneActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);
        Log.i(MainActivity.TAG, getIntent().getAction());
    }
}
