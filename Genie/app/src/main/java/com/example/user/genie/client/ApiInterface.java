package com.example.user.genie.client;

import com.example.user.genie.ObjectNew.CabResponse;
import com.example.user.genie.ObjectNew.GenPNRResponse;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.ObjectNew.ServiceImage;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by RatnaDev008 on 11/5/2018.
 */

public interface ApiInterface {
    @GET("pnr-status/pnr/{pnrno}/apikey/{apino}/")
    Call<GenPNRResponse> getPNRResponse(@Path("pnrno") String pnrno, @Path("apino") String apino);

    @GET("index.php/api/service/getservice")
    Call<ServiceImage> getImageResponse();

    @POST("api/service/cabService")
    @FormUrlEncoded
    Call<CabResponse> postCabBooking(@Field("user_id") int user_id,@Field("trip") int trip,@Field("from") String from,
                                     @Field("to") String to,@Field("traveldate") String traveldate,
                                     @Field("returndate") String returndate,@Field("time") String time);
    @GET("api/service/getRent")
    @Headers("Content-Type: application/json")
    Call<RentResponse> getRentResponse(@Query("user_id") int userid);
}
