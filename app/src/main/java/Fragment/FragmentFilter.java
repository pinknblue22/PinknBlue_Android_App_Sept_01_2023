package Fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.List;

import Adapter.RVCategoryAdapter;
import Service.Interface;
import Service.ServerResponseCategory;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/2/2018.
 */

public class FragmentFilter extends Fragment implements View.OnClickListener {
    RecyclerView rv_categories;
    Button btn_apply;
    TextView txt_reset;
    ArrayList<ServerResponseCategory.Data> categoyList;
    public static List<String> categoryname;
    String cat_name = "";
    private RVCategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);
        initUI(view);
        rv_categories.setLayoutManager(new LinearLayoutManager(getActivity()));
        hitcatApi();
        categoryname = new ArrayList<>();

        //categoryname.clear();
   /*     TabActivity.img_tool_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentFilter.categoryname.clear();
                if (Constant.selectall.equalsIgnoreCase("false")) {
                    Constant.selectall = "true";
                    TabActivity.img_tool_search.setImageResource(R.mipmap.select_all);
                      adapter.notifyDataSetChanged();
                } else {
                    Constant.selectall = "false";
                    TabActivity.img_tool_search.setImageResource(R.mipmap.unselect_all);
                     adapter.notifyDataSetChanged();
                }
            }
        });*/

        changeSelectionValue();
        return view;
    }

    public void changeSelectionValue() {
        if (Constant.selectall.equalsIgnoreCase("true")) {
            TabActivity.img_tool_search.setImageResource(R.mipmap.select_all);
            Constant.selectall = "true";
        } else {
            Constant.selectall = "false";
        }
    }

    private void hitcatApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
         dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResponseCategory> call = service.categories_list();
        call.enqueue(new Callback<ServerResponseCategory>() {
            @Override
            public void onResponse(Call<ServerResponseCategory> call, Response<ServerResponseCategory> response) {
                ServerResponseCategory data = response.body();
                dialog.dismiss();
                FragmentFilter.categoryname.clear();
                categoyList = new ArrayList<>();
                categoyList.clear();
                adapter = new RVCategoryAdapter(getActivity(), categoyList);
                rv_categories.setAdapter(adapter);

                if (data.getCode().equalsIgnoreCase("201")) {
                    //showMessage(data.getMessage());

                    for (int idx = 0; idx < data.getDatalist().size(); idx++) {
                        categoyList.add(data.getDatalist().get(idx));
                    }

                    if( Constant.selectall.equals("true"))
                    {
                        for(int idx=0;idx<categoyList.size();idx++)
                        {
                            FragmentFilter.categoryname.add(categoyList.get(idx).getCategoryName());
                        }

                    }

                    adapter.setCategoyList(categoyList);
                    adapter.notifyDataSetChanged();

                } else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseCategory> call, Throwable t) {
                dialog.dismiss();
                showMessage("try again");
            }
        });
    }

    private void initUI(View view) {
        rv_categories = (RecyclerView) view.findViewById(R.id.rv_categories);
        txt_reset = (TextView) view.findViewById(R.id.txt_reset);
        btn_apply = (Button) view.findViewById(R.id.btn_apply);
        btn_apply.setOnClickListener(this);
        txt_reset.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_apply:
                if (categoryname.size() > 0) {
                    for (int j = 0; j < categoryname.size(); j++) {
                        if (j == 0) {
                            cat_name = categoryname.get(j);
//                Log.e("bbbb",cat_name);
                        } else {
                            cat_name += "," + categoryname.get(j);
                        }
                        Log.e("bbbb", cat_name);
                    }
                    Log.e("cat_name-->", cat_name);
                    Intent intent = new Intent(getActivity(), NavigationActivity.class);
                    intent.putExtra("type", "hotspot");
                    intent.putExtra("cat_name", cat_name);
                    intent.putExtra("filtertype", "filteraplly");
                    startActivity(intent);
                    getActivity().finish();
                } else {
                    showMessage("Please select at least one category");
                }
                break;
            case R.id.txt_reset:
                FragmentFilter.categoryname.clear();
                Constant.selectall = "false";
                TabActivity.img_tool_search.setImageResource(R.mipmap.unselect_all);
                adapter.notifyDataSetChanged();

                break;
        }
    }

    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
