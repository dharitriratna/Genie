package ratna.genie1.user.genie.Utils;

import android.app.Application;
import android.content.Context;

import com.androidnetworking.AndroidNetworking;

/*
 * Created by biswajit on 08-08-15.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AndroidNetworking.initialize(getApplicationContext());
    }

    public static MyApplication getmInstance(){
        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }
}
