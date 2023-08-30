package Service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eweb-a1-pc-14 on 1/4/2018.
 */

public class ServerResponseLoginUser {
    @SerializedName("message")
    private String message;
    @SerializedName("user_type")
    private String user_type;
    @SerializedName("email")
    private String email;
    @SerializedName("device_type")
    private String device_type;
    @SerializedName("name")
    private String name;
    @SerializedName("device_id")
    private String device_id;
    @SerializedName("user_id")
    private String user_id;



    @SerializedName("code")
    private String code;
    @SerializedName("routedetails")
    private String routedetails;

    public String getRoutedetails() {
        return routedetails;
    }

    public void setRoutedetails(String routedetails) {
        this.routedetails = routedetails;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getFeaturebundle() {
        return featurebundle;
    }

    public void setFeaturebundle(String featurebundle) {
        this.featurebundle = featurebundle;
    }

    @SerializedName("map")
    private String map;
    @SerializedName("notifications")
    private String notifications;
    @SerializedName("featurebundle")
    private String featurebundle;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getDevice_type ()
    {
        return device_type;
    }

    public void setDevice_type (String device_type)
    {
        this.device_type = device_type;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDevice_id ()
    {
        return device_id;
    }

    public void setDevice_id (String device_id)
    {
        this.device_id = device_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }
}
