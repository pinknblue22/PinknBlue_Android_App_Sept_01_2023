package Service;

import com.google.gson.annotations.SerializedName;

/**
 * Created by eweb-a1-pc-14 on 1/4/2018.
 */

public class ServerResponseForgotpass {
    @SerializedName("message")
    private String message;
    @SerializedName("code")
    private String code;

    public String getOtpCode() {
        return otpCode;
    }

    public void setOtpCode(String otpCode) {
        this.otpCode = otpCode;
    }

    @SerializedName("otpCode")
    private String otpCode;
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
}
