package com.example.user.genie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import spencerstudios.com.bungeelib.Bungee;

public class SplashGifScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3600; //splash screen will be shown for this time period
    SharedPreferences sharedpreferences;
    private static final String mypreference ="myPref";
    String flag;
    String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_gif_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                sharedpreferences = getSharedPreferences(mypreference,
                        Context.MODE_PRIVATE);

                flag=sharedpreferences.getString("FLAG", "");
                SharedPreferences.Editor editor = sharedpreferences.edit(); //deb done code for one time login
                editor.putString("FLAG", user_id);

                Intent mainIntent = new Intent(SplashGifScreen.this, LogIn.class);
                startActivity(mainIntent);
                Bungee.split(SplashGifScreen.this);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
