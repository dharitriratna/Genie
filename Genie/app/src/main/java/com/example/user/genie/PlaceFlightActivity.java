package com.example.user.genie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.user.genie.Adapter.FlightPlaceCustomAdapter;
import com.example.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

public class PlaceFlightActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private ImageView cancelImg;
    private EditText searchEd;
    private RecyclerView placeRecyclerview;
    private FlightPlaceCustomAdapter flightPlaceCustomAdapter;
   // private CityMoviesCustomAdapter cityMoviesCustomAdapter;
    private ArrayList<String> placeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_flight);

        toolbar = findViewById(R.id.toolbar);
        cancelImg=toolbar.findViewById(R.id.cancelImg);
        cancelImg.setOnClickListener(this);
      intialize();




    }
    private void intialize(){
        String place= RegPrefManager.getInstance(this).getPlace();

        searchEd=findViewById(R.id.searchEd);
        if(place.equals("From")){
            searchEd.setHint("From Where?");
        }
        else {
            searchEd.setHint("To Where?");
        }
        placeRecyclerview=findViewById(R.id.placeRecyclerview);
       /* names = new ArrayList<>();

        names.add("Delhi");
        names.add("Bangalore");
        names.add("Mumbai");
        names.add("Dubai");
        names.add("Bbsr");*/






      /*  placeRecyclerview.setHasFixedSize(true);
        placeRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        cityMoviesCustomAdapter = new CityMoviesCustomAdapter(PlaceFlightActivity.this,names);

        placeRecyclerview.setAdapter(cityMoviesCustomAdapter);*/
        placeList=new ArrayList<>();
        placeList.add("delhi");
        placeList.add("Bengaluru, India");
        placeList.add("dubai");
        placeList.add("Mumbai, India");
        placeList.add("Bangkok, Thailand");
        placeList.add("Singapore, singapore");
        placeList.add("Goa, India");
        placeList.add("Hyderabad, India");
        placeList.add("Chennai, India");
        placeList.add("Ahamedabad, India");

        placeRecyclerview.setHasFixedSize(true);
        placeRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
        flightPlaceCustomAdapter=new FlightPlaceCustomAdapter(this,placeList);
        placeRecyclerview.setAdapter(flightPlaceCustomAdapter);
        //flightPlaceCustomAdapter.setClickListener(this);

        searchEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (String s : placeList) {
            //if the existing elements contains the search input
            if (s.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        flightPlaceCustomAdapter.filterList(filterdNames);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancelImg:
                finish();
                break;

        }
    }

   /* @Override
    public void onClick(View view, int position) {
        String placename=placeList.get(position);
        String place=RegPrefManager.getInstance(this).getPlace();
        if(place.equals("From")) {
            RegPrefManager.getInstance(this).setFromPlace(placename);
        }
        else {
            RegPrefManager.getInstance(this).setToPlace(placename);
        }
        startActivity(new Intent(PlaceFlightActivity.this,FlightActivity.class));
        finish();
    }*/
}
