package com.pinknblue;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;



import Service.Interface;
import Service.ServerResponseForgotpass;
import Service.ServerResposeRegisteruser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

public class ForgotPassActivity extends AppCompatActivity implements View.OnClickListener {
    Drawable upArrow;
    Toolbar toolbar;
    LinearLayout layout_forgot,layout_reset;
    Button btn_forgot,btn_reset;
    EditText edt_email,edt_otp,edt_new_pass,edt_confpass;
    String email,new_pass,otp,confpass;
    SharedPreferences preferences,preference;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_forgot_pass);

        preferences = getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);

         toolbar = (Toolbar) findViewById(R.id.toolbar);
         toolbar.setTitle("");
         setSupportActionBar(toolbar);

        upArrow = ContextCompat.getDrawable(this, R.mipmap.back_arrow);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initUi();

    }

    private void initUi() {
        layout_forgot=(LinearLayout)findViewById(R.id.layout_forgot);
        layout_reset=(LinearLayout)findViewById(R.id.layout_reset);
        btn_forgot=(Button)findViewById(R.id.btn_forgot);
        btn_reset=(Button)findViewById(R.id.btn_reset);
        edt_email=(EditText)findViewById(R.id.edt_email);
        edt_otp=(EditText)findViewById(R.id.edt_otp);
        edt_new_pass=(EditText)findViewById(R.id.edt_new_pass);
        edt_confpass=(EditText)findViewById(R.id.edt_confpass);

        btn_forgot.setOnClickListener(this);
        btn_reset.setOnClickListener(this);
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
        switch (v.getId()){
            case R.id.btn_forgot:

                if (for_Validtion()){
                    if (Constant.isNetworkAvailable(getApplicationContext())) {
                        HitForgotApi();
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }
                }

              /*  layout_reset.setVisibility(View.VISIBLE);
                layout_forgot.setVisibility(View.GONE);*/
                break;
            case R.id.btn_reset:
                   if (Validation()){
                       if (Constant.isNetworkAvailable(getApplicationContext())) {
                            HitresetApi();
                       }else {
                           showMessage("Please check your internet connection and try again");
                       }
                   }
                break;
        }
    }

    private void HitresetApi() {
        final AlertDialog dialog = new SpotsDialog(this, "Wait...");
        dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.reset_password(otp,new_pass,email);
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data=response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){


                    editor = preferences.edit();
                    editor.putString("user_id", data.getUser_id());
                    editor.putString("name", data.getName());
                    editor.putString("email", data.getEmail());
                    editor.putString("user_type", data.getUser_type());
                    editor.putString("device_id", data.getDevice_id());
                    editor.putString("device_type", data.getDevice_type());
                    editor.putString("routedetails", data.getRoutedetails());
                    editor.putString("map", data.getMap());
                    editor.putString("notifications", data.getNotifications());
                    editor.putString("featurebundle", data.getFeaturebundle());
                    editor.commit();


                    /*Intent intent = new Intent(ForgotPassActivity.this,HomeActivity.class);
                    intent.putExtra("type","login");
                    startActivity(intent);
                    finish();*/

                    Intent intent=new Intent(ForgotPassActivity.this, HomeActivity.class);
                    intent.putExtra("type","");
                    startActivity(intent);
                    finish();

                    showMessage("Password changed");

                }else {
                    if(data.getMessage().equalsIgnoreCase("not able to update password")){
                        showMessage("Please enter correct temporary password");
                    }else {
                        showMessage(data.getMessage());
                    }

                }
            }

            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

    private void HitForgotApi() {
        final AlertDialog dialog = new SpotsDialog(this, "Wait...");
        dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResponseForgotpass> call=service.ForgetPassword(email);

        call.enqueue(new Callback<ServerResponseForgotpass>() {
            @Override
            public void onResponse(Call<ServerResponseForgotpass> call, Response<ServerResponseForgotpass> response) {
                ServerResponseForgotpass data=response.body();
                dialog.dismiss();
                if (data.getCode().equalsIgnoreCase("201")){
                    showMessage(data.getMessage());
                    layout_reset.setVisibility(View.VISIBLE);
                    layout_forgot.setVisibility(View.GONE);
                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseForgotpass> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try again");
            }
        });
    }

    private boolean Validation() {
        otp=edt_otp.getText().toString().trim();
        new_pass=edt_new_pass.getText().toString().trim();
        confpass=edt_confpass.getText().toString().trim();
        if(otp.isEmpty()){
            showMessage("Enter Temporary password");
            return false;
        }else if (new_pass.isEmpty()){
            showMessage("Enter new password");
            return false;
        }else if(confpass.isEmpty()){
            showMessage("Enter confirm new password");
            return false;
        }else if(!confpass.equalsIgnoreCase(new_pass)){
            showMessage("New/Confirm new passwords entered do not match");
            return false;
        } else if (new_pass.length()<6) {
            showMessage("Password must be at least 6 characters");
            return false;
        }
        return true;
    }

    private boolean for_Validtion() {
        email=edt_email.getText().toString().trim();
        if(email.isEmpty()){
            showMessage("Enter email address");
            return false;
        }else if(!isValidEmail(email)){
            showMessage("Enter valid email address");
            return false;
        }
        return true;
    }

    private void showMessage(String s) {
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getApplicationContext(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    // email validation
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}
