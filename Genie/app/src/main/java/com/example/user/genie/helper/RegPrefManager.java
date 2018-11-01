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

}

