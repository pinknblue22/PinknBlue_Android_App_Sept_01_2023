package Service;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by eweb-a1-pc-14 on 1/8/2018.
 */

public class ServerResponseCategory {
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
        @SerializedName("CategoryName")
        private String CategoryName;
        @SerializedName("CategoryID")
        private String CategoryID;
        @SerializedName("CategoryStatus")
        private String CategoryStatus;

        public String getCheckBoxValue() {
            return checkBoxValue;
        }

        public void setCheckBoxValue(String checkBoxValue) {
            this.checkBoxValue = checkBoxValue;
        }

        private String checkBoxValue="";

        public String getCategoryName ()
        {
            return CategoryName;
        }

        public void setCategoryName (String CategoryName)
        {
            this.CategoryName = CategoryName;
        }

        public String getCategoryID ()
        {
            return CategoryID;
        }

        public void setCategoryID (String CategoryID)
        {
            this.CategoryID = CategoryID;
        }

        public String getCategoryStatus ()
        {
            return CategoryStatus;
        }

        public void setCategoryStatus (String CategoryStatus)
        {
            this.CategoryStatus = CategoryStatus;
        }
    }
}
