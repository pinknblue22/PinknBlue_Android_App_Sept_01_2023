package Fragment;

import android.app.AlertDialog;
import android.content.Context;
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

public class FragmentContactus extends Fragment {
    EditText edt_massage;
    Button btn_send;
    SharedPreferences preference;
    private String loginUserId="";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contactus,container,false);
        preference =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preference.getString("user_id", "");
        initUI(view);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_massage.getText().toString().trim().isEmpty()){
                    showMessage("Enter your message");
                }else {
                    if (Constant.isNetworkAvailable(getActivity())) {
                        hitApi(edt_massage.getText().toString().trim());
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }
                }
            }
        });
        return view;
    }

    private void hitApi(String msg) {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
         dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.ContactUs(loginUserId,msg);

         call.enqueue(new Callback<ServerResposeRegisteruser>() {
             @Override
             public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                 ServerResposeRegisteruser data=response.body();
                 dialog.dismiss();
                 if(data.getCode().equalsIgnoreCase("201")){
                     showMessage("Thank you for contacting us. \nSomeone will be in touch with you soon.");
                     getActivity().finish();
                 }else {
                     showMessage(data.getMessage());
                 }
             }

             @Override
             public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                 showMessage("Try again");
                 dialog.dismiss();
             }
         });
    }

    private void initUI(View view) {
        btn_send=(Button)view.findViewById(R.id.btn_send);
        edt_massage=(EditText)view.findViewById(R.id.edt_massage);
    }

    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
