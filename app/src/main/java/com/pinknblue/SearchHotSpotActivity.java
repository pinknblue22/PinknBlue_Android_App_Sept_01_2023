package com.pinknblue;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import Adapter.RVHotspotAdapter;
import Adapter.RVSearchAdapter;
import Service.Interface;
import Service.ServerResponseHotSpot;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

public class SearchHotSpotActivity extends AppCompatActivity {
    Drawable upArrow;
    Toolbar toolbar;
    TextView txt_tool_title;
    ImageView img_tool_search;
    RecyclerView rv_allrouts,rv_serchlist;
    RVSearchAdapter adapter;
    EditText edt_search;
    String searchstring;
    ArrayList<ServerResponseHotSpot.Data> hotspotlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_search_hotspot);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

         upArrow = ContextCompat.getDrawable(this, R.mipmap.back_arrow);
         getSupportActionBar().setHomeAsUpIndicator(upArrow);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         initUI();
         txt_tool_title.setText("Search");
        // HitallRouteApi();

         edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* ArrayList<ServerResponseAllroutes.Data> searchList=new ArrayList<ServerResponseAllroutes.Data>();
                searchList.clear();*/
                 searchstring=edt_search.getText().toString().trim().toLowerCase(Locale.getDefault());
             /*   if (searchstring.length()>0){
                    for(int idx=0;idx<allroutelist.size();idx++)
                    {
                        if(allroutelist.get(idx).getStarting_location().toLowerCase().contains(searchstring))
                        {
                            searchList.add(allroutelist.get(idx));
                        }
                        if(searchList.size()>0) {
                            rv_serchlist.setVisibility(View.VISIBLE);
                            adapter=new RVSearchAdapter(SearchActivity.this,searchList);
                            rv_serchlist.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }else
                        {
                            rv_serchlist.setVisibility(View.INVISIBLE);
                        }
                    }

                }else {
                    rv_serchlist.setVisibility(View.INVISIBLE);
                }*/

                if(s.length()==0){
                    rv_serchlist.setVisibility(View.GONE);
                }else {
                    rv_serchlist.removeAllViews();
                    if (Constant.isNetworkAvailable(getApplicationContext())) {
                        HitSearchHotSpotApi();
                    }else {
                        showMessage("Please check your internet connection and try again");
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void HitSearchHotSpotApi() {
        final AlertDialog dialog = new SpotsDialog(this, "Wait...");
       // dialog.show();

        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseHotSpot> call=service.SearchRouteNew(searchstring);
        call.enqueue(new Callback<ServerResponseHotSpot>() {
            @Override
            public void onResponse(Call<ServerResponseHotSpot> call, Response<ServerResponseHotSpot> response) {
                ServerResponseHotSpot data=response.body();
                dialog.dismiss();
                hotspotlist=new ArrayList<ServerResponseHotSpot.Data>();
                hotspotlist.clear();
                if (data.getCode().equalsIgnoreCase("201")){
                   // showMessage(data.getMessage());

                    for(int idx=0;idx<data.getData().size();idx++){
                        hotspotlist.add(data.getData().get(idx));
                    }


                    rv_serchlist.setAdapter(new RVHotspotAdapter(SearchHotSpotActivity.this,hotspotlist));
                    rv_serchlist.setVisibility(View.VISIBLE);
                   // Log.e("aaa",""+allroutelist.size());
                }else {
                    showMessage(data.getMessage());
                    rv_serchlist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ServerResponseHotSpot> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try again");
            }
        });
    }

    private void initUI() {
        img_tool_search=(ImageView)findViewById(R.id.img_tool_search);
        txt_tool_title=(TextView)findViewById(R.id.txt_tool_title);
        edt_search=(EditText)findViewById(R.id.edt_search);
        rv_allrouts=(RecyclerView)findViewById(R.id.rv_allrouts);
        rv_allrouts.setLayoutManager(new LinearLayoutManager(this));

        rv_serchlist=(RecyclerView)findViewById(R.id.rv_serchlist);
        rv_serchlist.setLayoutManager(new LinearLayoutManager(this));
    }

    private void showMessage(String s) {
//        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
