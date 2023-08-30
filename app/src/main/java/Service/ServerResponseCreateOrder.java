package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Param
 */

public class ServerResponseCreateOrder {

    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;
    @SerializedName("order_id")
    private String order_id;

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

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
