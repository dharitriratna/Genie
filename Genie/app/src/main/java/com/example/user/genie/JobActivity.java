package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class JobActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private Spinner spinner,tittlespinner;
    private EditText nameTv,qualiTv;
    private EditText locationTv;
    private EditText numberTv,descriptionTv;
    private Button uploadBtn,searchBtn,postBtn;
    String[] exp = { "Total Experience","Less then 1 year", "1 year", "2 year","3 year","4 year","5 year"
            ,"6 year","7 year","8 year","9 year","10+ year"};
    String[] tittle={"Job Title(Current or Most Recent)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(JobActivity.this,MainActivity.class));
                finish();
            }
        });
        intialize();
    }
    private void intialize(){
        spinner=findViewById(R.id.spinner);
        tittlespinner=findViewById(R.id.tittlespinner);
        nameTv=findViewById(R.id.nameTv);
        qualiTv=findViewById(R.id.qualiTv);
        numberTv=findViewById(R.id.numberTv);
        locationTv=findViewById(R.id.locationTv);
       // tittleTv=findViewById(R.id.tittleTv);
        descriptionTv=findViewById(R.id.descriptionTv);
        uploadBtn=findViewById(R.id.uploadBtn);
        postBtn=findViewById(R.id.postBtn);
        searchBtn=findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,exp);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tittle);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        tittlespinner.setAdapter(aaa);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchBtn:
             /*   startActivity(new Intent(JobActivity.this,JobSearchActivity.class));
                finish();*/
                break;
        }
    }
}
