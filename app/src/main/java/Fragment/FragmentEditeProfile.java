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
import android.widget.TextView;
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

public class FragmentEditeProfile extends Fragment {
    EditText edt_name;
    Button btn_save;
    TextView txt_email;
    SharedPreferences preference;
    private String loginUserId="",user_name,email;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_edit_profile,container,false);
        preference =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preference.getString("user_id", "");
        email=preference.getString("email", "");

        user_name=getArguments().getString("name");

        initUI(view);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edt_name.getText().toString().trim().isEmpty()){
                    showMessage("Enter the name");
                }else {
                      HitEditProfile();
                }
            }
        });
        return view;
    }

    private void HitEditProfile() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);

        Call<ServerResposeRegisteruser> call=service.edit_profile(loginUserId,edt_name.getText().toString().trim());
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data=response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
                     showMessage(data.getMessage());
                     getActivity().finish();
                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {

            }
        });

    }

    private void initUI(View view) {
        btn_save=(Button)view.findViewById(R.id.btn_save);
        edt_name=(EditText)view.findViewById(R.id.edt_name);
        txt_email=(TextView)view.findViewById(R.id.txt_email);
        edt_name.setText(user_name);
        txt_email.setText(email);
    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
