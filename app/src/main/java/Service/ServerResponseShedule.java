package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 1/11/2018.
 */

public class ServerResponseShedule {

    @SerializedName("Stops")
    private List<Stops> StopList;
    @SerializedName("No_schedule")
    private String No_schedule;
    @SerializedName("message")
    private String message;
    @SerializedName("Starting_location")
    private String Starting_location;
    @SerializedName("code")
    private String code;
    @SerializedName("End_location")
    private String End_location;
    @SerializedName("Route_ID")
    private String Route_ID;


    public List<Stops> getStopList() {
        return StopList;
    }
    public String getNo_schedule ()
    {
        return No_schedule;
    }

    public void setNo_schedule (String No_schedule)
    {
        this.No_schedule = No_schedule;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getStarting_location ()
    {
        return Starting_location;
    }

    public void setStarting_location (String Starting_location)
    {
        this.Starting_location = Starting_location;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getEnd_location ()
    {
        return End_location;
    }

    public void setEnd_location (String End_location)
    {
        this.End_location = End_location;
    }

    public String getRoute_ID ()
    {
        return Route_ID;
    }

    public void setRoute_ID (String Route_ID)
    {
        this.Route_ID = Route_ID;
    }

    public class Stops
    {
        @SerializedName("end_time")
        private String end_time;
        @SerializedName("Start_time")
        private String Start_time;
        @SerializedName("Stop_id")
        private String Stop_id;

        public String getEnd_time ()
        {
            return end_time;
        }

        public void setEnd_time (String end_time)
        {
            this.end_time = end_time;
        }

        public String getStart_time ()
        {
            return Start_time;
        }

        public void setStart_time (String Start_time)
        {
            this.Start_time = Start_time;
        }

        public String getStop_id ()
        {
            return Stop_id;
        }

        public void setStop_id (String Stop_id)
        {
            this.Stop_id = Stop_id;
        }

    }
}
