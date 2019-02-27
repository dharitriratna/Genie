package ratna.genie1.user.genie;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;



import static ratna.genie1.user.genie.VerticalFragment.hasPermissions;

public class SplashScreen extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    SharedPreferences sharedpreferences;
    private static final String mypreference ="myPref";
    String flag;
    String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                flag=sharedpreferences.getString("FLAG", "");
                SharedPreferences.Editor editor = sharedpreferences.edit(); //deb done code for one time login
                editor.putString("FLAG", user_id);

                Intent i = new Intent(SplashScreen.this, LogIn.class);
                startActivity(i);
              //  Bungee.split(SplashScreen.this);
               // overridePendingTransition(R.anim.hyperspace_jump);//R.anim.pull_up_from_bottom
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);


        int Permission_All = 1;

        String[] Permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, };
        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
    }

    public static boolean hasPermissions(Context context, String... permissions){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP && context!=null && permissions!=null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return  false;
                }
            }
        }
        return true;
    }
}
