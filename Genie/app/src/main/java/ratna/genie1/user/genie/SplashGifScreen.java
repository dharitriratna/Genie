package ratna.genie1.user.genie;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class SplashGifScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 200; //splash screen will be shown for this time period
    SharedPreferences sharedpreferences;
    private static final String mypreference = "myPref";
    String flag;
    String user_id;
    int versionCode;

    String currentVersion, latestVersion;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_gif_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                flag = sharedpreferences.getString("FLAG", "");
                SharedPreferences.Editor editor = sharedpreferences.edit(); //deb done code for one time login
                editor.putString("FLAG", user_id);


                Intent mainIntent = new Intent(SplashGifScreen.this, LogIn.class);
                startActivity(mainIntent);
                //  Bungee.split(SplashGifScreen.this);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

        // getCurrentVersion();
    }


    private void getCurrentVersion() {
        PackageManager pm = this.getPackageManager();
        PackageInfo pInfo = null;

        try {
            pInfo = pm.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        currentVersion = pInfo.versionName;

        new GetLatestVersion().execute();

    }

    private class GetLatestVersion extends AsyncTask<String, String, JSONObject> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            try {
//It retrieves the latest version by scraping the content of current version from play store at runtime
                Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=ratna.genie1.user.genie").get();
                latestVersion = doc.getElementsByClass("htlgb").get(6).text();

            } catch (Exception e) {
                e.printStackTrace();

            }

            return new JSONObject();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (latestVersion != null) {
                if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                    if (!isFinishing()) { //This would help to prevent Error : BinderProxy@45d459c0 is not valid; is your activity running? error
                 //       showUpdateDialog();
                    }
                } else if (currentVersion.equals(latestVersion)) {

                } else {

                    super.onPostExecute(jsonObject);
                }
            }
        }

/*
    private void showUpdateDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("A New Update is Available");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://play.google.com/store/apps/details?id=ratna.genie1.user.genie")));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finishAffinity();
               finish();
            }
        });

        builder.setCancelable(false);
        dialog = builder.show();
    }
*/
    }
}
