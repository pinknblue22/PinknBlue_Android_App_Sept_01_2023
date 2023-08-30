package Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.pinknblue.R;
import com.pinknblue.SearchActivity;
import com.pinknblue.TabActivity;

import java.util.ArrayList;

import Service.ServerResponseSearch;

/**
 * Created by eweb-a1-pc-14 on 1/16/2018.
 */

public class RVSearchAdapter extends RecyclerView.Adapter<RVSearchAdapter.RVsearchHoldedr> {
    SearchActivity searchActivity;
    ArrayList<ServerResponseSearch.Data> searchList;
    public RVSearchAdapter(SearchActivity searchActivity, ArrayList<ServerResponseSearch.Data> searchList) {
        this.searchActivity=searchActivity;
        this.searchList=searchList;

    }

    @Override
    public RVsearchHoldedr onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_search_adapter,parent,false);
        return new RVsearchHoldedr(view);
    }

    @Override
    public void onBindViewHolder(RVsearchHoldedr holder, final int position) {
         // holder.txt_routename.setText(searchList.get(position).getStarting_location());

        String start=searchList.get(position).getStarting_location();
        String[] Start_parts = start.split(",");
        String start_part1 = Start_parts[0]; // 004
//        String start_part2 = Start_parts[1]; // 034556

        String end=searchList.get(position).getEnd_location();
        String[] end_parts = end.split(",");
        String end_part1 = end_parts[0]; // 004
        // String end_part2 = end_parts[1]; // 034

//        holder.txt_routename.setText(start_part1+"-"+end_part1);
        holder.txt_routename.setText(searchList.get(position).getStarting_location_name()+"-"+searchList.get(position).getEnd_location_name());
       // Log.e("aaa",""+start_part1+"-"+end_part1);

          holder.layout_one.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(searchActivity, TabActivity.class);
                  intent.putExtra("type","shedules");
                  intent.putExtra("route_id",searchList.get(position).getRoute_id());
                  intent.putExtra("route_insert_id",searchList.get(position).getRoute_insert_id());
                  searchActivity.startActivity(intent);
              }
          });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class RVsearchHoldedr extends RecyclerView.ViewHolder{
          LinearLayout layout_one;
          TextView txt_routename;
        public RVsearchHoldedr(View itemView) {
            super(itemView);
            layout_one=(LinearLayout)itemView.findViewById(R.id.layout_one);
            txt_routename=(TextView)itemView.findViewById(R.id.txt_routename);
        }
    }
}
