package com.flowlayouttest;

import android.app.Application;
import android.content.Context;
import android.util.Log;

/**
 * Created by HaoPz_PC on 2016/11/1.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();
        Log.e("MyApplication", "onCreate: *********************"+applicationContext );
    }
}
