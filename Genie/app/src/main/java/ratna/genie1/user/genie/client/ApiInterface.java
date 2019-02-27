package ratna.genie1.user.genie.client;

import ratna.genie1.user.genie.ObjectNew.AllOrdersResponse;
import ratna.genie1.user.genie.ObjectNew.ApproveResponse;
import ratna.genie1.user.genie.ObjectNew.BeneficiaryDeleteResponse;
import ratna.genie1.user.genie.ObjectNew.BeneficiaryDeleteValidateResponse;
import ratna.genie1.user.genie.ObjectNew.BeneficiaryRegisterResponse;
import ratna.genie1.user.genie.ObjectNew.BeneficiaryValidateResponse;
import ratna.genie1.user.genie.ObjectNew.BrowsePlansResponse;
import ratna.genie1.user.genie.ObjectNew.BusCitesResponse;
import ratna.genie1.user.genie.ObjectNew.BusToCitiesResponse;
import ratna.genie1.user.genie.ObjectNew.CabResponse;
import ratna.genie1.user.genie.ObjectNew.DashboardResponse;
import ratna.genie1.user.genie.ObjectNew.DatacardResponse;
import ratna.genie1.user.genie.ObjectNew.DeleteResponse;
import ratna.genie1.user.genie.ObjectNew.FSESignupResponse;
import ratna.genie1.user.genie.ObjectNew.FSEUpdateResponse;
import ratna.genie1.user.genie.ObjectNew.FundTransferResponse;
import ratna.genie1.user.genie.ObjectNew.FundTransferStatusResponse;
import ratna.genie1.user.genie.ObjectNew.GenPNRResponse;
import ratna.genie1.user.genie.ObjectNew.GetInsuranseResponse;
import ratna.genie1.user.genie.ObjectNew.GetProfileResponse;
import ratna.genie1.user.genie.ObjectNew.HomeGroceryResponse;
import ratna.genie1.user.genie.ObjectNew.InsuranceDetailResponse;
import ratna.genie1.user.genie.ObjectNew.InsurancePaymentResponse;
import ratna.genie1.user.genie.ObjectNew.JobResponse;
import ratna.genie1.user.genie.ObjectNew.LandlineResponse;
import ratna.genie1.user.genie.ObjectNew.LandlineResponseModel;
import ratna.genie1.user.genie.ObjectNew.LoginResponse;
import ratna.genie1.user.genie.ObjectNew.MovieCityResponse;
import ratna.genie1.user.genie.ObjectNew.MovieListResponse;
import ratna.genie1.user.genie.ObjectNew.MyWalletResponse;
import ratna.genie1.user.genie.ObjectNew.OperatorFinderResponse;
import ratna.genie1.user.genie.ObjectNew.PlaceCabResponse;
import ratna.genie1.user.genie.ObjectNew.RemiterDetailsResponse;
import ratna.genie1.user.genie.ObjectNew.RemiterRegisterResponse;
import ratna.genie1.user.genie.ObjectNew.RemitterValidateResponse;
import ratna.genie1.user.genie.ObjectNew.RentResponse;
import ratna.genie1.user.genie.ObjectNew.RequestResponse;
import ratna.genie1.user.genie.ObjectNew.ResendOTPResponse;
import ratna.genie1.user.genie.ObjectNew.RetailerSignupResponse;
import ratna.genie1.user.genie.ObjectNew.RetailerUpdateResponse;
import ratna.genie1.user.genie.ObjectNew.SellResponse;
import ratna.genie1.user.genie.ObjectNew.ServiceImage;
import ratna.genie1.user.genie.ObjectNew.UpdateImageResponse;
import ratna.genie1.user.genie.ObjectNew.WalletTotalBalanceResponse;
import ratna.genie1.user.genie.ObjectNew.getDataCardCircle;
import ratna.genie1.user.genie.ObjectNew.getDataCardOperatorResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
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


    @GET("api/user/getWalletDistributorApprove")
    @Headers("Content-Type: application/json")
    Call<RequestResponse> getRequestResponse(@Query("user_id") int user_id);


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

    @GET("api/service/getOrderDetailsById")
    @Headers("Content-Type: application/json")
    Call<AllOrdersResponse> getAllOrderResponse(@Query("user_id") int userid,
                                                @Query("service_id") String service_id);

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

    @POST("https://genieservice.in/api/service/mobile_dth_datacard_recharge1")
    @FormUrlEncoded
    Call<DatacardResponse> postDatacardRecharge(@Field("user_id") int user_id, @Field("customer_id") String customer_id,
                                                @Field("operator") String operator,
                                                @Field("circle") int circle, @Field("amount") int amount);
    @GET("api/service/getlandline")
    Call<LandlineResponseModel> getLandlineOperator();


    @GET("api/service/get_mobileoperator")
    Call<LandlineResponseModel> get_mobileoperator();




   // @POST("api/service/Postpaidmobile_Landline_Broadband")
    @POST("api/service/Postpaidmobile_Landline_Broadband_recharge1")

    @FormUrlEncoded
    Call<LandlineResponse> postLandlineRecharge(@Field("user_id") int user_id, @Field("customer_id") String customer_id,
                                                @Field("operator") String operator,
                                                @Field("circle") int circle, @Field("amount") int amount,
                                                @Field("account_number") String account_number,@Field("std_code") String std_code);

    @POST("api/service/operatorFinder")
    Call<OperatorFinderResponse>postOperatorFinder(@Field("phone") String phone);


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


    @POST("api/user/distributorDashboard")
    @FormUrlEncoded
    Call<DashboardResponse> postDashboard(@Field("user_id") String  user_id, @Field("group_id") String group_id,
                                                 @Field("date") String date, @Field("end_date") String end_date);

    @POST("api/service/remiterRegistation")
    @FormUrlEncoded
    Call<RemiterRegisterResponse> postRemiterRegister(@Field("user_id") String user_id,@Field("phone") String  phone, @Field("name") String name,@Field("surname") String surname,
                                                       @Field("pincode") String pincode);

    @POST("api/service/BeneficiaryRegistration")
    @FormUrlEncoded
    Call<BeneficiaryRegisterResponse> postBeneficiaryRegister(@Field("user_id") String user_id,@Field("RemitterID") String  RemitterID, @Field("name") String name,
                                                          @Field("phone") String phone,@Field("ifsc") String ifsc,
                                                              @Field("Account") String Account);


    @POST("api/service/RemitterRegistration_Validate")
    @FormUrlEncoded
    Call<RemitterValidateResponse>postRemitterValidate(@Field("user_id") String user_id,@Field("phone") String phone, @Field("RemitterID") String RemitterID,
                                                             @Field("OTP") String OTP);


    @POST("api/service/BeneficiaryRegistration_ResendOTP")
    @FormUrlEncoded
    Call<ResendOTPResponse> postResendOTP(@Field("user_id") String user_id, @Field("RemitterID") String  RemitterID, @Field("BeneficiaryID") String BeneficiaryID);

    @POST("api/service/BeneficiaryRegistration_Validate")
    @FormUrlEncoded
    Call<BeneficiaryValidateResponse> postBeneficiaryValidate(@Field("user_id") String user_id, @Field("RemitterID") String  RemitterID,
                                                              @Field("BeneficiaryID") String BeneficiaryID,
                                                              @Field("otp") String otp);

    @POST("api/service/RemitterDetails")
    @FormUrlEncoded
    Call<RemiterDetailsResponse> postRemiterDetails(@Field("user_id")String user_id,@Field("remitter_phone") String  remitter_phone);


    @GET("index.php/api/service/Bus_GetOrigin?InputParameter")
    Call<BusCitesResponse> getBusCities();

    @POST("api/service/Bus_GetDestination")
    @FormUrlEncoded
    Call<BusToCitiesResponse> postDestinationDetails(@Field("InputParameter") String  InputParameter);

    @POST("api/service/plan_fetch")
    @FormUrlEncoded
    Call<BrowsePlansResponse> postPlan_Fetch(@Field("user_id") String user_id,
                                             @Field("phone") String  phone,
                                             @Field("operator")String operator,
                                             @Field("circle") String circle,
                                             @Field("rctype") String rctype);




    @POST("api/service/BeneficiaryDelete")
    @FormUrlEncoded
    Call<BeneficiaryDeleteResponse> postBeneficiaryDelete(@Field("user_id")String user_id,@Field("RemitterID") String  RemitterID,
                                                          @Field("BeneficiaryID") String  BeneficiaryID);
    @POST("api/service/BeneficiaryDelete_Validate")
    @FormUrlEncoded
    Call<BeneficiaryDeleteValidateResponse> postBeneficiaryDeleteValidate(@Field("user_id")String user_id,@Field("RemitterID") String  RemitterID,
                                                                          @Field("BeneficiaryID") String  BeneficiaryID,
                                                                          @Field("otp") String otp);
    @POST("api/service/FundTransfer")
    @FormUrlEncoded
    Call<FundTransferResponse> postFundTransfer(@Field("user_id")String user_id,@Field("remitter_mobile") String  remitter_mobile,
                                                @Field("BeneficiaryID") String  BeneficiaryID,
                                                @Field("amount") String amount, @Field("mode") String mode);

    @POST("api/service/FundTransfer_Status")
    @FormUrlEncoded
    Call<FundTransferStatusResponse> postFundTransferStatus(@Field("user_id")String user_id,@Field("cyrus_id") String  cyrus_id);

    @GET("api/service/MovieCityFind ")
    Call<MovieCityResponse> getMoviesCity();

    @POST("api/service/GetMovieList")
    @FormUrlEncoded
    Call<MovieListResponse> postMovieList(@Field("city_id") String  city_id);

    @POST("api/service/getWalletBalance")
    @FormUrlEncoded
    Call<MyWalletResponse> postWallet(@Field("user_id") String  user_id);

    @POST("api/service/getWalletBal")
    @FormUrlEncoded
    Call<WalletTotalBalanceResponse> postWalletTotalBalance(@Field("user_id") String  user_id);


    @POST("index.php/api/user/login")
    @FormUrlEncoded
    Call<LoginResponse> postLogin(@Field("phone") String  phone,@Field("user_pwd") String user_pwd);

    @POST("api/user/updateprofile")
    @FormUrlEncoded
    Call<GetProfileResponse> postUpdateProfile(@Field("first_name") String  first_name,
                                               @Field("phone") String phone,
                                               @Field("email") String email,
                                               @Field("user_id") String user_id);

    @Multipart
    @POST("api/user/profileImage")
    Call<UpdateImageResponse> postUpdateImageResponse(@Part MultipartBody.Part picture, @Part("user_id")RequestBody user_id);


    @POST("api/service/allproduct")
    @FormUrlEncoded
    Call<HomeGroceryResponse> postHomeGrocery(@Field("order") String  order);

    @POST("api/user/registerFse")
    @FormUrlEncoded
    Call<FSESignupResponse> postFSEResponse(@Field("order") String  order);

    @POST("api/user/approveByDistributorWallet")
    @FormUrlEncoded
    Call<ApproveResponse> postApproveResponse(@Field("user_id") String user_id,@Field("id") String  id,@Field("distributor_status") String  distributor_status);

    @POST("api/user/delByDistributorWallet")
    @FormUrlEncoded
    Call<DeleteResponse> deleteApproveResponse(@Field("user_id") String user_id, @Field("id") String  id);

    @Multipart
    @POST("api/user/registerFse")
    Call<FSESignupResponse> postFSEResponse(@Part MultipartBody.Part icon,
                                            @Part MultipartBody.Part icon1,
                                            @Part MultipartBody.Part icon2,
                                            @Part("first_name")RequestBody first_name,
                                            @Part("email")RequestBody email,@Part("phone")RequestBody phone,
                                            @Part("distributor_user_id")RequestBody distributor_user_id,@Part("sales_experience")RequestBody sales_experience,
                                            @Part("job_type")RequestBody job_type,@Part("address_proof")RequestBody address_proof,
                                            @Part("line1")RequestBody line1,@Part("city")RequestBody city,@Part("pin")RequestBody pin,
                                            @Part("state")RequestBody state,@Part("country")RequestBody country);
    @Multipart
    @POST("api/user/updatefseProfile")
    Call<FSEUpdateResponse> updateFSEResponse(@Part MultipartBody.Part icon,
                                            @Part MultipartBody.Part icon1,
                                            @Part MultipartBody.Part icon2,
                                            @Part("first_name")RequestBody first_name,
                                            @Part("email")RequestBody email,@Part("phone")RequestBody phone,
                                            @Part("user_id")RequestBody distributor_user_id,@Part("sales_experience")RequestBody sales_experience,
                                            @Part("job_type")RequestBody job_type,@Part("address_proof")RequestBody address_proof,
                                            @Part("line1")RequestBody line1,@Part("city")RequestBody city,@Part("pin")RequestBody pin,
                                            @Part("state")RequestBody state,@Part("country")RequestBody country);


    @Multipart
    @POST("api/user/registerRetailer")
    Call<RetailerSignupResponse> postRetailerResponse(@Part MultipartBody.Part icon,
                                                 @Part MultipartBody.Part icon1,
                                                 @Part MultipartBody.Part icon2,
                                                 @Part("first_name")RequestBody first_name,
                                                 @Part("email")RequestBody email, @Part("phone")RequestBody phone,
                                                 @Part("fse_user_id")RequestBody fse_user_id, @Part("business_name")RequestBody business_name,
                                                 @Part("retail_sub_type")RequestBody retail_sub_type, @Part("address_proof")RequestBody address_proof,
                                                 @Part("line1")RequestBody line1, @Part("city")RequestBody city, @Part("pin")RequestBody pin,
                                                 @Part("state")RequestBody state, @Part("country")RequestBody country);

    @Multipart
    @POST("api/user/updateRetailerProfile")
    Call<RetailerUpdateResponse> updateRetailerResponse(@Part MultipartBody.Part icon,
                                                 @Part MultipartBody.Part icon1,
                                                 @Part MultipartBody.Part icon2,
                                                 @Part("first_name")RequestBody first_name,
                                                 @Part("email")RequestBody email, @Part("phone")RequestBody phone,
                                                 @Part("user_id")RequestBody fse_user_id, @Part("business_name")RequestBody business_name,
                                                 @Part("retail_sub_type")RequestBody retail_sub_type, @Part("address_proof")RequestBody address_proof,
                                                 @Part("line1")RequestBody line1, @Part("city")RequestBody city, @Part("pin")RequestBody pin,
                                                 @Part("state")RequestBody state, @Part("country")RequestBody country);



}
