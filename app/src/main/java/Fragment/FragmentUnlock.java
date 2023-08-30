package Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.R;
import com.pinknblue.TabActivity;

import Service.Interface;
import Service.ServerResponseCreateOrder;
import Service.ServerResposeRegisteruser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sdk.payeezytokenised.RequestTask;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/1/2018.
 */

public class FragmentUnlock extends Fragment implements View.OnClickListener{
    ImageView img_map,img_route;
    Button btn_route_price,btn_map_price,btn_notification_price,btn_bundle_price;
    Intent intent;
    SharedPreferences preference;
    SharedPreferences.Editor editor;
    private String loginUserId="",productid="";
    RequestTask rTask5;
    TextView tv_route_detail;
    String order_id =   "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_unlock,container,false);

        preference  =    getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId =   preference.getString("user_id", "");

        initUI(view);

        return view;
    }

    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void initUI(View view) {
        img_map                 =   (ImageView)view.findViewById(R.id.img_map);
        img_route               =   (ImageView)view.findViewById(R.id.img_route);

        btn_route_price         =   (Button)view.findViewById(R.id.btn_route_price);
        btn_map_price           =   (Button)view.findViewById(R.id.btn_map_price);
        btn_notification_price  =   (Button)view.findViewById(R.id.btn_notification_price);
        btn_bundle_price        =   (Button)view.findViewById(R.id.btn_bundle_price);

        if (preference.getString("routedetails","").equalsIgnoreCase("1")){
            btn_route_price.setEnabled(false);
            btn_route_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
        }
        if (preference.getString("map","").equalsIgnoreCase("1")){
            btn_map_price.setEnabled(false);
            btn_map_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
        }
        if (preference.getString("notifications","").equalsIgnoreCase("1")){
            btn_notification_price.setEnabled(false);
            btn_notification_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
        }
        if (preference.getString("featurebundle","").equalsIgnoreCase("1")){
            btn_bundle_price.setEnabled(false);
            btn_bundle_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
            btn_route_price.setEnabled(false);
            btn_route_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
            btn_map_price.setEnabled(false);
            btn_map_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
            btn_notification_price.setEnabled(false);
            btn_notification_price.setBackground(getActivity().getResources().getDrawable(R.drawable.unlock_button_disabled));
        }

        tv_route_detail         =   (TextView) view.findViewById(R.id.tv_route_detail);

        img_route.setOnClickListener(this);
        img_map.setOnClickListener(this);
        btn_route_price.setOnClickListener(this);
        btn_map_price.setOnClickListener(this);
        btn_notification_price.setOnClickListener(this);
        btn_bundle_price.setOnClickListener(this);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_route:
              /*  intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","schedule_details");
                startActivity(intent);*/

                break;
            case R.id.img_map:
           /*     intent=new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type","map");
                startActivity(intent);*/
                /*RequestTask rTask5 = new RequestTask(getActivity());
                String method="token";
                String amount="1100.20";
                String currency_code="USD";

                rTask5.execute("getauthorisetoken",method,amount,currency_code);

                showMessage("get authorisetoken call end");*/

                break;
            case R.id.btn_route_price:




//                getActivity().finish();


                if (Constant.isNetworkAvailable(getActivity())) {
                    CreateOrder("0.99","2");
                }else {
                    showMessage("Please check your internet connection and try again");
                }


                /*rTask5 = new RequestTask(getActivity());

                String auth="true";
                String callback="Payeezy.callback";
                String currency="USD";
                String type="FDToken";
                String cardtype="visa";
                String cardholdername="John Smith";
                String creditcardnumber="4035874000424977";
                String expdate="1030";
                String cvv="123";
                rTask5.execute("gettokenvisa",auth,callback,currency,type,cardtype,cardholdername,
                        creditcardnumber,expdate,cvv);

                Log.e("aaa","get token call end");

                showMessage("get token succusefull");*/
                break;
            case R.id.btn_map_price:
                if (Constant.isNetworkAvailable(getActivity())) {
                    CreateOrder("0.99","3");
                }else {
                    showMessage("Please check your internet connection and try again");
                }
                /*rTask5 = new RequestTask(getActivity());
                String method1="token";
                String amount1="1100";
                String currency_code1="USD";
                rTask5.execute("getpurchasetoken",method1,amount1,currency_code1);
                Log.e("aaa","get purchasetoken call end");
                showMessage("purchase token succusefull");*/

                break;
            case R.id.btn_notification_price:
                if (Constant.isNetworkAvailable(getActivity())) {
                    CreateOrder("0.99","4");
                }else {
                    showMessage("Please check your internet connection and try again");
                }
                /*rTask5 = new RequestTask(getActivity());
                String method1="token";
                String amount1="1100";
                String currency_code1="USD";
                rTask5.execute("getpurchasetoken",method1,amount1,currency_code1);
                Log.e("aaa","get purchasetoken call end");
                showMessage("purchase token succusefull");*/

                break;
            case R.id.btn_bundle_price:
                if (Constant.isNetworkAvailable(getActivity())) {
                    CreateOrder("2.47","5");
                }else {
                    showMessage("Please check your internet connection and try again");
                }
                /*rTask5 = new RequestTask(getActivity());
                String method1="token";
                String amount1="1100";
                String currency_code1="USD";
                rTask5.execute("getpurchasetoken",method1,amount1,currency_code1);
                Log.e("aaa","get purchasetoken call end");
                showMessage("purchase token succusefull");*/

                break;

        }
    }

    private void HitPaymentApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.SendNotification(loginUserId,productid);
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data=response.body();
                 dialog.dismiss();
                 if(data.getCode().equalsIgnoreCase("201")){
                     showMessage(data.getMessage());

                 }else {
                     showMessage(data.getMessage());
                 }

            }

            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                    dialog.dismiss();
                    showMessage("Try Again");
            }
        });

    }



    private void CreateOrder(String amount, String plan_id) {

       /* AlertDialog.Builder confirmationDialog  =   new AlertDialog.Builder(getActivity());
        confirmationDialog.setMessage("Do you want to purchase this feature?");
        confirmationDialog.setCancelable(true);
        confirmationDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        confirmationDialog.setNegativeButton("cancel", null);
        confirmationDialog.show();*/

        final AlertDialog dialog = new SpotsDialog(getActivity(), "Please Wait...");
        dialog.show();
        Interface service= ApiClient.getClient().create(Interface.class);
        Call<ServerResponseCreateOrder> call    =   service.creatOrder(loginUserId,amount, plan_id);
        call.enqueue(new Callback<ServerResponseCreateOrder>() {
            @Override
            public void onResponse(Call<ServerResponseCreateOrder> call, Response<ServerResponseCreateOrder> response) {
                ServerResponseCreateOrder data  =   response.body();
                dialog.dismiss();
                if(data.getCode().equalsIgnoreCase("201")){
//                    showMessage(data.getMessage());
                    order_id    =   data.getOrder_id();
                    Intent intent=new Intent(getActivity(), TabActivity.class);
                    intent.putExtra("type","acceptPayment");
                    intent.putExtra("order_id",order_id);
                    getActivity().startActivity(intent);

                }else {
                    showMessage(data.getMessage());
                }
            }
            @Override
            public void onFailure(Call<ServerResponseCreateOrder> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try Again");
            }
        });

    }





}
 //ios@a1professionals.com

      //  (11:31 AM) Pankaj Kumar Kalyan (I.T): jhGF7^%*^(5(*&6&6)&(6()&*)(
    //  First Data getway

 /*   DBA store name: 1001263635
        Store number: Developer

        Password: eweb@123*/

 //https://github.com/payeezy/payeezy_android