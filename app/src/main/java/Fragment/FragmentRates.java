package Fragment;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.HomeActivity;
import com.pinknblue.R;

import Service.Interface;
import Service.ServerResposeRegisteruser;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;

/**
 * Created by eweb-a1-pc-14 on 1/3/2018.
 */

public class FragmentRates extends Fragment implements View.OnClickListener{

    RatingBar ratingBar;
    Button submit,rate_on_google_play;
    private String loginUserId="";
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   =   inflater.inflate(R.layout.fragment_rateus,container,false);
        preferences =getActivity(). getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preferences.getString("user_id", "");
        intUI(view);
        return view;
    }

    private void intUI(View view) {
        ratingBar   =   (RatingBar)view.findViewById(R.id.ratingBar);
        submit      =   (Button)view.findViewById(R.id.submit);
        rate_on_google_play      =   (Button)view.findViewById(R.id.rate_on_google_play);

        submit.setOnClickListener(this);
        rate_on_google_play.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                if (Constant.isNetworkAvailable(getActivity())) {
                    HitRateApi();
                }else {
                    showMessage("Please check your internet connection and try again");
                }

                break;
            case R.id.rate_on_google_play:
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }

                break;
        }
    }


    private void HitRateApi() {
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResposeRegisteruser> call=service.rateApp(loginUserId,String.valueOf(ratingBar.getRating()));
        call.enqueue(new Callback<ServerResposeRegisteruser>() {
            @Override
            public void onResponse(Call<ServerResposeRegisteruser> call, Response<ServerResposeRegisteruser> response) {
                ServerResposeRegisteruser data = response.body();
                dialog.dismiss();
                if (data.getCode().equalsIgnoreCase("201")){
                    showMessage(data.getMessage());
                    getActivity().finishAffinity();
                    Intent intent = new Intent(getActivity(),HomeActivity.class);
                    intent.putExtra("type","");
                    startActivity(intent);
                }else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResposeRegisteruser> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try again");
            }
        });


    }


    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}
