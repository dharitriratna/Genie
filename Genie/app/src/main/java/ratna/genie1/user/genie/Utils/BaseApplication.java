package ratna.genie1.user.genie.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.multidex.MultiDex;

public class  BaseApplication extends Application {


    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
     //   super.attachBaseContext(base);
        MultiDex.install(this);
    }
}