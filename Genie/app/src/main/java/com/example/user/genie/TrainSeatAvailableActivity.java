package com.example.user.genie;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.genie.Adapter.TrainListAdapter;
import com.example.user.genie.Adapter.TrainSeatAdapter;
import com.example.user.genie.Model.TrainSeatAvailableModel;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;


public class TrainSeatAvailableActivity extends AppCompatActivity implements  View.OnClickListener {
    private Toolbar toolbar;
    private TextView listTv;
    private RecyclerView trainRecycler;
    private TrainListAdapter adapter;
    private TrainSeatAdapter seatAdapter;
    private ArrayList<String> seatAvail;
    private TextView sorttv;
    private ArrayList<TrainSeatAvailableModel> trainSeatAvailableModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_seat_available);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(TrainSeatAvailableActivity.this,BookTrain.class));
                finish();
            }
        });
        listTv=toolbar.findViewById(R.id.listTv);

        listTv.setText(RegPrefManager.getInstance(this).getTainFromPlace()+"-"+RegPrefManager.getInstance(this).getTainToPlace());

        intialize();
    }
    private void intialize(){
        trainRecycler=findViewById(R.id.trainRecycler);
        sorttv=findViewById(R.id.sorttv);
        sorttv.setOnClickListener(this);
        trainSeatAvailableModels=new ArrayList<>();
        seatAvail=new ArrayList<>();
        trainSeatAvailableModels.clear();
        for(int i=0;i<10;i++){
            TrainSeatAvailableModel trainSeatAvailableModel=new TrainSeatAvailableModel("Jalinwala B Exp(12379)","Howrah Jn",
                    "New Delhi","13:10","12:10,03 Nov");
            trainSeatAvailableModels.add(trainSeatAvailableModel);
        }

        trainRecycler.setHasFixedSize(true);
        trainRecycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TrainListAdapter(TrainSeatAvailableActivity.this,trainSeatAvailableModels);

        trainRecycler.setAdapter(adapter);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.sorttv:
                bottomDialog();
                break;
        }
    }

    private void bottomDialog(){
        BottomSheetDialog bottomSheerDialog = new BottomSheetDialog(TrainSeatAvailableActivity.this);
        View parentView = getLayoutInflater().inflate(R.layout.trainsortlayout,null);
        bottomSheerDialog.setContentView(parentView);

        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics());
        bottomSheerDialog.show();
    }

    public void sortDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // Include dialog.xml file
        dialog.setContentView(R.layout.trainsortlayout);

        // Set dialog title







        dialog.show();
    }
}
