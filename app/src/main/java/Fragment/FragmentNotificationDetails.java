package Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pinknblue.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Param
 */

public class FragmentNotificationDetails extends Fragment {

    TextView txt_msg,txt_time;
    private String message,time;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_notification_detail,container,false);

        initUI(view);

        return view;
    }


    private void initUI(View view) {
        txt_msg         =   (TextView) view.findViewById(R.id.txt_msg);
        txt_time         =   (TextView) view.findViewById(R.id.txt_time);

        message =   getArguments().getString("message");
        time    =   getArguments().getString("time");

        txt_msg.setText(message);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String Difference = null;
        try {
            Date oldDate = dateFormat.parse(time);
            Date currentDate = new Date();
            long diff = currentDate.getTime() - oldDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;
            if (oldDate.before(currentDate)) {
                if (days == 0) {
                    if (hours == 0) {
                        if (minutes == 0) {
                            Difference = "now";
                        } else {
                            Difference = minutes + " " + "mins" + " " + "ago";
                        }
                    } else {
                        Difference = hours + " " + "hours" + "  " + "ago";
                    }
                } else {
                    Difference = days + " " + "days" + " " + "ago";
                    //Difference=minutes+" "+"minutes"+" "+"ago";
                }

                Log.e("oldDate", "is previous date");
                // Difference = " seconds: " + seconds + " minutes: " + minutes + " hours: " + hours + " days: " + days;
                Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes + " hours: " + hours + " days: " + days);

            }

            // Log.e("toyBornTime", "" + toyBornTime);
        } catch (Exception e) {

        }


        txt_time.setText(Difference);


    }
}
