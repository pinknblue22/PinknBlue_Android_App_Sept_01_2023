package Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.CommanActivity;
import com.pinknblue.NavigationActivity;
import com.pinknblue.R;

import java.util.ArrayList;

import Service.ServerResponseShedule;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/2/2018.
 */

public class RVScheduleAdapter extends RecyclerView.Adapter<RVScheduleAdapter.RVScheduleHolder>  {
    FragmentActivity activity;
    ArrayList<ServerResponseShedule.Stops> schedulelist;
    String ueserTye,route_insert_id;
    SharedPreferences preference;
    private Dialog dialog;
    public RVScheduleAdapter(FragmentActivity activity, ArrayList<ServerResponseShedule.Stops> schedulelist, String route_insert_id) {
        this.activity=activity;
        this.schedulelist=schedulelist;
        preference =activity.getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        this.ueserTye=preference.getString("user_type","");
        this.route_insert_id    =   route_insert_id;
    }

    @Override
    public RVScheduleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rvschedule_adapter,parent,false);
        return new RVScheduleHolder(view);
    }

    @Override
    public void onBindViewHolder(RVScheduleHolder holder, final int position) {
        holder.txt_leave.setText(schedulelist.get(position).getStart_time());
        holder.txt_arrive.setText(schedulelist.get(position).getEnd_time());

        holder.rl_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(ueserTye.equalsIgnoreCase("Free")){
                if(preference.getString("routedetails","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
                    showdilog();
                }else {
                    Intent intent = new Intent(activity, CommanActivity.class);
                    intent.putExtra("type", "schedule_details");
                    intent.putExtra("schedule_id", schedulelist.get(position).getStop_id());
                    intent.putExtra("route_insert_id", route_insert_id);
                    activity.startActivity(intent);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return schedulelist.size();
    }

    public class RVScheduleHolder extends RecyclerView.ViewHolder{
              RelativeLayout rl_layout;
              TextView txt_leave,txt_arrive;
        public RVScheduleHolder(View itemView) {
            super(itemView);
            rl_layout=(RelativeLayout)itemView.findViewById(R.id.rl_layout);
            txt_leave=(TextView)itemView.findViewById(R.id.txt_leave);
            txt_arrive=(TextView)itemView.findViewById(R.id.txt_arrive);
        }
    }


    private void showdilog() {
        dialog = new Dialog(activity);
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
                Intent intent=new Intent(activity,NavigationActivity.class);
                intent.putExtra("type","unlock");
                activity.startActivity(intent);
                activity.finish();
            }
        });
    }
}
