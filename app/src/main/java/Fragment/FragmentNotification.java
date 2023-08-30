package Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.R;
import com.pinknblue.TabActivity;
import java.util.ArrayList;
import Adapter.RVNotificationAdapter;
import Service.Interface;
import Service.ServerResponsefetchNotification;
import Service.ServerResposeRegisteruser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;
import uitls.DbHandler;

/**
 * Created by eweb-a1-pc-14 on 1/3/2018.
 */

public class FragmentNotification extends Fragment {
    RecyclerView rv_notification;
    SharedPreferences preference;
    private String loginUserId="";
    ArrayList<ServerResponsefetchNotification.Data> notificationlist;
    DbHandler dbHandler;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification,container,false);
        preference =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preference.getString("user_id", "");

        dbHandler   =   new DbHandler(getActivity());
        dbHandler.deleteAllRecords();

       /* MyApplication.getSharedPreferences().edit()
                .putString("noti_count", "").commit();*/



        initUI(view);
        rv_notification.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (Constant.isNetworkAvailable(getActivity())) {
            hitApi();
        }else {
            showMessage("Please check your internet connection and try again");
        }

        TabActivity.img_tool_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showMessage("click delete");
                HitDeletApi();
            }
        });

        return view;
    }

    private void HitDeletApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call= service.DeleteNotification(loginUserId);
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data=response.body();
                dialog.dismiss();
                if (data.getCode().equalsIgnoreCase("201")){
//                    showMessage(data.getMessage());
                    notificationlist.clear();
                    rv_notification.setAdapter(new RVNotificationAdapter(getActivity(),notificationlist));
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

    private void hitApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);

        Call<ServerResponsefetchNotification> call= service.fechNotification(loginUserId);
        call.enqueue(new Callback<ServerResponsefetchNotification>() {
            @Override
            public void onResponse(Call<ServerResponsefetchNotification> call, Response<ServerResponsefetchNotification> response) {
                ServerResponsefetchNotification data=response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
                    notificationlist=new ArrayList<>();
                    notificationlist.clear();
//                    showMessage(data.getMessage());

                    for( int idx=0;idx<data.getDatalist().size();idx++){
                        notificationlist.add(data.getDatalist().get(idx));
                    }
                    rv_notification.setAdapter(new RVNotificationAdapter(getActivity(),notificationlist));

                }else{
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponsefetchNotification> call, Throwable t) {
                 dialog.dismiss();
                showMessage("Try again");
            }
        });

    }

    private void initUI(View view) {
        rv_notification=(RecyclerView)view.findViewById(R.id.rv_notification);
    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
