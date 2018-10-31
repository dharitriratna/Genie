package com.example.user.genie.Utils;

/**
 * Created by RatnaDev008 on 12/22/2017.
 */
import android.app.Application;

public class GlobalClass extends Application {

    //add this variable declaration:
    public static String somevalue = "";

    private static GlobalClass singleton;

    public static GlobalClass getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}