package Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.NavigationActivity;

import com.pinknblue.R;
import com.pinknblue.TabActivity;

import java.util.ArrayList;

import Adapter.RVScheduleDetailsAdapter;
import Service.Interface;
import Service.ServerResponseScheduledetail;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/2/2018.
 */

public class FragmentSchedulesDetails extends Fragment implements View.OnClickListener {
    RecyclerView rv_schedule_details;
    Button btn_view_map;
    TextView txt_routeid,txt_address;
    private Dialog dialog;
    String schedule_id=" ",ueserTye,route_insert_id="";
    ArrayList<ServerResponseScheduledetail.Stop_data> stopDatalist;
    SharedPreferences preference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedules_details, container, false);
        preference =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        schedule_id=getArguments().getString("schedule_id");
        route_insert_id=getArguments().getString("route_insert_id");
        ueserTye=preference.getString("user_type","");


        Log.d("schedule_id--->",schedule_id);

        initUI(view);

        rv_schedule_details.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (Constant.isNetworkAvailable(getActivity())) {
            HitApi();
        }else {
            showMessage("Please check your internet connection and try again");
        }

        return view;
    }

    private void HitApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseScheduledetail> call=service.GetScheduleFullDetails(schedule_id);
        call.enqueue(new Callback<ServerResponseScheduledetail>() {
            @Override
            public void onResponse(Call<ServerResponseScheduledetail> call, Response<ServerResponseScheduledetail> response) {
                ServerResponseScheduledetail data =response.body();
//                Log.e("aaa",data.toString());
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){

//                    txt_routeid.setText(data.getRoute_ID());
                    txt_routeid.setText(route_insert_id);

                    String start=data.getStarting_location();
                    String[] Start_parts = start.split(",");
                    String start_part1 = Start_parts[0]; // 004
//        String start_part2 = Start_parts[1]; // 034556

                    String end=data.getEnd_location();
                    String[] end_parts = end.split(",");
                    String end_part1 = end_parts[0]; // 004
                    // String end_part2 = end_parts[1]; // 034

                    txt_address.setText(start_part1+"-"+end_part1);
                    stopDatalist=new ArrayList<>();
                    stopDatalist.clear();
                    for(int idx=0;idx<data.getStopdatalist().size();idx++){
                        stopDatalist.add(data.getStopdatalist().get(idx));
                    }
                    Log.e("aaa",""+stopDatalist.size());
                    rv_schedule_details.setAdapter(new RVScheduleDetailsAdapter(getActivity(),stopDatalist,ueserTye,schedule_id));
                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseScheduledetail> call, Throwable t) {
                   showMessage("Try again");
                   Log.e("aaa",t.toString());
                   dialog.dismiss();
            }
        });
    }

    private void initUI(View view) {
        rv_schedule_details = (RecyclerView) view.findViewById(R.id.rv_schedule_details);
        btn_view_map = (Button) view.findViewById(R.id.btn_view_map);
        txt_routeid=(TextView)view.findViewById(R.id.txt_routeid);
        txt_address=(TextView)view.findViewById(R.id.txt_address);
        btn_view_map.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if(ueserTye.equalsIgnoreCase("Free")){
        if(preference.getString("map","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
            showdilog();
        }else {
            Intent intent=new Intent(getActivity(), TabActivity.class);
            intent.putExtra("type","map");
            intent.putExtra("schedule_id",schedule_id);
            startActivity(intent);
//            getActivity().finish();
        }


    }

    private void showdilog() {
        dialog = new Dialog(getActivity());
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
                startActivity(intent);
                getActivity().finish();
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
