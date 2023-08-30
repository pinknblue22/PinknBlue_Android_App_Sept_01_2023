package com.pinknblue;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.google.firebase.FirebaseApp;


/**
 * Created by eweba1-pc-84 on 7/15/2016.
 */
public class MyApplication extends MultiDexApplication {

    public static SharedPreferences sharedPref_pnb;

    public static double latitute = 0.0;
    public static double longitude = 0.0;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }

    /** used to initialize instance globally of SharedPreferences */
    private void initSharedPreferences()
    {
        try
        {
            sharedPref_pnb = getApplicationContext().getSharedPreferences("sharedPref_pnb", Context.MODE_PRIVATE);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /** used to get instance globally of SharedPreferences */
    public static synchronized SharedPreferences getSharedPreferences()
    {
        return sharedPref_pnb;
    }
}
