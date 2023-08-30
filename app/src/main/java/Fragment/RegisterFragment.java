package Fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
 * Created by eweb-a1-pc-14 on 12/29/2017.
 */

public class RegisterFragment extends Fragment implements View.OnClickListener {
    Button btn_register;
    TextView txt_login;
    EditText edt_name,edt_email,edt_pass,edt_confpass;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String name,email,password,deviceType="1";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.fragment_register,container,false);
          intUI(view);
        return view;
    }

    private void intUI(View view) {
        btn_register=(Button)view.findViewById(R.id.btn_register);
        txt_login=(TextView)view.findViewById(R.id.txt_login);
        edt_name=(EditText)view.findViewById(R.id.edt_name);
        edt_email=(EditText)view.findViewById(R.id.edt_email);
        edt_pass=(EditText)view.findViewById(R.id.edt_pass);
        edt_confpass=(EditText)view.findViewById(R.id.edt_confpass);

        btn_register.setOnClickListener(this);
        txt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                if(validation()){

                    if (Constant.isNetworkAvailable(getActivity())) {
                        HitRisterApi();
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }

                }
                //getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;
            case R.id.txt_login:
                getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                break;

        }
    }

    private boolean validation() {
          name=edt_name.getText().toString().trim();
          email=edt_email.getText().toString().trim();
          password=edt_pass.getText().toString().trim();

        if(edt_name.getText().toString().trim().isEmpty()){
            showMessage("Please enter the name");
          return false;
        }else if(edt_email.getText().toString().trim().isEmpty()){
            showMessage("Please enter the email address");
            return false;
        }else  if(!isValidEmail(email)){
            showMessage("Please enter a valid email address");
            return false;
        }else if(edt_pass.getText().toString().trim().isEmpty()){
            showMessage("Please enter the password");
            return false;
        }else if(edt_confpass.getText().toString().trim().isEmpty()){
            showMessage("Please enter the confirm password");
            return false;
        } else if (!(edt_confpass.getText().toString().trim()).equalsIgnoreCase(edt_pass.getText().toString().trim())) {
            showMessage("Passwords do not match");
            return false;
        }else if (edt_pass.getText().toString().trim().length()<6) {
            showMessage("Password must be at least 6 characters");
            return false;
        }

        return true;
    }

    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    // email validation
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    private void HitRisterApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.register(name,email,password,Constant.FCMID,deviceType);
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data = response.body();
                dialog.dismiss();
                if (data.getCode().equalsIgnoreCase("201")){
                    showMessage("Registration successful!\nPlease check your inbox to verify your details before logging in");
                    getActivity().getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try again");
            }
        });


    }
}
