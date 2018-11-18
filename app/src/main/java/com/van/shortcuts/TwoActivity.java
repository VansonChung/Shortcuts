package com.van.shortcuts;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class TwoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        Log.i(MainActivity.TAG, getIntent().getAction());
    }
}
