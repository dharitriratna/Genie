package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ViewProfileDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    TextView userId, userName, userEmail, userNumber, userAdProof, userAddress;
    String userIdStr, userNameStr, userEmailStr, userPhoneStr, userAdProofStr, userAddressStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile_details);
        toolbar=findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        userId = findViewById(R.id.userId);
        userName= findViewById(R.id.userName);
        userEmail= findViewById(R.id.userEmail);
        userNumber= findViewById(R.id.userNumber);
        userAdProof= findViewById(R.id.userAdProof);
        userAddress= findViewById(R.id.userAddress);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            userIdStr = bundle.getString("FSE_ID");
            userNameStr = bundle.getString("FSE_NAME");
            userEmailStr = bundle.getString("FSE_EMAIL");
            userPhoneStr = bundle.getString("FSE_PHONE");
            userAdProofStr = bundle.getString("FSE_ADPROOF");
            userAddressStr = bundle.getString("FSE_ADDRESS");

            Log.d("url", userIdStr);
        }

        userId.setText(userIdStr);
        userName.setText(userNameStr);
        userEmail.setText(userEmailStr);
        userNumber.setText(userPhoneStr);
        userAdProof.setText(userAdProofStr);
        userAddress.setText(userAddressStr);
    }
}
