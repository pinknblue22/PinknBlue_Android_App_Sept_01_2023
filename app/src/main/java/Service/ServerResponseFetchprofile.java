package Service;

/**
 * Created by eweb-a1-pc-14 on 1/9/2018.
 */

public class ServerResponseFetchprofile {

    private String UserType;

    private String message;

    private String first_name;

    private String email;

    private String profile_image;

    private String UID;

    private String last_name;

    private String code;

    public String getUserType ()
    {
        return UserType;
    }

    public void setUserType (String UserType)
    {
        this.UserType = UserType;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getProfile_image ()
    {
        return profile_image;
    }

    public void setProfile_image (String profile_image)
    {
        this.profile_image = profile_image;
    }

    public String getUID ()
    {
        return UID;
    }

    public void setUID (String UID)
    {
        this.UID = UID;
    }

    public String getLast_name ()
    {
        return last_name;
    }

    public void setLast_name (String last_name)
    {
        this.last_name = last_name;
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
