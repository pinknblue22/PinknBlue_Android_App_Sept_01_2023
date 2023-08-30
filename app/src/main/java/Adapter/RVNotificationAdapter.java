package Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.CommanActivity;
import com.pinknblue.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Service.ServerResponsefetchNotification;

/**
 * Created by eweb-a1-pc-14 on 1/3/2018.
 */

public class RVNotificationAdapter extends RecyclerView.Adapter<RVNotificationAdapter.RVNotificationHolder> {
    FragmentActivity activity;
    Intent intent;
    ArrayList<ServerResponsefetchNotification.Data> data;
    public RVNotificationAdapter(FragmentActivity activity, ArrayList<ServerResponsefetchNotification.Data> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public RVNotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_notificatio_adapter,parent,false);

        return new RVNotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(RVNotificationHolder holder, int position) {
        holder.txt_msg.setText(data.get(position).getMessage());

        String date1 = data.get(position).getDate() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Difference = null;
        try {
            Date oldDate = dateFormat.parse(date1);
            Date currentDate = new Date();
            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
             if (oldDate.before(currentDate)) {
                if (days == 0) {
                    if (hours == 0) {
                        if (minutes == 0) {
                            Difference = "now";
                        } else {
                            Difference = minutes + " " + "mins" + " " + "ago";
                        }
                    } else {
                        Difference = hours + " " + "hours" + "  " + "ago";
                    }
                } else {
                    Difference = days + " " + "days" + " " + "ago";
                    //Difference=minutes+" "+"minutes"+" "+"ago";
                }

                Log.e("oldDate", "is previous date");
                // Difference = " seconds: " + seconds + " minutes: " + minutes + " hours: " + hours + " days: " + days;
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes + " hours: " + hours + " days: " + days);

            }

            // Log.e("toyBornTime", "" + toyBornTime);
        } catch (Exception e) {

        }
        holder.txt_time.setText(Difference);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class RVNotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
          TextView txt_msg,txt_time;
        public RVNotificationHolder(View itemView) {
            super(itemView);
            txt_msg=(TextView)itemView.findViewById(R.id.txt_msg);
            txt_time=(TextView)itemView.findViewById(R.id.txt_time);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            intent=new Intent(activity, CommanActivity.class);
            intent.putExtra("type","notification_detail");
            intent.putExtra("message",data.get(getPosition()).getMessage());
            intent.putExtra("time",data.get(getPosition()).getDate());

            activity.startActivity(intent);
        }
    }
}
