package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 1/9/2018.
 */

public class ServerResponseAllroutes {
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private List<Data> datalist;
    @SerializedName("code")
    private String code;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }


    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }


    public List<Data> getDatalist() {
        return datalist;
    }
    public class Data{

        @SerializedName("No_schedule")
        private String No_schedule;
        @SerializedName("Route_id")
        private String Route_id;
        @SerializedName("time_interval")
        private String time_interval;
        @SerializedName("end_time")
        private String end_time;
        @SerializedName("Starting_location")
        private String Starting_location;
        @SerializedName("start_am_pm")
        private String start_am_pm;
        @SerializedName("start_time")
        private String start_time;
        @SerializedName("route_insert_id")
        private String route_insert_id;
        @SerializedName("start_min")
        private String start_min;
        @SerializedName("interval_second")
        private String interval_second;
        @SerializedName("end_am_pm")
        private String end_am_pm;
        @SerializedName("End_location")
        private String End_location;
        @SerializedName("end_min")
        private String end_min;

        public String getNo_schedule ()
        {
            return No_schedule;
        }

        public void setNo_schedule (String No_schedule)
        {
            this.No_schedule = No_schedule;
        }

        public String getRoute_id ()
        {
            return Route_id;
        }

        public void setRoute_id (String Route_id)
        {
            this.Route_id = Route_id;
        }

        public String getTime_interval ()
        {
            return time_interval;
        }

        public void setTime_interval (String time_interval)
        {
            this.time_interval = time_interval;
        }

        public String getEnd_time ()
        {
            return end_time;
        }

        public void setEnd_time (String end_time)
        {
            this.end_time = end_time;
        }

        public String getStarting_location ()
        {
            return Starting_location;
        }

        public void setStarting_location (String Starting_location)
        {
            this.Starting_location = Starting_location;
        }

        public String getStart_am_pm ()
        {
            return start_am_pm;
        }

        public void setStart_am_pm (String start_am_pm)
        {
            this.start_am_pm = start_am_pm;
        }

        public String getStart_time ()
        {
            return start_time;
        }

        public void setStart_time (String start_time)
        {
            this.start_time = start_time;
        }

        public String getRoute_insert_id ()
        {
            return route_insert_id;
        }

        public void setRoute_insert_id (String route_insert_id)
        {
            this.route_insert_id = route_insert_id;
        }

        public String getStart_min ()
        {
            return start_min;
        }

        public void setStart_min (String start_min)
        {
            this.start_min = start_min;
        }

        public String getInterval_second ()
        {
            return interval_second;
        }

        public void setInterval_second (String interval_second)
        {
            this.interval_second = interval_second;
        }

        public String getEnd_am_pm ()
        {
            return end_am_pm;
        }

        public void setEnd_am_pm (String end_am_pm)
        {
            this.end_am_pm = end_am_pm;
        }

        public String getEnd_location ()
        {
            return End_location;
        }

        public void setEnd_location (String End_location)
        {
            this.End_location = End_location;
        }

        public String getEnd_min ()
        {
            return end_min;
        }

        public void setEnd_min (String end_min)
        {
            this.end_min = end_min;
        }

    }
}
