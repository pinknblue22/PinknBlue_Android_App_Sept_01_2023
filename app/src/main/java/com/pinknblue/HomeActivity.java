package com.pinknblue;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageButton;

import  Fragment.LoginFragment;
import uitls.Constant;
import uitls.DbHandler;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    String type;
    Fragment fragment;
    Intent intent;
    ImageButton btn_hotspot,btn_routs,btn_more,btn_unlock;
    public static HomeActivity homeActivity;
    SharedPreferences preferences;
    DbHandler dbHandler;
    String ueserTye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeActivity=this;
        setContentView(R.layout.activity_home);


        Constant.checkGpsAndsaveLocationAddress(HomeActivity.this);

        preferences =   getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        ueserTye    =   preferences.getString("user_type","");

        intUI();

//        if(ueserTye.equalsIgnoreCase("Paid")) {
        if(preferences.getString("notifications","").equalsIgnoreCase("0") &&  preferences.getString("featurebundle","").equalsIgnoreCase("0")) {
            dbHandler = new DbHandler(HomeActivity.this);
            int totalcount = dbHandler.getTotalCount();
            if (totalcount > 0) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(HomeActivity.this);
                dialog.setMessage("You have new notifications, Please press ok to view.");
                dialog.setCancelable(true);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(HomeActivity.this, TabActivity.class);
                        intent.putExtra("type", "notification");
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("cancel", null);
                dialog.show();
            }
        }


        type=getIntent().getStringExtra("type");
        if(type.equalsIgnoreCase("login")){
            fragment=new LoginFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
          //  showMsg(getApplication(),"I won....burraaaaa");

        }else {

        }

    }

   /* private void showMsg(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
*/
    private void intUI() {
        btn_hotspot=(ImageButton)findViewById(R.id.btn_hotspot);
        btn_routs=(ImageButton)findViewById(R.id.btn_routs);
        btn_more=(ImageButton)findViewById(R.id.btn_more);
        btn_unlock=(ImageButton)findViewById(R.id.btn_unlock);

        btn_hotspot.setOnClickListener(this);
        btn_routs.setOnClickListener(this);
        btn_more.setOnClickListener(this);
        btn_unlock.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_hotspot:
                intent=new Intent(this,NavigationActivity.class);
                intent.putExtra("type","hotspot");
                intent.putExtra("cat_name","");
                intent.putExtra("filtertype","");
                startActivity(intent);
                break;
            case   R.id.btn_routs:
                intent=new Intent(this,NavigationActivity.class);
                intent.putExtra("type","routs");
                startActivity(intent);
                 break;
            case   R.id.btn_more:
                intent=new Intent(this,NavigationActivity.class);
                intent.putExtra("type","more");
                startActivity(intent);
                break;
            case   R.id.btn_unlock:
                intent=new Intent(this,NavigationActivity.class);
                intent.putExtra("type","unlock");
                startActivity(intent);
                break;
        }
    }

}
