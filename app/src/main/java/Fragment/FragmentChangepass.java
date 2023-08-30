package Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.HomeActivity;
import com.pinknblue.R;

import Service.Interface;
import Service.ServerResposeRegisteruser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/3/2018.
 */

public class FragmentChangepass extends Fragment {
    EditText edt_oldpass,edt_new_pass,edt_confpass;
    Button btn_save;
    SharedPreferences preferences;
    private String loginUserId="";
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_change_pass,container,false);
        preferences =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preferences.getString("user_id", "");

        initUI(view);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validition()){
                    if (Constant.isNetworkAvailable(getActivity())) {
                        HitchangepassApi();
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }
              /*      Intent intent = new Intent(getActivity(),HomeActivity.class);
                    intent.putExtra("type","login");
                    startActivity(intent);
                    HomeActivity.homeActivity.finish();
                    NavigationActivity.navigationActivity.finish();
                    getActivity().finish();*/
                }
            }
        });
        return view;
    }

    private void HitchangepassApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.change_password(loginUserId,edt_new_pass.getText().toString().trim(),edt_oldpass.getText().toString().trim());

        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data=response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){


//                    showMessage("Password change successfuly");

                    AlertDialog.Builder alertDialogBuilder =   new AlertDialog.Builder(getActivity(),R.style.DialogStyle);
//                    android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this,R.style.DialogStyle);
                  /*  alertDialogBuilder.setTitle(getResources().getString(R.string.app_name));
                    alertDialogBuilder.setIcon(getResources().getDrawable(R.mipmap.app_icon));*/

                    // set dialog message
                    alertDialogBuilder
                            .setMessage("Password changed")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    getActivity().finishAffinity();
                                    Intent intent = new Intent(getActivity(),HomeActivity.class);
//                    intent.putExtra("type","login");
                                    intent.putExtra("type","");
                                    startActivity(intent);
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                    /*editor = preferences.edit();
                    editor.clear();
                    editor.commit();*/

                }else {
                    showMessage(data.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                  dialog.dismiss();
                  showMessage("Try Again");
            }
        });
    }

    private boolean Validition() {
        if(edt_oldpass.getText().toString().trim().isEmpty()){
            showMessage("Enter current password");
            return false;
        }else if(edt_new_pass.getText().toString().trim().isEmpty()){
            showMessage("Enter new password");
            return false;
        }else if(edt_confpass.getText().toString().trim().isEmpty()){
            showMessage("Enter Confirm new password");
            return false;
        }else if(!edt_confpass.getText().toString().trim().equalsIgnoreCase(edt_new_pass.getText().toString().trim())){
            showMessage("New password and confirm new password entered do not match");
            return false;
        }else if (edt_new_pass.getText().toString().trim().length()<6) {
//            showMessage("New password must be atleast 6 alphanumeric characters");
            showMessage("Your password should be at least 6 digits long");
            return false;
        }
        return true;
    }

    private void initUI(View view) {
        btn_save=(Button)view.findViewById(R.id.btn_save);
        edt_oldpass=(EditText)view.findViewById(R.id.edt_oldpass);
        edt_new_pass=(EditText)view.findViewById(R.id.edt_new_pass);
        edt_confpass=(EditText)view.findViewById(R.id.edt_confpass);
    }
    private void showMessage(String s) {

        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
}
