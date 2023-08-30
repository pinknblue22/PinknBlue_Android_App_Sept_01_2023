package Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.ForgotPassActivity;
import com.pinknblue.HomeActivity;
import com.pinknblue.R;


import Service.Interface;
import Service.ServerResponseLoginUser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 12/29/2017.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    Button btn_signin;
    TextView txt_register,txt_forgotpass;
    EditText edt_email,edt_pass;
    SharedPreferences preferences,preference;
    SharedPreferences.Editor editor;
    public  String email, password,deviceType="1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_login,container,false);
        preferences = getActivity().getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);

        preference = getActivity().getSharedPreferences("device",Context.MODE_PRIVATE);
        Constant.FCMID = preference.getString("deviceId", " ");
        Log.e("bbbb", "=======" + Constant.FCMID);
        intUI(view);
        hideKeyBoard();

        return view;
    }

    private void intUI(View view) {
        txt_register=(TextView)view.findViewById(R.id.txt_register);
        txt_forgotpass=(TextView)view.findViewById(R.id.txt_forgotpass);
        btn_signin=(Button)view.findViewById(R.id.btn_signin);
        edt_email=(EditText)view.findViewById(R.id.edt_email);
        edt_pass=(EditText)view.findViewById(R.id.edt_pass);

        txt_register.setOnClickListener(this);
        btn_signin.setOnClickListener(this);
        txt_forgotpass.setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        edt_email.setText("");
        edt_pass.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_register:
                Fragment fragment=new RegisterFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).addToBackStack("").commit();
                break;

            case R.id.btn_signin:
                hideKeyBoard();
                if (Validation()){
                    if (Constant.isNetworkAvailable(getActivity())) {
                        hitloginApi();
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }
                }

               /* Intent intent=new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("type","");
                startActivity(intent);
                getActivity().finish();
*/
             // getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.txt_forgotpass:
                Intent intent1=new Intent(getActivity(),ForgotPassActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void hitloginApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();

        Interface service= ApiClient.getClient().create(Interface.class);
//        Call<ServerResponseLoginUser> call =service.login(email,password,Constant.FCMID,deviceType);
        Call<ServerResponseLoginUser> call =service.login(email,password,preference.getString("deviceId", " "),deviceType);
        call.enqueue(new Callback<ServerResponseLoginUser>() {
            @Override
            public void onResponse(Call<ServerResponseLoginUser> call, Response<ServerResponseLoginUser> response) {
                ServerResponseLoginUser data=response.body();
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

                    Intent intent=new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("type","");
                    startActivity(intent);
                    getActivity().finish();
                    showMessage(data.getMessage());
                }else {
                    if(data.getMessage().equalsIgnoreCase("User Account Is Locked"))
                        showMessage("Please check your inbox to verify your details so you may log in");
                    if(data.getMessage().equalsIgnoreCase("You are already logged in on another device"))
                        showMessage(data.getMessage());
                    else
                        showMessage("Email or password do not match");
                }
            }

            @Override
            public void onFailure(Call<ServerResponseLoginUser> call, Throwable t) {
                   dialog.dismiss();
                   showMessage("Try again");
            }
        });
    }

    private boolean Validation() {
        email=edt_email.getText().toString().trim();
        password=edt_pass.getText().toString().trim();
        if(email.isEmpty()){
            showMessage("Enter email address");
            return false;
        }else if(password.isEmpty()){
            showMessage("Enter password ");
            return false;
        }
        return true;
    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    public void hideKeyBoard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
