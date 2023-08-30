package com.pinknblue;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.alexandroid.gps.GpsStatusDetector;

import Fragment.FragmentAllrouts;
import Fragment.FragmentUnlock;
import Fragment.FragmentHotspots;
import Fragment.FragmentMore;
import uitls.Constant;
import uitls.DbHandler;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener,GpsStatusDetector.GpsStatusDetectorCallBack {

    LinearLayout layout_home,layout_routs,layout_hotspot,layout_unlock,layout_more;
    ImageView img_home,img_routs,img_hotspot,img_unlock,img_more,img_tool_search,img_tool_filter,img_tool_search_hotspot;
    TextView txt_home,txt_routs,txt_hotspot,txt_unlock,txt_more,txt_tool_title;
    Toolbar toolbar;
    String type,cat_name,filter_type;
    Fragment fragment;
    Intent intent;
    Drawable upArrow;
    public  static  NavigationActivity navigationActivity;
    FrameLayout notification_layout;
    TextView notification_count;
    String ueserTye;
    SharedPreferences preference;

    DbHandler dbHandler;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private GpsStatusDetector mGpsStatusDetector;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigationActivity=this;

        preference  =   getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        ueserTye    =   preference.getString("user_type","");

        Constant.checkGpsAndsaveLocationAddress(NavigationActivity.this);

        setContentView(R.layout.activity_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
     /*   upArrow = ContextCompat.getDrawable(this, R.mipmap.back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
//        checkPermissions();


        mGpsStatusDetector = new GpsStatusDetector(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                mGpsStatusDetector.checkGpsStatus();
            } else {
                requestPermission();
            }
        }else {
            mGpsStatusDetector.checkGpsStatus();
        }


         type=getIntent().getStringExtra("type");
         cat_name=getIntent().getStringExtra("cat_name");
         filter_type=getIntent().getStringExtra("filtertype");

        initUI();

       switch (type){
           case "hotspot":
               txt_tool_title.setText("Hotspots");
               img_tool_filter.setVisibility(View.VISIBLE);
               img_tool_search.setVisibility(View.GONE);
               img_tool_search_hotspot.setVisibility(View.VISIBLE);
               layout_hotspot.setBackgroundResource(R.mipmap.selected_tab);
               txt_hotspot.setTextColor(Color.parseColor("#1c4078"));
               img_hotspot.setImageResource(R.mipmap.select_hotspot);
               Bundle bundle=new Bundle();
               bundle.putString("cat_name",cat_name);
               bundle.putString("filtertype",filter_type);
               fragment=new FragmentHotspots();
               fragment.setArguments(bundle);
               getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
           break;
           case "routs":
               txt_tool_title.setText("All Routes");
               img_tool_filter.setVisibility(View.GONE);
               img_tool_search.setVisibility(View.VISIBLE);
               img_tool_search_hotspot.setVisibility(View.GONE);
               layout_routs.setBackgroundResource(R.mipmap.selected_tab);
               txt_routs.setTextColor(Color.parseColor("#1c4078"));
               img_routs.setImageResource(R.mipmap.select_routs);
               fragment=new FragmentAllrouts();
               getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
               break;
           case "more":
               txt_tool_title.setText("More");
               img_tool_filter.setVisibility(View.GONE);
               img_tool_search.setVisibility(View.GONE);
               img_tool_search_hotspot.setVisibility(View.GONE);
               layout_more.setBackgroundResource(R.mipmap.selected_tab);
               txt_more.setTextColor(Color.parseColor("#1c4078"));
               img_more.setImageResource(R.mipmap.select_more);
               fragment=new FragmentMore();
               getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
               break;
           case "unlock":
               img_tool_filter.setVisibility(View.GONE);
               img_tool_search.setVisibility(View.GONE);
               img_tool_search_hotspot.setVisibility(View.GONE);
               txt_tool_title.setText("Unlock Features");
               layout_unlock.setBackgroundResource(R.mipmap.selected_tab);
               txt_unlock.setTextColor(Color.parseColor("#1c4078"));
               img_unlock.setImageResource(R.mipmap.select_unlock);
               fragment=new FragmentUnlock();
               getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
               break;
       }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initUI() {
        layout_home=(LinearLayout)findViewById(R.id.layout_home);
        layout_routs=(LinearLayout)findViewById(R.id.layout_routs);
        layout_hotspot=(LinearLayout)findViewById(R.id.layout_hotspot);
        layout_unlock=(LinearLayout)findViewById(R.id.layout_unlock);
        layout_more=(LinearLayout)findViewById(R.id.layout_more);

        img_home=(ImageView)findViewById(R.id.img_home);
        img_routs=(ImageView)findViewById(R.id.img_routs);
        img_hotspot=(ImageView)findViewById(R.id.img_hotspot);
        img_unlock=(ImageView)findViewById(R.id.img_unlock);
        img_more=(ImageView)findViewById(R.id.img_more);
        img_tool_search=(ImageView)findViewById(R.id.img_tool_search);
        img_tool_search_hotspot=(ImageView)findViewById(R.id.img_tool_search_hotspot);
        img_tool_filter=(ImageView)findViewById(R.id.img_tool_filter);

        txt_home=(TextView)findViewById(R.id.txt_home);
        txt_routs=(TextView)findViewById(R.id.txt_routs);
        txt_hotspot=(TextView)findViewById(R.id.txt_hotspot);
        txt_unlock=(TextView)findViewById(R.id.txt_unlock);
        txt_more=(TextView)findViewById(R.id.txt_more);
        txt_tool_title=(TextView)findViewById(R.id.txt_tool_title);

        notification_count  =   (TextView)findViewById(R.id.notification_count);
        notification_layout =   (FrameLayout) findViewById(R.id.notification_layout);

        dbHandler   =   new DbHandler(NavigationActivity.this);
        int totalcount  =   dbHandler.getTotalCount();
        if (totalcount>0){
            notification_count.setText(totalcount+"");
            notification_count.setVisibility(View.VISIBLE);
        }else {
            notification_count.setVisibility(View.GONE);
        }


        layout_home.setOnClickListener(this);
        layout_routs.setOnClickListener(this);
        layout_hotspot.setOnClickListener(this);
        layout_unlock.setOnClickListener(this);
        layout_more.setOnClickListener(this);
        img_tool_search.setOnClickListener(this);
        img_tool_search_hotspot.setOnClickListener(this);
        img_tool_filter.setOnClickListener(this);
        notification_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.layout_home:
                finishAffinity();
                Intent intent=new Intent(NavigationActivity.this,HomeActivity.class);
                intent.putExtra("type","");
                startActivity(intent);
                /*finish();
                HomeActivity.homeActivity.finish();*/

                break;
            case R.id.layout_routs:
                txt_tool_title.setText("All Routes");
                img_tool_filter.setVisibility(View.GONE);
                img_tool_search.setVisibility(View.VISIBLE);
                img_tool_search_hotspot.setVisibility(View.GONE);

                layout_routs.setBackgroundResource(R.mipmap.selected_tab);
                txt_routs.setTextColor(Color.parseColor("#1c4078"));
                img_routs.setImageResource(R.mipmap.select_routs);

                layout_hotspot.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_more.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_unlock.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_home.setBackgroundColor(Color.parseColor("#F0F0F0"));

                txt_hotspot.setTextColor(Color.parseColor("#C3C1C2"));
                txt_more.setTextColor(Color.parseColor("#C3C1C2"));
                txt_unlock.setTextColor(Color.parseColor("#C3C1C2"));
                txt_home.setTextColor(Color.parseColor("#C3C1C2"));

                img_hotspot.setImageResource(R.mipmap.unselect_hotpost);
                img_more.setImageResource(R.mipmap.unselect_more);
                img_unlock.setImageResource(R.mipmap.unselect_unlock);
                img_home.setImageResource(R.mipmap.unselect_home);


                if (dbHandler.getTotalCount()>0){
                    notification_count.setText(dbHandler.getTotalCount()+"");
                    notification_count.setVisibility(View.VISIBLE);
                }else {
                    notification_count.setVisibility(View.GONE);
                }

                fragment=new FragmentAllrouts();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                break;
            case R.id.layout_hotspot:
                txt_tool_title.setText("Hotspots");
                img_tool_filter.setVisibility(View.VISIBLE);
                img_tool_search.setVisibility(View.GONE);
                img_tool_search_hotspot.setVisibility(View.VISIBLE);
                layout_hotspot.setBackgroundResource(R.mipmap.selected_tab);
                txt_hotspot.setTextColor(Color.parseColor("#1c4078"));
                img_hotspot.setImageResource(R.mipmap.select_hotspot);

                layout_routs.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_more.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_unlock.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_home.setBackgroundColor(Color.parseColor("#F0F0F0"));

                txt_routs.setTextColor(Color.parseColor("#C3C1C2"));
                txt_more.setTextColor(Color.parseColor("#C3C1C2"));
                txt_unlock.setTextColor(Color.parseColor("#C3C1C2"));
                txt_home.setTextColor(Color.parseColor("#C3C1C2"));

                img_routs.setImageResource(R.mipmap.unselect_routs);
                img_more.setImageResource(R.mipmap.unselect_more);
                img_unlock.setImageResource(R.mipmap.unselect_unlock);
                img_home.setImageResource(R.mipmap.unselect_home);

                if (dbHandler.getTotalCount()>0){
                    notification_count.setText(dbHandler.getTotalCount()+"");
                    notification_count.setVisibility(View.VISIBLE);
                }else {
                    notification_count.setVisibility(View.GONE);
                }

                Bundle bundle=new Bundle();
                bundle.putString("cat_name","");
                bundle.putString("filtertype","");

                fragment=new FragmentHotspots();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                break;
            case R.id.layout_unlock:
                txt_tool_title.setText("Unlock Features");
                img_tool_filter.setVisibility(View.GONE);
                img_tool_search.setVisibility(View.GONE);
                img_tool_search_hotspot.setVisibility(View.GONE);
                layout_unlock.setBackgroundResource(R.mipmap.selected_tab);
                txt_unlock.setTextColor(Color.parseColor("#1c4078"));

                layout_routs.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_more.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_hotspot.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_home.setBackgroundColor(Color.parseColor("#F0F0F0"));

                txt_routs.setTextColor(Color.parseColor("#C3C1C2"));
                txt_more.setTextColor(Color.parseColor("#C3C1C2"));
                txt_hotspot.setTextColor(Color.parseColor("#C3C1C2"));
                txt_home.setTextColor(Color.parseColor("#C3C1C2"));

                if (dbHandler.getTotalCount()>0){
                    notification_count.setText(dbHandler.getTotalCount()+"");
                    notification_count.setVisibility(View.VISIBLE);
                }else {
                    notification_count.setVisibility(View.GONE);
                }

                img_routs.setImageResource(R.mipmap.unselect_routs);
                img_more.setImageResource(R.mipmap.unselect_more);
                img_hotspot.setImageResource(R.mipmap.unselect_hotpost);
                img_home.setImageResource(R.mipmap.unselect_home);

                img_unlock.setImageResource(R.mipmap.select_unlock);
                fragment=new FragmentUnlock();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                break;
            case R.id.layout_more:
                txt_tool_title.setText("More");
                img_tool_filter.setVisibility(View.GONE);
                img_tool_search.setVisibility(View.GONE);
                img_tool_search_hotspot.setVisibility(View.GONE);
                layout_more.setBackgroundResource(R.mipmap.selected_tab);
                txt_more.setTextColor(Color.parseColor("#1c4078"));
                img_more.setImageResource(R.mipmap.select_more);

                layout_routs.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_unlock.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_hotspot.setBackgroundColor(Color.parseColor("#F0F0F0"));
                layout_home.setBackgroundColor(Color.parseColor("#F0F0F0"));

                txt_routs.setTextColor(Color.parseColor("#C3C1C2"));
                txt_unlock.setTextColor(Color.parseColor("#C3C1C2"));
                txt_hotspot.setTextColor(Color.parseColor("#C3C1C2"));
                txt_home.setTextColor(Color.parseColor("#C3C1C2"));

                if (dbHandler.getTotalCount()>0){
                    notification_count.setText(dbHandler.getTotalCount()+"");
                    notification_count.setVisibility(View.VISIBLE);
                }else {
                    notification_count.setVisibility(View.GONE);
                }

                img_routs.setImageResource(R.mipmap.unselect_routs);
                img_unlock.setImageResource(R.mipmap.unselect_unlock);
                img_hotspot.setImageResource(R.mipmap.unselect_hotpost);
                img_home.setImageResource(R.mipmap.unselect_home);

                fragment=new FragmentMore();
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_container,fragment).commit();
                break;
            case R.id.img_tool_search:
                intent=new Intent(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.img_tool_search_hotspot:
                intent=new Intent(this,SearchHotSpotActivity.class);
                startActivity(intent);
                break;
            case R.id.notification_layout:
//                if(ueserTye.equalsIgnoreCase("Free")) {
                if(preference.getString("notifications","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
                    showdilog();
                }else {
                    intent = new Intent(this, TabActivity.class);
                    intent.putExtra("type", "notification");
                    startActivity(intent);
                }

                break;
            case R.id.img_tool_filter:
                 intent=new Intent(this,TabActivity.class);
                intent.putExtra("type","filter");
                startActivity(intent);
                break;


        }

    }

    private void showdilog() {
        final Dialog dialog = new Dialog(NavigationActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.schedules_dialog);
        dialog.show();
        Button btn_upgrad=(Button)dialog.findViewById(R.id.btn_upgrad);
        Button btn_cancel=(Button)dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_upgrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NavigationActivity.this,NavigationActivity.class);
                intent.putExtra("type","unlock");
                startActivity(intent);
                finish();
            }
        });
    }

    /*private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && this.checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            } else {
                //nextActivity();
            }

        } else {
            //   nextActivity();
        }
    }*/
    private boolean checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(NavigationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result1 == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(NavigationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(NavigationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            ActivityCompat.requestPermissions(NavigationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mGpsStatusDetector.checkGpsStatus();
                } else {
                    finish();
                }

                break;
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGpsStatusDetector.checkOnActivityResult(requestCode, resultCode);
    }

    @Override
    public void onGpsSettingStatus(boolean enabled) {
        Log.d("Gps enabled: ",  enabled+"");
        if (enabled){
            /*Intent serviceIntent = new Intent(this, MyService.class);
            startService(serviceIntent);*/
            Constant.checkGpsAndsaveLocationAddress(NavigationActivity.this);
        }
    }

    @Override
    public void onGpsAlertCanceledByUser() {
        Log.d("Gps cancelled: ",  "");
    }


}
