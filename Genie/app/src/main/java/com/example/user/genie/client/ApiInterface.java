package com.example.user.genie.client;

import com.example.user.genie.Model.BeneficiaryDetailsResponse;
import com.example.user.genie.ObjectNew.BeneficiaryDeleteResponse;
import com.example.user.genie.ObjectNew.BeneficiaryDeleteValidateResponse;
import com.example.user.genie.ObjectNew.BeneficiaryRegisterResponse;
import com.example.user.genie.ObjectNew.BeneficiaryValidateResponse;
import com.example.user.genie.ObjectNew.BrowsePlansResponse;
import com.example.user.genie.ObjectNew.BusCitesResponse;
import com.example.user.genie.ObjectNew.BusToCitiesResponse;
import com.example.user.genie.ObjectNew.CabResponse;
import com.example.user.genie.ObjectNew.DatacardRechargeResponse;
import com.example.user.genie.ObjectNew.DatacardResponse;
import com.example.user.genie.ObjectNew.FundTransferResponse;
import com.example.user.genie.ObjectNew.FundTransferStatusResponse;
import com.example.user.genie.ObjectNew.GenPNRResponse;
import com.example.user.genie.ObjectNew.GetInsuranseResponse;
import com.example.user.genie.ObjectNew.InsuranceDetailResponse;
import com.example.user.genie.ObjectNew.InsurancePaymentResponse;
import com.example.user.genie.ObjectNew.JobResponse;
import com.example.user.genie.ObjectNew.LandlineResponse;
import com.example.user.genie.ObjectNew.LandlineResponseModel;
import com.example.user.genie.ObjectNew.MovieCityResponse;
import com.example.user.genie.ObjectNew.MovieListResponse;
import com.example.user.genie.ObjectNew.PlaceCabResponse;
import com.example.user.genie.ObjectNew.RemiterDetailsResponse;
import com.example.user.genie.ObjectNew.RemiterRegisterResponse;
import com.example.user.genie.ObjectNew.RentResponse;
import com.example.user.genie.ObjectNew.ResendOTPResponse;
import com.example.user.genie.ObjectNew.SellResponse;
import com.example.user.genie.ObjectNew.ServiceImage;
import com.example.user.genie.ObjectNew.getDataCardCircle;
import com.example.user.genie.ObjectNew.getDataCardOperatorResponse;
import com.example.user.genie.helper.RegPrefManager;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("api/service/sell")
    Call<SellResponse> postSellResponse(@Part MultipartBody.Part file, @Part("user_id")RequestBody user_id,
                                        @Part("category") RequestBody category, @Part("price") RequestBody price,
                                        @Part("description") RequestBody description,@Part("phone") RequestBody phone,
                                        @Part("address") RequestBody address,
                                        @Part("latitude") RequestBody latitude,
                                        @Part("longitude") RequestBody longitude);
    @Multipart
    @POST("api/service/job")
    Call<JobResponse> uploadFile(@Part MultipartBody.Part file, @Part("user_id") RequestBody user_id,
                                 @Part("name") RequestBody name, @Part("number") RequestBody number,
                                 @Part("highest_qulification") RequestBody highest_qulification,
                                 @Part("desc") RequestBody desc,
                                 @Part("experience")RequestBody experience,
                                 @Part("last_company") RequestBody last_company);

    @GET("index.php/api/service/getpin")
    Call<PlaceCabResponse> getCabServiceLocation();

    @GET("api/service/getdatacard")
    Call<getDataCardOperatorResponse> getDataCardOperator();

    @GET("api/service/getcircle")
    Call<getDataCardCircle> getDataCardCircle();

    @POST("api/service/mobile_dth_datacard_recharge")
    @FormUrlEncoded
    Call<DatacardResponse> postDatacardRecharge(@Field("user_id") int user_id, @Field("customer_id") String customer_id,
                                                @Field("operator") String operator,
                                                @Field("circle") int circle, @Field("amount") int amount);
    @GET("api/service/getlandline")
    Call<LandlineResponseModel> getLandlineOperator();


    @GET("api/service/get_mobileoperator")
    Call<LandlineResponseModel> get_mobileoperator();


   // @POST("api/service/Postpaidmobile_Landline_Broadband")
    @POST("api/service/Postpaidmobile_Landline_Broadband_recharge")

    @FormUrlEncoded
    Call<LandlineResponse> postLandlineRecharge(@Field("user_id") int user_id, @Field("customer_id") String customer_id,
                                                @Field("operator") String operator,
                                                @Field("circle") int circle, @Field("amount") int amount,
                                                @Field("account_number") String account_number,@Field("std_code") String std_code);




    @GET("api/service/getallinsurnce")
    Call<GetInsuranseResponse> getAllInsurance();
    @POST("api/service/verify_insuranceDetails")
    @FormUrlEncoded
    Call<InsuranceDetailResponse> postVerifyInsurance(@Field("user_id") String  user_id, @Field("id") String id,
                                                      @Field("policyNo") String policyNo,
                                                      @Field("dob") String dob);
    @POST("api/service/insurancePayment")
    @FormUrlEncoded
    Call<InsurancePaymentResponse> postInsurancePayment(@Field("user_id") String  user_id, @Field("req_id") String req_id,
                                                       @Field("policyNo") String policyNo);
    @POST("api/service/remiterRegistation")
    @FormUrlEncoded
    Call<RemiterRegisterResponse> postRemiterRegister(@Field("phone") String  phone, @Field("name") String name,
                                                       @Field("pincode") String pincode);

    @POST("api/service/BeneficiaryRegistration")
    @FormUrlEncoded
    Call<BeneficiaryRegisterResponse> postBeneficiaryRegister(@Field("RemitterID") String  RemitterID, @Field("name") String name,
                                                          @Field("phone") String phone,@Field("ifsc") String ifsc,
                                                              @Field("Account") String Account);
    @POST("api/service/BeneficiaryRegistration_ResendOTP")
    @FormUrlEncoded
    Call<ResendOTPResponse> postResendOTP(@Field("RemitterID") String  RemitterID, @Field("BeneficiaryID") String BeneficiaryID);

    @POST("api/service/BeneficiaryRegistration_Validate")
    @FormUrlEncoded
    Call<BeneficiaryValidateResponse> postBeneficiaryValidate(@Field("RemitterID") String  RemitterID,
                                                              @Field("BeneficiaryID") String BeneficiaryID,
                                                              @Field("otp") String otp);

    @POST("api/service/RemitterDetails")
    @FormUrlEncoded
    Call<RemiterDetailsResponse> postRemiterDetails(@Field("remitter_phone") String  remitter_phone);


    @GET("index.php/api/service/Bus_GetOrigin?InputParameter")
    Call<BusCitesResponse> getBusCities();

    @POST("api/service/Bus_GetDestination")
    @FormUrlEncoded
    Call<BusToCitiesResponse> postDestinationDetails(@Field("InputParameter") String  InputParameter);

    @POST("api/service/plan_fetch")
    @FormUrlEncoded
    Call<BrowsePlansResponse> postPlan_Fetch(@Field("user_id") String customerId,
                                             @Field("phone") String  phoneNumber,
                                             @Field("operator")String operatorCode,
                                             @Field("circle") String circleCode);



    @POST("api/service/BeneficiaryDelete")
    @FormUrlEncoded
    Call<BeneficiaryDeleteResponse> postBeneficiaryDelete(@Field("RemitterID") String  RemitterID,
                                                          @Field("BeneficiaryID") String  BeneficiaryID);
    @POST("api/service/BeneficiaryDelete_Validate")
    @FormUrlEncoded
    Call<BeneficiaryDeleteValidateResponse> postBeneficiaryDeleteValidate(@Field("RemitterID") String  RemitterID,
                                                                          @Field("BeneficiaryID") String  BeneficiaryID,
                                                                          @Field("otp") String otp);
    @POST("api/service/FundTransfer")
    @FormUrlEncoded
    Call<FundTransferResponse> postFundTransfer(@Field("remitter_mobile") String  remitter_mobile,
                                                @Field("BeneficiaryID") String  BeneficiaryID,
                                                @Field("amount") String amount, @Field("mode") String mode);

    @POST("api/service/FundTransfer_Status")
    @FormUrlEncoded
    Call<FundTransferStatusResponse> postFundTransferStatus(@Field("cyrus_id") String  cyrus_id);

    @GET("api/service/MovieCityFind ")
    Call<MovieCityResponse> getMoviesCity();

    @POST("api/service/GetMovieList")
    @FormUrlEncoded
    Call<MovieListResponse> postMovieList(@Field("city_id") String  city_id);

}
