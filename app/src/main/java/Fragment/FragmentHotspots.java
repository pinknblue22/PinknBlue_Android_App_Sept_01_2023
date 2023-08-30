package Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.google.gson.Gson;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.R;
import com.pinknblue.TabActivity;


import java.util.ArrayList;

import Adapter.RVHotspotAdapter;
import Service.Interface;
import Service.ServerResponseHotSpot;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/1/2018.
 */

public class FragmentHotspots extends Fragment {
    RecyclerView rv_hotspot;
    ArrayList<ServerResponseHotSpot.Data> hotspotlist;
    public String cat_name="",filter_type;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_hotspots,container,false);
        cat_name    =   getArguments().getString("cat_name");
        filter_type =   getArguments().getString("filtertype");

        rv_hotspot=(RecyclerView)view.findViewById(R.id.rv_hotspot);
        rv_hotspot.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(filter_type.equalsIgnoreCase("")){
            if (Constant.isNetworkAvailable(getActivity())) {
                HithotspotApi();
            }else {
                showMessage("Please check your internet connection and try again");
            }
        }else {
            if (cat_name!="") {
                if (Constant.isNetworkAvailable(getActivity())) {
                  hitapplyApi(cat_name);
                }else {
                    showMessage("Please check your internet connection and try again");
                }
            }else {
                    if (Constant.isNetworkAvailable(getActivity())) {
                        HithotspotApi();
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }
            }
        }
    }

    private void HithotspotApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();

        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseHotSpot> call= service.hotspot_list();
        call.enqueue(new Callback<ServerResponseHotSpot>() {
            @Override
            public void onResponse(Call<ServerResponseHotSpot> call, Response<ServerResponseHotSpot> response) {
                ServerResponseHotSpot data=response.body();
                dialog.dismiss();
                if (data.getCode().equalsIgnoreCase("201")){
                  //  showMessage(data.getMessage());
                    hotspotlist=new ArrayList<ServerResponseHotSpot.Data>();
                    hotspotlist.clear();
                    for(int idx=0;idx<data.getData().size();idx++){
                        hotspotlist.add(data.getData().get(idx));
                    }
                    Constant.selectall = "false";
                    rv_hotspot.setAdapter(new RVHotspotAdapter(getActivity(),hotspotlist));

                }else {
                    showMessage("No hotspots found");
                }

            }

            @Override
            public void onFailure(Call<ServerResponseHotSpot> call, Throwable t) {
                      dialog.dismiss();
                    Log.e("aaa",t.toString());
                    showMessage("Try Again");
            }
        });

    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void hitapplyApi(String catname) {

        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseHotSpot> call=service.FilterBusinessByCategory(catname);

        cat_name    =   "";

        call.enqueue(new Callback<ServerResponseHotSpot>() {
            @Override
            public void onResponse(Call<ServerResponseHotSpot> call, Response<ServerResponseHotSpot> response) {
                ServerResponseHotSpot data = response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
//                    showMessage(data.getMessage());
                    hotspotlist=new ArrayList<ServerResponseHotSpot.Data>();
                    hotspotlist.clear();
                    for(int idx=0;idx<data.getData().size();idx++){
                        hotspotlist.add(data.getData().get(idx));
                    }
                    rv_hotspot.setAdapter(new RVHotspotAdapter(getActivity(),hotspotlist));
                }else {
                    showMessage("No hotspots found");
                    Intent intent=new Intent(getActivity(),TabActivity.class);
                    intent.putExtra("type","filter");
                    getActivity().startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<ServerResponseHotSpot> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try again");
            }
        });
    }
}
