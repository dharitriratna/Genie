package com.example.user.genie.helper;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Raju Satheesh on 10/18/2016.
 */
public class RegPrefManager {

    private SharedPreferences mSharedPreferences;
    private static RegPrefManager mPrefManager;



    public Context mContext;


    private RegPrefManager(Context context) {
        this.mContext=context;
        mSharedPreferences = context.
                getSharedPreferences("ratna", Context.MODE_PRIVATE);
    }

    public static RegPrefManager getInstance(Context context) {
        if (mPrefManager == null) {
            mPrefManager = new RegPrefManager(context);
        }
        return mPrefManager;
    }



   public void setCity(String name){
       mSharedPreferences.edit().putString("cityname",name).apply();
   }
   public String getCity(){
       return mSharedPreferences.getString("cityname",null);
   }


    public void setFromPlace(String place){
        mSharedPreferences.edit().putString("FlightFromPlace",place).apply();
    }
    public String getFromPlace(){
        return mSharedPreferences.getString("FlightFromPlace",null);
    }

    public void setToPlace(String place){
        mSharedPreferences.edit().putString("FlightToPlace",place).apply();
    }
    public String getToPlace(){
        return mSharedPreferences.getString("FlightToPlace",null);
    }

    public void setPlace(String place){
        mSharedPreferences.edit().putString("FlightPlace",place).apply();
    }
    public String getPlace(){
        return mSharedPreferences.getString("FlightPlace",null);
    }

    public void setAdultNo(String place){
        mSharedPreferences.edit().putString("AdultNo",place).apply();
    }
    public String getAdultNo(){
        return mSharedPreferences.getString("AdultNo",null);
    }

    public void setChildNo(String place){
        mSharedPreferences.edit().putString("ChildNo",place).apply();
    }
    public String getChildNo(){
        return mSharedPreferences.getString("ChildNo",null);
    }

    public void setInfantNo(String place){
        mSharedPreferences.edit().putString("InfantNo",place).apply();
    }
    public String getInfantNo(){
        return mSharedPreferences.getString("InfantNo",null);
    }
    public void setClassName(String place){
        mSharedPreferences.edit().putString("ClassName",place).apply();
    }
    public String getClassName(){
        return mSharedPreferences.getString("ClassName",null);
    }

    public void setDestinationName(String place){
        mSharedPreferences.edit().putString("Destinname",place).apply();
    }
    public String getDestinationName(){
        return mSharedPreferences.getString("Destinname",null);
    }
    public void setTrainFromPlace(String place){
        mSharedPreferences.edit().putString("FlightTrainFromPlace",place).apply();
    }
    public String getTainFromPlace(){
        return mSharedPreferences.getString("FlightTrainFromPlace",null);
    }

    public void setTainToPlace(String place){
        mSharedPreferences.edit().putString("FlightTrainToPlace",place).apply();
    }
    public String getTainToPlace(){
        return mSharedPreferences.getString("FlightTrainToPlace",null);
    }
    public void setCabToPlace(String place){
        mSharedPreferences.edit().putString("CabToPlace",place).apply();
    }
    public String getCabToPlace(){
        return mSharedPreferences.getString("CabToPlace",null);
    }
    public void setCabFromPlace(String place){
        mSharedPreferences.edit().putString("CabFromPlace",place).apply();
    }
    public String getCabFromPlace(){
        return mSharedPreferences.getString("CabFromPlace",null);
    }
    public void setTripRadio(String trip){
        mSharedPreferences.edit().putString("trip",trip).apply();
    }
    public String getTripRadio(){
        return mSharedPreferences.getString("trip",null);
    }
    public void setPhoneNo(String phone){
        mSharedPreferences.edit().putString("phoneno",phone).apply();
    }
    public String getPhoneNo(){
        return mSharedPreferences.getString("phoneno",null);
    }
    public void setBack(String back){
        mSharedPreferences.edit().putString("back",back).apply();
    }
    public String getBack(){
        return mSharedPreferences.getString("back",null);
    }

    public void setDataCardOperator(String operator,String code){
        mSharedPreferences.edit().putString("operator",operator).apply();
        mSharedPreferences.edit().putString("opearator_code",code).apply();
    }
    public String getDataCardOperator(){
        return mSharedPreferences.getString("operator",null);
    }
    public String getDataCardOperatorCode(){
        return mSharedPreferences.getString("opearator_code",null);
    }
    public void setDataCardCircle(String name,String code){
        mSharedPreferences.edit().putString("circlename",name).apply();
        mSharedPreferences.edit().putString("circlecode",code).apply();
    }
    public String getDDataCardCirclename(){
        return mSharedPreferences.getString("circlename",null);
    }
    public String gettDDataCardCircleCode(){
        return mSharedPreferences.getString("circlecode",null);
    }
    public void setLandlineOperator(String name,String code){
        mSharedPreferences.edit().putString("landlineoperatorname",name).apply();
        mSharedPreferences.edit().putString("landlineoperatorcode",code).apply();
    }
    public String getLandlineOperatorname(){
        return mSharedPreferences.getString("landlineoperatorname",null);
    }
    public String getLandlineOperatorcode(){
        return mSharedPreferences.getString("landlineoperatorcode",null);
    }

    public void setLandlineCircle(String name,String code){
        mSharedPreferences.edit().putString("landlinecirclename",name).apply();
        mSharedPreferences.edit().putString("landlinecirclecode",code).apply();
    }
    public String getLandlinecirclename(){
        return mSharedPreferences.getString("landlinecirclename",null);
    }
    public String getLandlinecirclecode(){
        return mSharedPreferences.getString("landlinecirclecode",null);
    }

    public void setInsuranceId(String id){
        mSharedPreferences.edit().putString("InsuranceID",id).apply();

    }
    public String getInsuranceId(){
        return mSharedPreferences.getString("InsuranceID",null);
    }
    public void setReqId(String id){
        mSharedPreferences.edit().putString("ReqID",id).apply();

    }
    public String getReqId(){
        return mSharedPreferences.getString("ReqID",null);
    }

}

