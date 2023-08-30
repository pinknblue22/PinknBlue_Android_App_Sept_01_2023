package Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.CommanActivity;
import com.pinknblue.R;


import Service.Interface;
import Service.ServerResponseFetchprofile;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/3/2018.
 */

public class FragmentProfile extends Fragment implements View.OnClickListener {
    Button btn_edit_pro,btn_chang_pass;
    TextView txt_username,txt_email;
    Intent intent;
    SharedPreferences preference;
    private String loginUserId="",name;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        preference =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preference.getString("user_id", "");

        initUI(view);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Constant.isNetworkAvailable(getActivity())) {
            HitFeachProfileApi();
        }else {
            showMessage("Please check your internet connection and try again");
        }

    }

    private void HitFeachProfileApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();

        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseFetchprofile> call=service.user_profile(loginUserId);
        call.enqueue(new Callback<ServerResponseFetchprofile>() {
            @Override
            public void onResponse(Call<ServerResponseFetchprofile> call, Response<ServerResponseFetchprofile> response) {
                ServerResponseFetchprofile data= response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
                  //  showMessage("User Profile successfully");
                    name=data.getFirst_name();
                    txt_username.setText(data.getFirst_name());
                    txt_email.setText(data.getEmail());
                }else {
                       showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseFetchprofile> call, Throwable t) {
                   dialog.dismiss();
                   showMessage("Try again");
            }
        });

    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void initUI(View view) {
        btn_chang_pass=(Button)view.findViewById(R.id.btn_chang_pass);
        btn_edit_pro=(Button)view.findViewById(R.id.btn_edit_pro);
        txt_username=(TextView)view.findViewById(R.id.txt_username);
        txt_email=(TextView)view.findViewById(R.id.txt_email);
        btn_chang_pass.setOnClickListener(this);
        btn_edit_pro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chang_pass:
                intent=new Intent(getActivity(), CommanActivity.class);
                intent.putExtra("type","change_pass");
                startActivity(intent);
                break;
            case R.id.btn_edit_pro:
                intent=new Intent(getActivity(), CommanActivity.class);
                intent.putExtra("type","edite_pro");
                intent.putExtra("name",name);
                startActivity(intent);

                break;
        }
    }
}
