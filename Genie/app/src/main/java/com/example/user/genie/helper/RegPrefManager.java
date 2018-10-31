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




}

