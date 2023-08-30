package uitls;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.pinknblue.MyApplication;

/**
 * Created by eweb-a1-pc-14 on 12/29/2017.
 */

public class Constant {
//    public static String Base_URL="http://a1professionals.net/Down_PINKnBLUE/";
    public static String Base_URL="https://www.pinkn.blue/";
    public static String FCMID="";
    public static String Shared_Pref="share_pref";
    public static String selectall="false";

    public static final int NOTIFICATION_ID             = 100;
    public static final int NOTIFICATION_ID_BIG_IMAGE   = 101;
    public static final String PUSH_NOTIFICATION        = "pushNotification";

    public static void showMessage(Context context,String s){

//        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(context,s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }


    public static boolean isGpsOn(Context ctx) {
        LocationManager locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static void checkGpsAndsaveLocationAddress(Context context)
    {
        try
        {
            if(Constant.isGpsOn(context))
            {
                Log.e("checkGpsLocationAddress","isGpsOn true called");

                Location location=Constant.getCurrentLocation(context);

                if(location!=null)
                {
                    MyApplication.latitute=location.getLatitude();
                    MyApplication.longitude=location.getLongitude();
//                    AppUtills.getAddressFromLocation(Application.latitute, Application.longitude, context);
                    Log.d("getLatitude-->",location.getLatitude()+"--"+location.getLongitude());
                }
            }else
            {
                Log.e("elsechkGpsLocationAddrs","isGpsOn called");
                Location location=Constant.getCurrentLocation(context);

                if(location!=null)
                {
                    MyApplication.latitute=location.getLatitude();
                    MyApplication.longitude=location.getLongitude();
//                    AppUtills.getAddressFromLocation(Application.latitute, Application.longitude, context);
                    Log.d("getLatitude-->",location.getLatitude()+"--"+location.getLongitude());
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public static Location getCurrentLocation(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Location location;
            String mProvider;
            if (locationManager != null) {
                Criteria criteria = new Criteria();
                criteria.setPowerRequirement(Criteria.POWER_LOW); // Chose your desired power consumption level.
                criteria.setAccuracy(Criteria.ACCURACY_FINE); // Choose your accuracy requirement.
                criteria.setSpeedRequired(true); // Chose if speed for first location fix is required.
                criteria.setAltitudeRequired(false); // Choose if you use altitude.
                criteria.setBearingRequired(false); // Choose if you use bearing.
                criteria.setCostAllowed(false);


                boolean networkIsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                boolean gpsIsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                Log.v("LocationIsEnabled", "" + networkIsEnabled);
                Log.v("Location gIsEnabled", "" + gpsIsEnabled);

                if (networkIsEnabled)
                {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location == null)
                    {
                        if(gpsIsEnabled)
                        {
                            mProvider =locationManager.getBestProvider(criteria, gpsIsEnabled);
                            location = locationManager.getLastKnownLocation(mProvider);

                            if (location == null)
                            {
                                return null;
                            }
                            else
                            {
                                return location;
                            }
                        }
                        else
                        {
                            return null;
                        }
                    }
                    else
                    {
                        return location;
                    }

                }
                else if(gpsIsEnabled)
                {
                    mProvider =locationManager.getBestProvider(criteria, gpsIsEnabled);
                    location = locationManager.getLastKnownLocation(mProvider);

                    return location;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                Log.v("Location Service","Location Manager is null");
                return null;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }



    public static boolean isNetworkAvailable(Context context) {
        boolean connected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connected;
    }

   //  http://a1professionals.net/PINKnBLUE/
}
