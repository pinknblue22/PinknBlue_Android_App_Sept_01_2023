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
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.pinknblue.NavigationActivity;
import com.pinknblue.R;
import com.pinknblue.TabActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import Service.ServerResponseHotSpot;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/1/2018.
 */

public class RVHotspotAdapter extends RecyclerView.Adapter<RVHotspotAdapter.RVHotspotHolder> {
    FragmentActivity activity;
    ArrayList<ServerResponseHotSpot.Data> hotspotlist;
    String ueserTye;
    SharedPreferences preference;

    public RVHotspotAdapter(FragmentActivity activity, ArrayList<ServerResponseHotSpot.Data> hotspotlist) {
        this.activity = activity;
        this.hotspotlist = hotspotlist;
        preference  =   activity.getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        ueserTye    =   preference.getString("user_type","");
    }


    @Override
    public RVHotspotHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_hotspot_adapter, parent, false);
        return new RVHotspotHolder(view);
    }

    @Override
    public void onBindViewHolder(RVHotspotHolder holder, final int position) {

        holder.txt_businss_name.setText(hotspotlist.get(position).getBusiness_name());
        String url = hotspotlist.get(position).getBusiness_image();
        if(!(url.equals(""))) {
            Picasso.with(activity).load(url)
                    .error(R.mipmap.ic_hotspot_pin_default)
                    .into(holder.img_busines_imge);
        }
        else
        {
            holder.img_busines_imge.setImageResource(R.mipmap.ic_hotspot_pin_default);
        }
        holder.layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(ueserTye.equalsIgnoreCase("Free")){
                if(preference.getString("map","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
                    showdilog();
                }else {

                    String b_id = hotspotlist.get(position).getBusiness_id();
                    Intent intent = new Intent(activity, TabActivity.class);
                    intent.putExtra("type", "bussines");
                    intent.putExtra("b_id", b_id);
                    intent.putExtra("bussiness_name", hotspotlist.get(position).getBusiness_name());
                    activity.startActivity(intent);
//                Constant.showMessage(activity,"hello");

                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return hotspotlist.size();
    }

    public class RVHotspotHolder extends RecyclerView.ViewHolder {
        LinearLayout layout_one;
        CircularImageView img_busines_imge;
        TextView txt_businss_name;

        public RVHotspotHolder(View itemView) {
            super(itemView);
            layout_one = (LinearLayout) itemView.findViewById(R.id.layout_one);
            img_busines_imge = (CircularImageView) itemView.findViewById(R.id.img_busines_imge);
            txt_businss_name = (TextView) itemView.findViewById(R.id.txt_businss_name);
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
}
