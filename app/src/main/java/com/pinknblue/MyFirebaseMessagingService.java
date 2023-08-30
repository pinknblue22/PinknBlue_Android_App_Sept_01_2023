package com.pinknblue;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import uitls.Constant;
import uitls.DbHandler;
import uitls.NotificationUtils;


/**
 * Created by eweb-a1-pc-14 on 9/1/2016.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    SharedPreferences preference;
    private String loginUserId="";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        preference = getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
        loginUserId = preference.getString("user_id", "");

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }


    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.e(TAG, "Refreshed token: " + token);

        preference =getSharedPreferences("device", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString("deviceId",""+token);
        editor.commit();
       //deviceId=refreshedToken;

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    private void sendRegistrationToServer(String token) {
        // Add custom implementation, as needed.
        Log.e(TAG, "sendRegistrationToServer: " + token);
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Constant.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }

    private void handleDataMessage(JSONObject json) {
        Log.e(TAG, "push json: " + json.toString());

        try {
            JSONObject data = json.getJSONObject("data");

            String title = data.getString("title");
            String message = data.getString("message");
            String dateTime = data.getString("payload");
            boolean isBackground = data.getBoolean("is_background");
            String imageUrl = data.getString("image");
            String timestamp = data.getString("timestamp");
//            JSONObject payload = data.getJSONObject("payload");

            Log.e(TAG, "title: " + title);
            Log.e(TAG, "message: " + message);
            Log.e(TAG, "isBackground: " + isBackground);
//            Log.e(TAG, "payload: " + payload.toString());
            Log.e(TAG, "imageUrl: " + imageUrl);
            Log.e(TAG, "timestamp: " + timestamp);

            DbHandler dbHandler =   new DbHandler(this);
            dbHandler.addValues(message, title);




            /*if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                // app is in foreground, broadcast the push message
                Intent pushNotification = new Intent(ApiList.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

                // play notification sound
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
            } else {*/
            // app is in background, show the notification in notification tray


      /*      Intent resultIntent = new Intent(getApplicationContext(), SplashActivity.class);
            resultIntent.putExtra("message", message);*/

            Intent resultIntent =   new Intent(getApplicationContext(), CommanActivity.class);
            resultIntent.putExtra("type","notification_detail");
            resultIntent.putExtra("from","pushnotifiation");
            resultIntent.putExtra("message",message);
            resultIntent.putExtra("time",dateTime);

            // check for image attachment
//            if (Application.getSharedPreferences().getString("IsLogin", "").equalsIgnoreCase("true")) {
//                if (TextUtils.isEmpty(imageUrl)) {

            if (!(loginUserId.equalsIgnoreCase(""))){
                showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
            }


                /*} else {
                    // image is present, show notification with image
                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
                }*/
//            }
//            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent);
    }

    /**
     * Showing notification with text and image
     */
    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}
/*
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Context context;
    private static final String TAG = "FCM Service";
    private SharedPreferences preference;
    private String loginUserId="",UserType=" ";

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // Check if message contains a data payload.
        Log.e(TAG, "Message data payload: " + remoteMessage.getData());
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e("FCM", "Notification Message Body: " + remoteMessage.getNotification().getBody());
        }if (remoteMessage.getData().containsKey("title") ){
            Log.e("message", remoteMessage.getData().get("title"));

            // eg. Server Send Structure data:{"post_id":"12345","post_title":"A Blog Post"}
            printdata(remoteMessage.getData().get("title"),remoteMessage.getData().get("message"));
        }

    }
    private void printdata(String message, String body) {
        Log.e("aaa",message);
        Notification(message,body);
     */
/*   JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(message);
            String sms=jsonObject.getString("message");
            String title=jsonObject.getString("title");
            String type=jsonObject.getString("type");

        } catch (JSONException e) {
            e.printStackTrace();
        }*//*


   }
   public void Notification(String title,String body) {
       preference = getSharedPreferences(Constant.Shared_Pref, Context.MODE_PRIVATE);
       UserType = preference.getString("user_type", "");
       if (UserType.equalsIgnoreCase("Paid")){
           Intent intent = new Intent(this, HomeActivity.class);
           intent.putExtra("title", title);
           intent.putExtra("text",body);
           intent.putExtra("type","paid");
           PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
           NotificationCompat.Builder builder = null;
           builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                   .setSmallIcon(R.mipmap.ic_launcher_round)
                   .setTicker(getString(R.string.app_name))
                   .setContentTitle(title)
                   .setContentText(body)
                   .setContentIntent(pIntent)
                   .setAutoCancel(true);
           NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
           notificationmanager.notify(1, builder.build());
       }else {

       }


   }
}*/
