package Service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by eweb-a1-pc-14 on 1/4/2018.
 */

public interface Interface {

    @FormUrlEncoded
    @POST("webservices/register")
    Call<ServerResposeRegisteruser> register(@Field("name") String name,@Field("email") String email,@Field("password") String password,@Field("device_id") String device_id,
                                             @Field("device_type") String device_type);

    @FormUrlEncoded
    @POST("webservices/login")
    Call<ServerResponseLoginUser> login(@Field("email") String email,@Field("password") String password,@Field("device_id") String device_id,
                                        @Field("device_type") String device_type);
    @FormUrlEncoded
    @POST("webservices/logout")
    Call<ServerResposeRegisteruser> logout(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("webservices/UserAuthController/ForgetPassword")
    Call<ServerResponseForgotpass> ForgetPassword(@Field("email") String email);

    @FormUrlEncoded
    @POST("webservices/reset-password")
    Call<ServerResposeRegisteruser> reset_password(@Field("otpcode") String otpcode,@Field("newpassword") String newpassword,@Field("email") String email);

    @GET("webservices/hotspot-list")
    Call<ServerResponseHotSpot> hotspot_list();

    @FormUrlEncoded
    @POST("webservices/hotspot-details")
    Call<ServerResponsehotsoptDetail> hotspot_details(@Field("hotspot_id") String hotspot_id);

    @GET("webservices/categories-list")
    Call<ServerResponseCategory> categories_list();

    @GET("webservices/all-route")
    Call<ServerResponseAllroutes> all_route();

    @FormUrlEncoded
    @POST("webservices/user-profile")
    Call<ServerResponseFetchprofile> user_profile(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("webservices/edit-profile")
    Call<ServerResposeRegisteruser> edit_profile(@Field("user_id") String user_id,@Field("name") String name);

    @FormUrlEncoded
    @POST("webservices/change-password")
    Call<ServerResposeRegisteruser> change_password(@Field("user_id") String user_id,@Field("new_password") String new_password,@Field("old_password") String old_password);

    @FormUrlEncoded
    @POST("webservices/get-route-schedule")
    Call<ServerResponseShedule> get_route_schedule(@Field("route_id") String route_id);

    @FormUrlEncoded
    @POST("webservices/RouteController/GetScheduleFullDetails")
    Call<ServerResponseScheduledetail> GetScheduleFullDetails(@Field("stop_id") String stop_id);

    @FormUrlEncoded
    @POST("webservices/RouteController/GetScheduleFullDetailsByRouteId")
    Call<ServerResponseScheduledetail> GetScheduleFullDetailsByRouteId(
            @Field("route_id") String stop_id,
            @Field("lat") String latitude,
            @Field("long") String longitude
            );

    @FormUrlEncoded
    @POST("webservices/NotificationController/ContactUs")
    Call<ServerResposeRegisteruser> ContactUs(@Field("UiD") String UiD,@Field("Message") String Message);

    @FormUrlEncoded
    @POST("webservices/RouteController/FilterBusinessByCategory")
    Call<ServerResponseHotSpot> FilterBusinessByCategory(@Field("categoryName") String categoryName);

    @FormUrlEncoded
    @POST("webservices/NotificationController/NotificationsByUserID")
    Call<ServerResponsefetchNotification> fechNotification(@Field("UID") String UID);

    @FormUrlEncoded
    @POST("webservices/NotificationController/DeleteNotification")
    Call<ServerResposeRegisteruser> DeleteNotification(@Field("UId") String UId);

/*    @FormUrlEncoded
    @POST("webservices/SearchController/SearchRoute")
    Call<ServerResponseSearch> SearchRoute(@Field("start_location") String start_location);*/
    @FormUrlEncoded
    @POST("webservices/all-route-search")
    Call<ServerResponseSearch> SearchRoute(@Field("start_location") String start_location);


    @FormUrlEncoded
    @POST("webservices/hotspot-list-search")
    Call<ServerResponseHotSpot> SearchRouteNew(@Field("start_location") String start_location);

    @FormUrlEncoded
    @POST("webservices/NotificationController/SendNotification")
    Call<ServerResposeRegisteruser> SendNotification(@Field("UserID") String UserID,@Field("GoogleId") String GoogleId);


    @FormUrlEncoded
    @POST("webservices/RateApp")
    Call<ServerResposeRegisteruser> rateApp(@Field("user_id") String user_id,@Field("rating") String rating);

    @FormUrlEncoded
    @POST("webservices/CreateOrder")
    Call<ServerResponseCreateOrder> creatOrder(@Field("user_id") String user_id,
                                               @Field("amount") String amount,
                                               @Field("plan_id") String plan_id);
    @FormUrlEncoded
    @POST("webservices/CompleteOrder")
    Call<ServerResponseCompleteOrder> completeOrder(@Field("user_id") String user_id,
                                               @Field("order_id") String order_id,
                                               @Field("data_descriptor") String data_descriptor,
                                               @Field("data_value") String data_value);
    @FormUrlEncoded
    @POST("webservices/CancelOrder")
    Call<ServerResponseCancelOrder> cancelOrder(@Field("user_id") String user_id,
                                               @Field("order_id") String order_id,
                                               @Field("status") String status);
}
