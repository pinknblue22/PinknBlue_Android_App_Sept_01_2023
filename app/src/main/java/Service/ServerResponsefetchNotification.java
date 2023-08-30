package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 2/2/2018.
 */

public class ServerResponsefetchNotification {
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

    public List<Data> getDatalist() {
        return datalist;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public class Data
    {
        @SerializedName("date")
        private String date;
        @SerializedName("message")
        private String Message;
        @SerializedName("id")
        private String NID;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
        public String getMessage ()
        {
            return Message;
        }

        public void setMessage (String Message)
        {
            this.Message = Message;
        }

        public String getNID ()
        {
            return NID;
        }

        public void setNID (String NID)
        {
            this.NID = NID;
        }

    }
}
