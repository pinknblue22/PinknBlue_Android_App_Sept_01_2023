package com.pinknblue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;


import androidx.appcompat.app.AppCompatActivity;

import uitls.Constant;

public class SplashActivity extends AppCompatActivity {
     SharedPreferences preference;
     private String loginUserId="",UserType=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Constant.checkGpsAndsaveLocationAddress(SplashActivity.this);

        preference = getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preference.getString("user_id", "");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!(loginUserId.equalsIgnoreCase(""))){
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    intent.putExtra("type","");
                    startActivity(intent);
                    finish();

                }else {
                    Intent intent = new Intent(SplashActivity.this,HomeActivity.class);
                    intent.putExtra("type","login");
                    startActivity(intent);
                    finish();

                }



            }
        },1000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
