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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.NavigationActivity;
import com.pinknblue.R;
import com.pinknblue.TabActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Service.ServerResponseScheduledetail;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/2/2018.
 */

public class RVScheduleDetailsAdapter extends RecyclerView.Adapter<RVScheduleDetailsAdapter.RVSchDetailsHolder> {
    FragmentActivity activity;
    ArrayList<ServerResponseScheduledetail.Stop_data> stopDatalist;
    String ueserTye,schedule_id;
    SharedPreferences preference;

    public RVScheduleDetailsAdapter(FragmentActivity activity, ArrayList<ServerResponseScheduledetail.Stop_data> stopDatalist, String ueserTye, String schedule_id) {
        this.activity = activity;
        this.stopDatalist = stopDatalist;
        this.ueserTye=ueserTye;
        this.schedule_id=schedule_id;
        this.preference =   activity.getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
    }

    @Override
    public RVSchDetailsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_scheduldetails_adapter, parent, false);
        return new RVSchDetailsHolder(view);
    }

    @Override
    public void onBindViewHolder(RVSchDetailsHolder holder, final int position) {
        String time = stopDatalist.get(position).getStop_time();
        holder.txt_time.setText(time.replaceAll(" ", "\n "));
        holder.txt_address.setText(stopDatalist.get(position).getStop_address());
        if (position == 0) {
            holder.txt_bus_stop.setBackgroundResource(R.drawable.pink_circale);
            holder.txt_bus_stop.setText("");
        } else if (position == stopDatalist.size() - 1) {
            holder.txt_bus_stop.setBackgroundResource(R.drawable.blue_circale);
            holder.txt_bus_stop.setText("");
            holder.view_line.setVisibility(View.INVISIBLE);
        } else {
            holder.txt_bus_stop.setBackgroundResource(R.drawable.white_circale);
            holder.txt_bus_stop.setText("BUS\nSTOP");
            holder.view_line.setVisibility(View.VISIBLE);
        }

        holder.lldem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(ueserTye.equalsIgnoreCase("Free")){
                if(preference.getString("map","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
                    showdilog();
                }else {
                    Intent intent=new Intent(activity, TabActivity.class);
                    intent.putExtra("type","map");
                    intent.putExtra("schedule_id",schedule_id);
                    intent.putExtra("from","stop");
                    intent.putExtra("position",position);
                    activity.startActivity(intent);
//                    activity.finish();
                }
            }
        });

        /*holder.view_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*if (position != 0 && position != stopDatalist.size()-1 && !stopDatalist.get(position).getStop_Image().isEmpty())
                    showImageDialog(stopDatalist.get(position).getStop_Image());*//*


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return stopDatalist.size();
    }

    public class RVSchDetailsHolder extends RecyclerView.ViewHolder {
        TextView txt_bus_stop, txt_time, txt_address;
        View view_line;
        LinearLayout view_map,lldem;
        ImageView view_image;

        public RVSchDetailsHolder(View itemView) {
            super(itemView);
            txt_bus_stop = (TextView) itemView.findViewById(R.id.txt_bus_stop);
            txt_time = (TextView) itemView.findViewById(R.id.txt_time);
            txt_address = (TextView) itemView.findViewById(R.id.txt_address);
            view_line = (View) itemView.findViewById(R.id.view_line);
            view_map = (LinearLayout) itemView.findViewById(R.id.view_map);
            lldem = (LinearLayout) itemView.findViewById(R.id.lldem);
            view_image = (ImageView) itemView.findViewById(R.id.view_image);
        }
    }
    private void showdilog() {
       final Dialog dialog = new Dialog(activity);
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


    private void showImageDialog(String imagePath) {
       final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog);
        dialog.show();
        Button btn_close        =   (Button)dialog.findViewById(R.id.btn_close);
        ImageView stop_image    =   (ImageView) dialog.findViewById(R.id.stop_image);

        Picasso.with(activity)
                .load(imagePath)
                .error(R.mipmap.bus_stop)
                .into(stop_image);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
