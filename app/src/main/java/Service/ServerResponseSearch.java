package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 2/15/2018.
 */

public class ServerResponseSearch {
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

    public class Data
    {
        public String getRoute_insert_id() {
            return route_insert_id;
        }

        public void setRoute_insert_id(String route_insert_id) {
            this.route_insert_id = route_insert_id;
        }

        @SerializedName("Route_id")
        private String Route_id;
        @SerializedName("route_insert_id")
        private String route_insert_id;
        @SerializedName("Starting_location")
        private String Starting_location;
        @SerializedName("End_location")
        private String End_location;
        @SerializedName("Starting_location_name")
        private String Starting_location_name;

        public String getStarting_location_name() {
            return Starting_location_name;
        }

        public void setStarting_location_name(String starting_location_name) {
            Starting_location_name = starting_location_name;
        }

        public String getEnd_location_name() {
            return End_location_name;
        }

        public void setEnd_location_name(String end_location_name) {
            End_location_name = end_location_name;
        }

        @SerializedName("End_location_name")
        private String End_location_name;

        public String getRoute_id ()
        {
            return Route_id;
        }

        public void setRoute_id (String Route_id)
        {
            this.Route_id = Route_id;
        }

        public String getStarting_location ()
        {
            return Starting_location;
        }

        public void setStarting_location (String Starting_location)
        {
            this.Starting_location = Starting_location;
        }

        public String getEnd_location ()
        {
            return End_location;
        }

        public void setEnd_location (String End_location)
        {
            this.End_location = End_location;
        }


    }
}
