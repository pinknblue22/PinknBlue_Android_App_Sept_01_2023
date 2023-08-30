package com.pinknblue;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.widget.TextView;


import Fragment.FragmentNotificationDetails;
import Fragment.FragmentSchedulesDetails;
import Fragment.FragmentEditeProfile;
import Fragment.FragmentChangepass;
import Fragment.FragmentHotspotMap;
import uitls.Constant;

public class CommanActivity extends AppCompatActivity {
    Drawable upArrow;
    Toolbar toolbar;
    TextView txt_tool_title;
    String type,schedule_id,name,address,route_insert_id,latitude,longitude;
    Bundle bundle;
    Fragment fragment;
    String from =   "test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comman);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Constant.checkGpsAndsaveLocationAddress(CommanActivity.this);

        upArrow = ContextCompat.getDrawable(this, R.mipmap.back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_tool_title=(TextView)findViewById(R.id.txt_tool_title);

        type = getIntent().getStringExtra("type");
        from = getIntent().getStringExtra("from");
        schedule_id = getIntent().getStringExtra("schedule_id");
        route_insert_id = getIntent().getStringExtra("route_insert_id");
        name = getIntent().getStringExtra("name");
        address=getIntent().getStringExtra("hotaddress");
        latitude=getIntent().getStringExtra("latitude");
        longitude=getIntent().getStringExtra("longitude");


        bundle = new Bundle();
        switch (type){
             case "schedule_details":
                txt_tool_title.setText("Schedule Detail");
                bundle.putString("schedule_id", schedule_id);
                bundle.putString("route_insert_id", route_insert_id);
                fragment = new FragmentSchedulesDetails();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.cotainer, fragment).commit();
                break;
            case "edite_pro":
                txt_tool_title.setText("Edit profile");
                bundle.putString("name", name);
                fragment = new FragmentEditeProfile();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.cotainer, fragment).commit();
                break;
            case "change_pass":
                txt_tool_title.setText("Change password");
                fragment = new FragmentChangepass();
                getSupportFragmentManager().beginTransaction().replace(R.id.cotainer, fragment).commit();
                break;
            case "notification_detail":
                txt_tool_title.setText("Notification Details");
                bundle.putString("message", getIntent().getStringExtra("message"));
                bundle.putString("time", getIntent().getStringExtra("time"));
                fragment = new FragmentNotificationDetails();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.cotainer, fragment).commit();
                break;
            case "viewmap":
                txt_tool_title.setText("View map");
                bundle.putString("address",address);
                bundle.putString("latitude",latitude);
                bundle.putString("longitude",longitude);
                fragment = new FragmentHotspotMap();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.cotainer, fragment).commit();
                break;
        }

    }
    public void onBackPressed() {
        super.onBackPressed();
        /*if (from.equalsIgnoreCase("pushnotifiation")) {
            Intent intent = new Intent(CommanActivity.this,HomeActivity.class);
            intent.putExtra("type","");
            startActivity(intent);
            finish();
        }*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
