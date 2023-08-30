package Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.R;
import com.pinknblue.TabActivity;

import java.util.ArrayList;

import Fragment.FragmentFilter;
import Service.ServerResponseCategory;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/2/2018.
 */

public class RVCategoryAdapter extends RecyclerView.Adapter<RVCategoryAdapter.RVCategoryHolder> {
    FragmentActivity activity;
    ArrayList<ServerResponseCategory.Data> categoyList;

    public RVCategoryAdapter(FragmentActivity activity, ArrayList<ServerResponseCategory.Data> categoyList) {
        this.activity = activity;
        this.categoyList = categoyList;

    }

    public void setCategoyList(ArrayList<ServerResponseCategory.Data> categoyList) {
        this.categoyList = categoyList;
    }

    @Override
    public RVCategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_category_adapter, parent, false);
        return new RVCategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(final RVCategoryHolder holder, final int position) {
        holder.txt_cat_name.setText(categoyList.get(position).getCategoryName());


        if (Constant.selectall.equalsIgnoreCase("true")) {
            holder.checkbox.setChecked(true);
        } else {
            if (FragmentFilter.categoryname.contains(categoyList.get(position).getCategoryName())) {
                holder.checkbox.setChecked(true);
                FragmentFilter.categoryname.add(categoyList.get(position).getCategoryName());
            } else {
                holder.checkbox.setChecked(false);
            }
        }
        Log.e("listsize", "" + FragmentFilter.categoryname.size());
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.checkbox.isChecked()) {

                    FragmentFilter.categoryname.add(categoyList.get(position).getCategoryName());

                } else {
                    int index = FragmentFilter.categoryname.indexOf(categoyList.get(position).getCategoryName());
                    FragmentFilter.categoryname.remove(index);

                }

                if (FragmentFilter.categoryname.size() == categoyList.size()) {
                    TabActivity.img_tool_search.setImageResource(R.mipmap.select_all);
                    Constant.selectall = "true";
                } else {
                    TabActivity.img_tool_search.setImageResource(R.mipmap.unselect_all);
                    Constant.selectall = "false";
                }



                Log.e("listsize", "" + FragmentFilter.categoryname.size());
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoyList.size();
    }

    public class RVCategoryHolder extends RecyclerView.ViewHolder {
        TextView txt_cat_name;
        CheckBox checkbox;

        public RVCategoryHolder(View itemView) {
            super(itemView);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox);
            txt_cat_name = (TextView) itemView.findViewById(R.id.txt_cat_name);
        }
    }
}
