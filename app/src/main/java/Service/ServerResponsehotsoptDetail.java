package Service;

import com.google.gson.annotations.SerializedName;

/**
        * Created by eweb-a1-pc-14 on 1/8/2018.
        */

public class ServerResponsehotsoptDetail {
    @SerializedName("Business_name")
    private String Business_name;
    @SerializedName("message")
    private String message;
    @SerializedName("category_name")
    private String category_name;
    @SerializedName("location")
    private String location;
    @SerializedName("email")
    private String email;
    @SerializedName("business_image")
    private String business_image;
    @SerializedName("contact_name")
    private String contact_name;
    @SerializedName("business_id")
    private String business_id;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @SerializedName("website_link")
    private String website_link;
    @SerializedName("contact_title")
    private String contact_title;
    @SerializedName("code")
    private String code;
    @SerializedName("Contact_number")
    private String Contact_number;
    @SerializedName("latitude")
    private String latitude;
    @SerializedName("longitude")
    private String longitude;

    public String getBusiness_name ()
    {
        return Business_name;
    }

    public void setBusiness_name (String Business_name)
    {
        this.Business_name = Business_name;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getCategory_name ()
    {
        return category_name;
    }

    public void setCategory_name (String category_name)
    {
        this.category_name = category_name;
    }

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getBusiness_image ()
    {
        return business_image;
    }

    public void setBusiness_image (String business_image)
    {
        this.business_image = business_image;
    }

    public String getContact_name ()
    {
        return contact_name;
    }

    public void setContact_name (String contact_name)
    {
        this.contact_name = contact_name;
    }

    public String getBusiness_id ()
    {
        return business_id;
    }

    public void setBusiness_id (String business_id)
    {
        this.business_id = business_id;
    }

    public String getWebsite_link ()
    {
        return website_link;
    }

    public void setWebsite_link (String website_link)
    {
        this.website_link = website_link;
    }

    public String getContact_title ()
    {
        return contact_title;
    }

    public void setContact_title (String contact_title)
    {
        this.contact_title = contact_title;
    }

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

    public String getContact_number ()
    {
        return Contact_number;
    }

    public void setContact_number (String Contact_number)
    {
        this.Contact_number = Contact_number;
    }
}
