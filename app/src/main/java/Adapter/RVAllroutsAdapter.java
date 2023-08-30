package Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.R;
import com.pinknblue.TabActivity;

import java.util.ArrayList;

import Service.ServerResponseAllroutes;

/**
 * Created by eweb-a1-pc-14 on 1/1/2018.
 */

public class RVAllroutsAdapter extends RecyclerView.Adapter<RVAllroutsAdapter.RVAllroutsHolder> {
    FragmentActivity activity;
    ArrayList<ServerResponseAllroutes.Data> data;

    public RVAllroutsAdapter(FragmentActivity activity, ArrayList<ServerResponseAllroutes.Data> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public RVAllroutsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_allrots_adapter,parent,false);
        return new RVAllroutsHolder(view);
    }

    @Override
    public void onBindViewHolder(RVAllroutsHolder holder, final int position) {
//        holder.txt_routeid.setText(data.get(position).getRoute_id());
        holder.txt_routeid.setText(data.get(position).getRoute_insert_id());
        String start=data.get(position).getStarting_location();
        String[] Start_parts = start.split(",");
        String start_part1 = Start_parts[0]; // 004
//        String start_part2 = Start_parts[1]; // 034556

        String end=data.get(position).getEnd_location();
        String[] end_parts = end.split(",");
        String end_part1 = end_parts[0]; // 004
       // String end_part2 = end_parts[1]; // 034

        holder.txt_location.setText(start_part1+"-"+end_part1);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity, TabActivity.class);
                intent.putExtra("type","shedules");
                intent.putExtra("route_id",data.get(position).getRoute_id());
                intent.putExtra("route_insert_id",data.get(position).getRoute_insert_id());
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RVAllroutsHolder extends RecyclerView.ViewHolder{
        CardView card_view;
        TextView txt_routeid,txt_location;
        public RVAllroutsHolder(View itemView) {
            super(itemView);
            card_view=(CardView)itemView.findViewById(R.id.card_view);
            txt_location=(TextView)itemView.findViewById(R.id.txt_location);
            txt_routeid=(TextView)itemView.findViewById(R.id.txt_routeid);
        }
    }

}
