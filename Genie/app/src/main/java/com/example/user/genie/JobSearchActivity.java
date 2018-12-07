package com.example.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.user.genie.Adapter.CityMoviesCustomAdapter;
import com.example.user.genie.Adapter.JobSearchAdapter;
import com.example.user.genie.Model.JobSearchModel;

import java.util.ArrayList;

public class JobSearchActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView searchRecyclerview;
    private Button searchBtn;
    private EditText whereEd,whatEd;
    private Spinner tittlespinner;
    private Toolbar toolbar;
    private JobSearchAdapter adapter;
    private ArrayList<JobSearchModel> jobSearchModelArrayList;
    String[] tittle={"What(Job Title)"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(JobSearchActivity.this,MainActivity.class));
                finish();
            }
        });
        intialize();
    }
    private void intialize(){
        jobSearchModelArrayList=new ArrayList<>();
        searchRecyclerview=findViewById(R.id.searchRecyclerview);
        searchBtn=findViewById(R.id.searchBtn);
        whereEd=findViewById(R.id.whereEd);
        whatEd=findViewById(R.id.whatEd);
        tittlespinner=findViewById(R.id.tittlespinner);
        searchBtn.setOnClickListener(this);

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aaa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,tittle);
        aaa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        tittlespinner.setAdapter(aaa);

     //   names = new ArrayList<>();
        for (int i=0;i<10;i++) {
            JobSearchModel jobSearchModel = new JobSearchModel();
            jobSearchModel.setJobtitle("Android Development");
            jobSearchModel.setCompanyname("Ratna Technology");
            jobSearchModel.setLocation("Bhubaneswar,Odisha");
            jobSearchModel.setDetails("Android");

            jobSearchModelArrayList.add(jobSearchModel);

        }


        searchRecyclerview.setHasFixedSize(true);
        searchRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        adapter = new JobSearchAdapter(JobSearchActivity.this,jobSearchModelArrayList);

        searchRecyclerview.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.searchBtn:

                break;
        }
    }
}
