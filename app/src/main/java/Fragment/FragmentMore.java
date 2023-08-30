package Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.pinknblue.HomeActivity;
import com.pinknblue.NavigationActivity;
import com.pinknblue.R;
import com.pinknblue.TabActivity;

import Service.Interface;
import Service.ServerResposeRegisteruser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/1/2018.
 */

public class FragmentMore extends Fragment implements View.OnClickListener {
    TextView txt_notification,txt_profile,txt_term,txt_privacy,txt_contact,txt_rates,txt_faq;
    Button btn_logout;
    Intent intent;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String userid,ueserTye;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_more,container,false);
        preferences =   getActivity().getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        userid      =   preferences.getString("user_id","");
        ueserTye    =   preferences.getString("user_type","");
        Log.d("purchased",preferences.getString("notifications",""));
        Log.d("purchased",preferences.getString("routedetails",""));
        Log.d("purchased",preferences.getString("map",""));
        Log.d("purchased",preferences.getString("featurebundle",""));
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        txt_notification=(TextView)view.findViewById(R.id.txt_notification);
        txt_profile=(TextView)view.findViewById(R.id.txt_profile);
        txt_term=(TextView)view.findViewById(R.id.txt_term);
        txt_privacy=(TextView)view.findViewById(R.id.txt_privacy);
        txt_contact=(TextView)view.findViewById(R.id.txt_contact);
        txt_rates=(TextView)view.findViewById(R.id.txt_rates);
        txt_faq=(TextView)view.findViewById(R.id.txt_faq);
        btn_logout=(Button)view.findViewById(R.id.btn_logout);

        txt_notification.setOnClickListener(this);
        txt_profile.setOnClickListener(this);
        txt_term.setOnClickListener(this);
        txt_privacy.setOnClickListener(this);
        txt_contact.setOnClickListener(this);
        txt_rates.setOnClickListener(this);
        txt_faq.setOnClickListener(this);
        btn_logout.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
               logoutDialog();

            /*    intent=new Intent(getActivity(), HomeActivity.class);
                intent.putExtra("type","login");
                startActivity(intent);
                getActivity().finish();
                HomeActivity.homeActivity.finish();*/
                break;
            case R.id.txt_notification:
//                if(ueserTye.equalsIgnoreCase("Free")) {
                if(preferences.getString("notifications","").equalsIgnoreCase("0") &&  preferences.getString("featurebundle","").equalsIgnoreCase("0")) {
                    showdilog();
                }else {
                    intent = new Intent(getActivity(), TabActivity.class);
                    intent.putExtra("type", "notification");
                    startActivity(intent);
                }

                break;
            case R.id.txt_profile:
                intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","profile");
                startActivity(intent);
                break;
            case R.id.txt_term:
                intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","term");
                startActivity(intent);
                break;
            case R.id.txt_privacy:
                intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","privacy");
                startActivity(intent);
                break;
            case R.id.txt_contact:
                intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","contact");
                startActivity(intent);
                break;
            case R.id.txt_rates:
                intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","rates");
                startActivity(intent);
                break;
            case R.id.txt_faq:
                intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","faqs");
                startActivity(intent);
                break;
        }
    }

    private void logoutDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
        dialog.setMessage("Do you want to logout?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hitlogout();
            }
        });
        dialog.setNegativeButton("cancel", null);
        dialog.show();

    }

    private void hitlogout() {
        final android.app.AlertDialog dialog = new SpotsDialog(getActivity(),"Wait...");
        dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.logout(userid);
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data=response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
                    showMessage(data.getMessage());
                    editor = preferences.edit();
                    editor.clear();
                    editor.commit();

                    intent=new Intent(getActivity(), HomeActivity.class);
                    intent.putExtra("type","login");
                    startActivity(intent);
                    getActivity().finish();
                    HomeActivity.homeActivity.finish();

                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    private void showdilog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.schedules_dialog);
        dialog.show();
        Button btn_upgrad=(Button)dialog.findViewById(R.id.btn_upgrad);
        Button btn_cancel=(Button)dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_upgrad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NavigationActivity.class);
                intent.putExtra("type","unlock");
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
