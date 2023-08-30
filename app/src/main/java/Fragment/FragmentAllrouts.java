package Fragment;

import android.app.AlertDialog;
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
import java.util.ArrayList;
import Adapter.RVAllroutsAdapter;
import Service.Interface;
import Service.ServerResponseAllroutes;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/1/2018.
 */

public class FragmentAllrouts extends Fragment {

    RecyclerView rv_allrouts;
    ArrayList<ServerResponseAllroutes.Data> allroutelist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_allrouts,container,false);

        rv_allrouts=(RecyclerView)view.findViewById(R.id.rv_allrouts);
        rv_allrouts.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (Constant.isNetworkAvailable(getActivity())) {
            HitallRouteApi();
        }else {
            showMessage("Please check your internet connection and try again");
        }

        return view;
    }

    private void HitallRouteApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();

        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseAllroutes> call=service.all_route();
        call.enqueue(new Callback<ServerResponseAllroutes>() {
            @Override
            public void onResponse(Call<ServerResponseAllroutes> call, Response<ServerResponseAllroutes> response) {
                ServerResponseAllroutes data=response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
                   //  showMessage(data.getMessage());
                     allroutelist=new ArrayList<>();
                     allroutelist.clear();
                    for(int idx=0;idx<data.getDatalist().size();idx++){
                        allroutelist.add(data.getDatalist().get(idx));
                    }

                    rv_allrouts.setAdapter(new RVAllroutsAdapter(getActivity(),allroutelist));
                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseAllroutes> call, Throwable t) {
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
}
