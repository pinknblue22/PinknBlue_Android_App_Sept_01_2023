package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 1/11/2018.
 */

public class ServerResponseScheduledetail {
    @SerializedName("message")
    private String message;
    @SerializedName("nearbyNode")
    private Integer nearbyNode;

    public Integer getNearbyNode() {
        return nearbyNode;
    }

    public void setNearbyNode(Integer nearbyNode) {
        this.nearbyNode = nearbyNode;
    }

    @SerializedName("stop_data")
    private List<Stop_data> stopdatalist;
    @SerializedName("hotspots")
    private List<Hotspots> hotspots;
    @SerializedName("end_time")
    private String end_time;
    @SerializedName("Starting_location")
    private String Starting_location;
    @SerializedName("code")
    private String code;
    @SerializedName("End_location")
    private String End_location;
    @SerializedName("Start_time")
    private String Start_time;
    @SerializedName("Route_id")
    private String Route_ID;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public List<Stop_data> getStopdatalist() {
        return stopdatalist;
    }


    public List<Hotspots> getHotspots() {
        return hotspots;
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

    public String getStart_time ()
    {
        return Start_time;
    }

    public void setStart_time (String Start_time)
    {
        this.Start_time = Start_time;
    }
    public String getRoute_ID ()
    {
        return Route_ID;
    }

    public void setRoute_ID (String Route_ID)
    {
        this.Route_ID = Route_ID;
    }


    public class Hotspots{
        @SerializedName("Business_name")
        private String Business_name;

        public String getBusiness_name() {
            return Business_name;
        }

        public void setBusiness_name(String business_name) {
            Business_name = business_name;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getBusiness_id() {
            return business_id;
        }

        public void setBusiness_id(String business_id) {
            this.business_id = business_id;
        }

        @SerializedName("latitude")

        private double latitude;
        @SerializedName("longitude")
        private double longitude;
        @SerializedName("business_id")
        private String business_id;
    }

    public class Stop_data
    {
        @SerializedName("stop_time")
        private String stop_time;
        @SerializedName("stop_Image")
        private String stop_Image;

        public String getOrig_address() {
            return orig_address;
        }

        public void setOrig_address(String orig_address) {
            this.orig_address = orig_address;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        @SerializedName("stop_address")

        private String stop_address;
        @SerializedName("orig_address")
        private String orig_address;
        @SerializedName("lat")
        private double latitude;
        @SerializedName("long")
        private double longitude;

        public String getStop_time ()
        {
            return stop_time;
        }

        public void setStop_time (String stop_time)
        {
            this.stop_time = stop_time;
        }

        public String getStop_Image ()
        {
            return stop_Image;
        }

        public void setStop_Image (String stop_Image)
        {
            this.stop_Image = stop_Image;
        }

        public String getStop_address ()
        {
            return stop_address;
        }

        public void setStop_address (String stop_address)
        {
            this.stop_address = stop_address;
        }


    }



}
