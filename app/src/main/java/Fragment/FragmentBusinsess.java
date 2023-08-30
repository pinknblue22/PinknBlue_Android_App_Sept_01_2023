package Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.CommanActivity;

import com.pinknblue.NavigationActivity;
import com.pinknblue.R;
import com.squareup.picasso.Picasso;

import Service.Interface;
import Service.ServerResponsehotsoptDetail;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/2/2018.
 */

public class FragmentBusinsess extends Fragment implements View.OnClickListener {
    String  hotspot_id;
    ImageView img_busines_imge;
    TextView txt_businss_name,txt_cat_name,txt_address,txt_contact,txt_email;
    String url,phone,address,latitude,longitude;
    String ueserTye;
    SharedPreferences preference;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_business,container,false);
        checkPermissions();
        hotspot_id=getArguments().getString("b_id");

        preference =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        ueserTye=preference.getString("user_type","");

        initUI(view);

        if (Constant.isNetworkAvailable(getActivity())) {
            hitb_detailApi();
        }else {
            showMessage("Please check your internet connection and try again");
        }

        txt_contact.setOnClickListener(this);
        txt_email.setOnClickListener(this);
        txt_address.setOnClickListener(this);

        return view;
    }

    private void hitb_detailApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();

        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponsehotsoptDetail> call=service.hotspot_details(hotspot_id);
        call.enqueue(new Callback<ServerResponsehotsoptDetail>() {
            @Override
            public void onResponse(Call<ServerResponsehotsoptDetail> call, Response<ServerResponsehotsoptDetail> response) {
                ServerResponsehotsoptDetail data =response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
                   // showMessage(data.getMessage());
                    txt_businss_name.setText(data.getBusiness_name());
//                    txt_cat_name.setText(data.getCategory_name().replace(",",",\n"));
                    txt_cat_name.setText(data.getCategory_name().replace(",",", "));
                    url=data.getWebsite_link();
                    phone=data.getContact_number();
                    address=data.getLocation();
                    latitude   =   String.valueOf(data.getLatitude());
                    longitude   =   String.valueOf(data.getLongitude());
                    Log.d("location-->",latitude+"-"+longitude);

                    if (address.isEmpty()){
                        txt_address.setVisibility(View.GONE);
                    }else {
                        txt_address.setText(address);
                        txt_address.setVisibility(View.VISIBLE);
                    }

                    if (phone.isEmpty()){
                        txt_contact.setVisibility(View.GONE);
                    }else {
                        txt_contact.setText(phone);
                        txt_contact.setVisibility(View.VISIBLE);
                    }

                    if (url.isEmpty()){
                        txt_email.setVisibility(View.GONE);
                    }else {
                        txt_email.setText(url);
                        txt_email.setVisibility(View.VISIBLE);
                    }
                    Picasso.with(getActivity())
                            .load(data.getBusiness_image())
                            .error(R.mipmap.business_default)
                            .into(img_busines_imge);

                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponsehotsoptDetail> call, Throwable t) {
                     dialog.dismiss();
                     showMessage("Try again");
            }
        });


    }

    private void initUI(View view) {
        img_busines_imge=(ImageView)view.findViewById(R.id.img_busines_imge);
        txt_businss_name=(TextView)view.findViewById(R.id.txt_businss_name);
        txt_cat_name=(TextView)view.findViewById(R.id.txt_cat_name);
        txt_address=(TextView)view.findViewById(R.id.txt_address);
        txt_contact=(TextView)view.findViewById(R.id.txt_contact);
        txt_email=(TextView)view.findViewById(R.id.txt_email);
    }
    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (getActivity().checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1);
                return;

            } else {
                //nextActivity();
            }
            /*if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && getActivity().checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) !=PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE}, 1);
                return;

            } else {
                //nextActivity();
            }*/

        } else {
            //   nextActivity();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.txt_email:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
                break;
            case R.id.txt_contact:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + phone));
                startActivity(callIntent);
                break;
            case R.id.txt_address:
//                if(ueserTye.equalsIgnoreCase("Free")) {
                if(preference.getString("map","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
                    showdilog();
                }else {
                    Intent intent = new Intent(getActivity(), CommanActivity.class);
                    intent.putExtra("type", "viewmap");
                    intent.putExtra("hotaddress", address);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);
                }
             break;
        }
    }

    private void showdilog() {
        final Dialog dialog = new Dialog(getActivity());
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
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
