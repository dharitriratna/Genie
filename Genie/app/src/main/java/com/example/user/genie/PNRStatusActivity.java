package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PNRStatusActivity extends AppCompatActivity {
    private TextView pnrTV,trainTV,traveldateTV,chatTV,passengernoTv,bookStatusTv,currentstatusTv;
    private Button confirmTrains;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnrstatus);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // onBackPressed();
                startActivity(new Intent(PNRStatusActivity.this,BookTrain.class));
                finish();
            }
        });

        traveldateTV=findViewById(R.id.traveldateTV);
        pnrTV=findViewById(R.id.pnrTV);
        trainTV=findViewById(R.id.trainTV);
        chatTV=findViewById(R.id.chatTV);
        passengernoTv=findViewById(R.id.passengernoTv);
        bookStatusTv=findViewById(R.id.bookStatusTv);
        currentstatusTv=findViewById(R.id.currentstatusTv);
        confirmTrains=findViewById(R.id.confirmTrains);

        Intent i = getIntent();


        Bundle b = i.getBundleExtra("PassengerDetails");
        String doj = b.getString("DOJ");
        String currentstatus = b.getString("CurrentStatus");
        String bookingstatus = b.getString("BookingStatus");
        String Passengerno = b.getString("Passengerno");
        String PNR = b.getString("PNR");
        String TrainName = b.getString("TrainName");
        String TrainNumber = b.getString("TrainNumber");
        String Chartprepared = b.getString("Chartprepared");
        String BoardingPoint = b.getString("BoardingPoint");
        String BoardingCode = b.getString("BoardingCode");
        String ArrivalPoint = b.getString("ArrivalPoint");
        String ArrivalCode = b.getString("ArrivalCode");

        pnrTV.setText("PNR "+PNR);
        trainTV.setText(TrainName+" "+TrainNumber);
        traveldateTV.setText("Travel date: "+doj);

        if(Chartprepared.equals("false")){
            chatTV.setText("CHART NOT PREPARED");
        }else {
            chatTV.setText("CHART PREPARED");
        }

        passengernoTv.setText("Passenger "+Passengerno);
        bookStatusTv.setText(bookingstatus);
        currentstatusTv.setText(currentstatus);

    }
}
