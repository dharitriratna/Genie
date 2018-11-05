package com.example.user.genie.client;

import com.example.user.genie.ObjectNew.GenPNRResponse;
import com.example.user.genie.ObjectNew.ServiceImage;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by RatnaDev008 on 11/5/2018.
 */

public interface ApiInterface {
    @GET("pnr-status/pnr/{pnrno}/apikey/{apino}/")
    Call<GenPNRResponse> getPNRResponse(@Path("pnrno") String pnrno, @Path("apino") String apino);

    @GET("service/getservice")
    Call<ServiceImage> getImageResponse();
}
