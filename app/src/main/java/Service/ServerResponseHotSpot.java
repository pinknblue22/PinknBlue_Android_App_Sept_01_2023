package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 1/8/2018.
 */

public class ServerResponseHotSpot {
   @SerializedName("message")
    private String message;

    public List<Data> getData() {
        return datalist;
    }
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
    public class Data{
        @SerializedName("Business_name")
        private String Business_name;
        @SerializedName("business_image")
        private String business_image;
        @SerializedName("business_id")
        private String business_id;

        public String getBusiness_name ()
        {
            return Business_name;
        }

        public void setBusiness_name (String Business_name)
        {
            this.Business_name = Business_name;
        }

        public String getBusiness_image ()
        {
            return business_image;
        }

        public void setBusiness_image (String business_image)
        {
            this.business_image = business_image;
        }

        public String getBusiness_id ()
        {
            return business_id;
        }

        public void setBusiness_id (String business_id)
        {
            this.business_id = business_id;
        }
    }
}
