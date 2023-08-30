package Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pinknblue.MyApplication;
import com.pinknblue.NavigationActivity;
import com.pinknblue.R;
import com.pinknblue.TabActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Service.Interface;
import Service.ServerResponseScheduledetail;
import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uitls.ApiClient;
import uitls.Constant;
import uitls.GMapV2GetRoute;
import uitls.NotificationUtils;
import util.DirectionsJSONParser;

/**
 * Created by Param
 */

public class FargmentNearByMap extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener  {
    ArrayList<LatLng> stopDatalist;
    String route_id = "";
    Integer position = 0;
    String from = "";
    private GoogleMap googleMap;
    GMapV2GetRoute v2GetRouteDirection;
    Document document;
    LatLng destination,end;
    LatLng source,start;
    private static final String TAG = FargmentNearByMap.class.getSimpleName();

    ArrayList<LatLng> coordList = new ArrayList<LatLng>();
    String ueserTye;
    SharedPreferences preference;

    int mMode=0;
    String waypoints =  "waypoints=optimize:true|";
    String waypoints_parts =  "";

    public static ArrayList<LatLng> waypointData = new ArrayList<LatLng>();

    private void showImageDialog(String imagePath) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.image_dialog);
        dialog.show();
        Button btn_close        =   (Button)dialog.findViewById(R.id.btn_close);
        ImageView stop_image    =   (ImageView) dialog.findViewById(R.id.stop_image);

        Picasso.with(getActivity())
                .load(imagePath)
                .error(R.mipmap.bus_stop)
                .into(stop_image);

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
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


    @Override
    public boolean onMarkerClick(final Marker marker) {

        Log.d("b_id--->",marker.getSnippet()+"-"+marker.getTitle());
        if (marker.getTitle().equalsIgnoreCase("stops")){
            showImageDialog(marker.getSnippet());
        }else if (!marker.getTitle().equalsIgnoreCase("start") && !marker.getTitle().equalsIgnoreCase("end")) {

//            if(ueserTye.equalsIgnoreCase("Free")){
            if(preference.getString("map","").equalsIgnoreCase("0") &&  preference.getString("featurebundle","").equalsIgnoreCase("0")) {
                showdilog();
            }else {
                String b_id = marker.getSnippet();
                Intent intent = new Intent(getActivity(), TabActivity.class);
                intent.putExtra("type", "bussines");
                intent.putExtra("b_id", b_id);
                intent.putExtra("bussiness_name", marker.getTitle());
                getActivity().startActivity(intent);
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view   =   inflater.inflate(R.layout.fragment_map, container, false);

        route_id =   getArguments().getString("route_id");

//        position    =   getArguments().getInt("position");
        from        =   getArguments().getString("from");

        waypointData.clear();


        preference  =   getActivity().getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        ueserTye    =   preference.getString("user_type","");


//        Log.e("position-->", position+"-"+from);

        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        if (Constant.isNetworkAvailable(getActivity())) {
            HitApi();
        }else {
            showMessage("Please check your internet connection and try again");
        }
        return view;
    }

    private void HitApi() {
        Log.e(TAG, "Response :route_id: "+ route_id);
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Wait...");
        dialog.show();
        Interface service = ApiClient.getClient().create(Interface.class);
        Call<ServerResponseScheduledetail> call = service.GetScheduleFullDetailsByRouteId(route_id, String.valueOf(MyApplication.latitute),String.valueOf(MyApplication.longitude));
        call.enqueue(new Callback<ServerResponseScheduledetail>() {
            @Override
            public void onResponse(Call<ServerResponseScheduledetail> call, Response<ServerResponseScheduledetail> response) {
                ServerResponseScheduledetail data = response.body();

                dialog.dismiss();
                if (data.getCode().equalsIgnoreCase("201")) {
//                    showMessage(data.getMessage());
                    stopDatalist = new ArrayList<>();
                    stopDatalist.clear();


                   for (int i=0;i<data.getHotspots().size();i++){
                       double hlatitude = data.getHotspots().get(i).getLatitude();
                       double hlongitude = data.getHotspots().get(i).getLongitude();
                       LatLng ph = null;
                       Log.e(TAG, "Response :getHotspots: "+"lat: " + hlatitude  + " lang: "  + hlongitude);

                       ph = new LatLng(hlatitude, hlongitude);
                       googleMap.addMarker(new MarkerOptions()
                               .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_hotspot_pin))
                               .position(ph)
                               .title(data.getHotspots().get(i).getBusiness_name())
                               .snippet(data.getHotspots().get(i).getBusiness_id()));
                       googleMap.setOnMarkerClickListener(FargmentNearByMap.this);
                   }


                    for (int idx = 0; idx < data.getStopdatalist().size(); idx++) {
//
                        double latitude = data.getStopdatalist().get(idx).getLatitude();
                        double longitude = data.getStopdatalist().get(idx).getLongitude();

                        LatLng p1 = null;
                        p1 = new LatLng(latitude, longitude);
                        Log.e(TAG, "Response :getstopDataList: "+"lat: " + latitude  + " lang: "  + longitude);

                        Log.e("latlang-->", idx+"-" + p1.toString());

                        coordList.add(new LatLng(latitude, longitude));
                        stopDatalist.add(p1);

                        waypoints += "" + latitude + "," + longitude + "|";

                        waypointData.add(p1);


                        if (idx == 0) {
                            googleMap.addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory
                                            .fromResource(R.mipmap.ic_source_icon))
                                    .title("start")
                                    .snippet(data.getStopdatalist().get(idx).getStop_Image())
                                    .position(p1));
//                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p1, 13));
                        } else {

                            if (data.getStopdatalist().size() - 1 == idx) {
                                googleMap.addMarker(new MarkerOptions()
                                        .icon(BitmapDescriptorFactory
                                                .fromResource(R.mipmap.ic_destination_icon))
                                        .title("end")
                                        .snippet(data.getStopdatalist().get(idx).getStop_Image())
                                        .position(p1));
                            } else {
                                googleMap.addMarker(new MarkerOptions()
                                        .icon(BitmapDescriptorFactory
                                                .fromResource(R.mipmap.bus_stop))
                                        .title("stops")
                                        .snippet(data.getStopdatalist().get(idx).getStop_Image())
                                        .position(p1));

                            }
                        }

                        if (idx==data.getNearbyNode()){
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(p1, 15));
                        }

                    }

                    Log.e("waypointData-->", "" + waypointData.size());
                    /*for (int y=0;y<waypointData.size();y++){
                        String url = getDirectionsUrl(stopDatalist.get(0), stopDatalist.get(stopDatalist.size()-1));
                        DownloadTask downloadTask = new DownloadTask();
                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }*/

                    int divisible = NotificationUtils.divisible(waypointData.size(),5);
                    int mod = NotificationUtils.mod(waypointData.size(),5);
                    if(mod > 0){
                        divisible = divisible+1;
                    }
                    if(divisible==0){
                        String url = getDirectionsUrl(stopDatalist.get(0), stopDatalist.get(stopDatalist.size()-1));
                        DownloadTask downloadTask = new DownloadTask();
                        // Start downloading json data from Google Directions API
                        downloadTask.execute(url);
                    }else {

                        Log.e("waypointData-->divisible", "" + divisible);
                        Log.e("waypointData-->mod", "" + mod);
                        int end_loop;
                        for (int k = 0; k < divisible; k++) {
                            int cntr = k * 5;
                            if (k == (divisible - 1) && mod!=0) {
                                end_loop = mod - 1;
                            } else {
                                end_loop = 5;
                            }
                            Log.e("waypointData-->cntr", "" + cntr);
                            waypoints_parts = "waypoints=optimize:true|";

                            LatLng p1 = null;
                            LatLng p2 = null;


                            for (int l = cntr; l <= (cntr + end_loop); l++) {
                                Log.e("waypointData-->l", "" + l);
                                if (l == cntr) {
                                    p1 = new LatLng(waypointData.get(l).latitude, waypointData.get(l).longitude);
                                }

                                if (l == (cntr + end_loop)) {
                                    p2 = new LatLng(waypointData.get(l).latitude, waypointData.get(l).longitude);
                                }

                                waypoints_parts += "" + waypointData.get(l).latitude + "," + waypointData.get(l).longitude + "|";


                            }

                            String url = getDirectionsUrlNew(p1, p2, waypoints_parts);
                            DownloadTask downloadTask = new DownloadTask();
                            // Start downloading json data from Google Directions API
                            downloadTask.execute(url);

                        }





                        Log.e("waypointData-->", "" + mod);

                        Log.e("listsize-->", "" + stopDatalist.size());
//                    hitRoute();
                    }

                    /*String url = getDirectionsUrl(stopDatalist.get(0), stopDatalist.get(stopDatalist.size()-1));
                    DownloadTask downloadTask = new DownloadTask();
                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);*/


                } else {
                    showMessage(data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponseScheduledetail> call, Throwable t) {
                dialog.dismiss();
                showMessage("Try again");
            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng sydney = new LatLng(-33.867, 151.206);

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //    here to request the missing permissions, and then overriding
            //    public void onRequestPermissionsResult(int requestCode, String[] permissions int[] grantResults)
            //    to handle the case where the user grants the permission. See the documentation
            //    for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
       /* googleMap.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));*/
        this.googleMap = googleMap;

    }

    private void showMessage(String s) {
//        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
        Toast toast = Toast.makeText(getActivity(),s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void hitRoute() {
        v2GetRouteDirection = new GMapV2GetRoute();
        FargmentNearByMap.GetRouteTask getRoute = new FargmentNearByMap.GetRouteTask();
        getRoute.execute();
    }

    private class GetRouteTask extends AsyncTask<String, Void, String> {
        String response = "";
        @Override
        protected String doInBackground(String... urls) {
            //Get All Route values
                start   =   stopDatalist.get(0);
                end     =   stopDatalist.get(stopDatalist.size()-1);

            source          = start;
            destination     = end;

            Log.e("start",""+source.latitude+" "+source.longitude);
            Log.e("end",""+destination.latitude+" "+destination.longitude);
            document = v2GetRouteDirection.getDocument(source, destination, GMapV2GetRoute.MODE_DRIVING);
            response = "Success";
            return response;

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.e("response", result);
            if (response.equalsIgnoreCase("Success")) {
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.addAll(coordList);
                polylineOptions
                        .width(10)
                        .color(Color.parseColor("#457DD7"));
                googleMap.addPolyline(polylineOptions);
            }
        }
    }





    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);

                // Changing the color polyline according to the mode

                lineOptions.color(Color.parseColor("#457DD7"));
            }

            if(result.size()<1){
//                Toast.makeText(getActivity(), "No Points", Toast.LENGTH_SHORT).show();
                return;
            }

            // Drawing polyline in the Google Map for the i-th route
            googleMap.addPolyline(lineOptions);
        }
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);
            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();
            // Connecting to url
            urlConnection.connect();
            // Reading data from url
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
            StringBuffer sb = new StringBuffer();
            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        }catch(Exception e){
            Log.d("Exception while downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }



    /** A method to download json data from url */
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

//        String waypoints = "waypoints=optimize:true|" + 26.449896 + "," + 74.639915 + "|"+ 26.100735 + "," + 74.319077 + "|";

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";

        // Travelling Mode
        String mode = "mode=driving&key=AIzaSyDK0RuyB-WgeYx9_XRrB3mq2iMhcHRtLLc";
        mMode = 0 ;

        Log.d("waypoints--->",waypoints);
        // Building the parameters to the web service
        String parameters = str_origin  +   "&" +   waypoints   +   "&" +str_dest   +   "&" +   sensor  +   "&" +   mode;
//        String parameters = str_origin  +  "&" +str_dest   +   "&" +   sensor  +   "&" +   mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.d("url--->",url);

        return url;
    }

    /** A method to download json data from url */
    private String getDirectionsUrlNew(LatLng origin,LatLng dest, String waypointsParts){
        String str_origin = "origin="+origin.latitude+","+origin.longitude;
        String str_dest = "destination="+dest.latitude+","+dest.longitude;
        String sensor = "sensor=false";
        String mode = "mode=driving&key=AIzaSyDK0RuyB-WgeYx9_XRrB3mq2iMhcHRtLLc";
        mMode = 0 ;

        Log.d("waypointsParts--->",waypointsParts);
        // Building the parameters to the web service
        String parameters = str_origin  +   "&" +   waypointsParts   +   "&" +str_dest   +   "&" +   sensor  +   "&" +   mode;
        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.d("url--->",url);

        return url;
    }
}
