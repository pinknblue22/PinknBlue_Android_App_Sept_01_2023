package com.pinknblue;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Fragment.FragmentSchedules;
import Fragment.FragmentBusinsess;
import Fragment.FragmentFilter;
import Fragment.FragmentNotification;
import Fragment.FragmentProfile;
import Fragment.FragmentTerms;
import Fragment.FragmentPrivacy;
import Fragment.FragmentContactus;
import Fragment.FragmentRates;
import Fragment.FragmentFaq;
import Fragment.FargmentMap;
import Fragment.FargmentNearByMap;
import Fragment.AcceptFragment;
import Fragment.FragmentThankyou;
import uitls.Constant;

public class TabActivity extends AppCompatActivity implements View.OnClickListener {
    Drawable upArrow;
    Toolbar toolbar;
    TextView txt_tool_title;
    public static ImageView img_tool_search;
    Fragment fragment;
    String type, b_id, name, route_id, schedule_id,route_insert_id,from,order_id,message;
    Integer position;
    Bundle bundle;
    private String bussiness_name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        upArrow = ContextCompat.getDrawable(this, R.mipmap.back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bussiness_name = getIntent().getStringExtra("bussiness_name");
        type = getIntent().getStringExtra("type");
        b_id = getIntent().getStringExtra("b_id");
        name = getIntent().getStringExtra("name");
        route_id = getIntent().getStringExtra("route_id");
        order_id = getIntent().getStringExtra("order_id");
        message = getIntent().getStringExtra("message");
        route_insert_id = getIntent().getStringExtra("route_insert_id");
        schedule_id = getIntent().getStringExtra("schedule_id");
        from = getIntent().getStringExtra("from");
        position = getIntent().getIntExtra("position",0);

        initUI();
        bundle = new Bundle();
        switch (type) {
            case "shedules":
                txt_tool_title.setText("Schedule");
                img_tool_search.setVisibility(View.VISIBLE);
                bundle.putString("route_id", route_id);
                bundle.putString("route_insert_id", route_insert_id);
                fragment = new FragmentSchedules();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
          /*  case "schedule_details":
                txt_tool_title.setText("Schedule Detail");
                img_tool_search.setVisibility(View.INVISIBLE);
                bundle.putString("schedule_id", schedule_id);
                fragment = new FragmentSchedulesDetails();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;*/
            case "bussines":
                txt_tool_title.setText(bussiness_name);
                // txt_tool_title.setText("Business name");
                img_tool_search.setVisibility(View.GONE);
                bundle.putString("b_id", b_id);
                fragment = new FragmentBusinsess();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "filter":
                txt_tool_title.setText("Filter Hotspots");
                img_tool_search.setVisibility(View.VISIBLE);
                img_tool_search.setImageResource(R.mipmap.unselect_all);
                fragment = new FragmentFilter();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "notification":
                txt_tool_title.setText("Notifications");
                img_tool_search.setVisibility(View.GONE);
                img_tool_search.setImageResource(R.mipmap.clear_all);
                fragment = new FragmentNotification();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;



            case "profile":
                txt_tool_title.setText("Profile");
                img_tool_search.setVisibility(View.GONE);
                fragment = new FragmentProfile();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "term":
                txt_tool_title.setText("Terms & Conditions");
                img_tool_search.setVisibility(View.GONE);
                fragment = new FragmentTerms();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "privacy":
                txt_tool_title.setText("Privacy Policy");
                img_tool_search.setVisibility(View.GONE);
                fragment = new FragmentPrivacy();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "contact":
                txt_tool_title.setText("Contact us");
                img_tool_search.setVisibility(View.GONE);
                fragment = new FragmentContactus();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "rates":
                txt_tool_title.setText("Rate us");
                img_tool_search.setVisibility(View.GONE);
                fragment = new FragmentRates();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "faqs":
                txt_tool_title.setText("FAQs");
                img_tool_search.setVisibility(View.GONE);
                fragment = new FragmentFaq();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;


        /*    case "change_pass":
                txt_tool_title.setText("Change password");
                img_tool_search.setVisibility(View.INVISIBLE);
                fragment = new FragmentChangepass();
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;*/
          /*  case "edite_pro":
                txt_tool_title.setText("Edit profile");
                img_tool_search.setVisibility(View.INVISIBLE);
                bundle.putString("name", name);
                fragment = new FragmentEditeProfile();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;*/
            case "map":
                txt_tool_title.setText("View route");
                img_tool_search.setVisibility(View.GONE);
                bundle.putString("schedule_id", schedule_id);
//                if (bundle.containsKey("from")){
                    bundle.putInt("position",position);
                    bundle.putString("from",from);
//                }

                fragment = new FargmentMap();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "mapnearby":
                txt_tool_title.setText("Nearby");
                img_tool_search.setVisibility(View.GONE);
                bundle.putString("route_id", route_id);
//                if (bundle.containsKey("from")){
                    bundle.putInt("position",position);
                    bundle.putString("from",from);
//                }

                fragment = new FargmentNearByMap();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;

            case "acceptPayment":
                txt_tool_title.setText("Enter your card details");
                img_tool_search.setVisibility(View.GONE);
                bundle.putString("order_id", order_id);
                fragment = new AcceptFragment();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
            case "thankyou":
                txt_tool_title.setText("Confirmation");
                img_tool_search.setVisibility(View.VISIBLE);
                bundle.putString("message", message);
                fragment = new FragmentThankyou();
                fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
                break;
        }
    }

    private void initUI() {
        txt_tool_title = (TextView) findViewById(R.id.txt_tool_title);
        img_tool_search = (ImageView) findViewById(R.id.img_tool_search);
        img_tool_search.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {

        if (type.equalsIgnoreCase("shedules")){
            switch (v.getId()){
                case R.id.img_tool_search:
                    Intent intent  =    new Intent(this,SearchActivity.class);
                    startActivity(intent);
                    break;
            }
        }


        if (type.equalsIgnoreCase("notification")) {


        } else if (type.equalsIgnoreCase("filter")) {
            FragmentFilter.categoryname.clear();
            if (Constant.selectall.equalsIgnoreCase("false")) {
                Constant.selectall = "true";
                img_tool_search.setImageResource(R.mipmap.select_all);

            } else {
                Constant.selectall = "false";
                img_tool_search.setImageResource(R.mipmap.unselect_all);
            }

            fragment = new FragmentFilter();
            getSupportFragmentManager().beginTransaction().replace(R.id.tab_container, fragment).commit();
        }
    }
}
