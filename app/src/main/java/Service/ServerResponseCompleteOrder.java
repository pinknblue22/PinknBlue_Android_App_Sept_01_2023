package Service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Param
 */

public class ServerResponseCompleteOrder {

    @SerializedName("message")
    private String message;

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

    @SerializedName("code")

    private String code;

    @SerializedName("routedetails")
    private String routedetails;
    @SerializedName("map")
    private String map;
    @SerializedName("notifications")
    private String notifications;
    @SerializedName("featurebundle")
    private String featurebundle;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
