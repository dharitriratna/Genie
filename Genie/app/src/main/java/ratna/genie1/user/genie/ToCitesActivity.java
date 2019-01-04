package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import ratna.genie1.user.genie.Adapter.BusCitiesAdapter;
import ratna.genie1.user.genie.Adapter.BusToCitiesAdapter;
import ratna.genie1.user.genie.ObjectNew.BusToCitiesResponse;
import ratna.genie1.user.genie.ObjectNew.Cites;

import ratna.genie1.user.genie.ObjectNew.destinationCities;
import ratna.genie1.user.genie.ObjectNew.oRiginCities;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ratna.genie1.user.genie.Adapter.BusToCitiesAdapter;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToCitesActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView tocitiesRecyclerView;
    ProgressDialog progressDialog;
    int i=0;
    private BusToCitiesAdapter busToCitiesAdapter;
    private ArrayList<destinationCities> destinationCitiesModels;
    EditText searchEd;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_cites);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchEd = findViewById(R.id.searchEd);
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
        progressDialog = new ProgressDialog(this);
        tocitiesRecyclerView = findViewById(R.id.tocitiesRecyclerView);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        tocitiesRecyclerView.setLayoutManager(manager);

        destinationCitiesModels = new ArrayList<>();


        if (isNetworkAvailable()) {
            networkCircleService();
        } else {
            noNetwrokErrorMessage();
        }


        tocitiesRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, tocitiesRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                destinationCities list = destinationCitiesModels.get(position);
                String destination_name = list.getDestinationName();
                int circle_code = list.getDestinationId();

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("DESTINATION_NAME", destination_name.toString());
                editor.commit();
                Intent intent = new Intent(ToCitesActivity.this,BusBookingActivity.class);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<destinationCities> filterdNames = new ArrayList<>();

        for(int i=0;i<destinationCitiesModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            destinationCities circleModel=destinationCitiesModels.get(i);
            if(circleModel.getDestinationName().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        busToCitiesAdapter.filterList(filterdNames);
    }

    public boolean isNetworkAvailable(){
        ConnectivityManager connectivityManager= (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void noNetwrokErrorMessage(){
        alertDialog.setTitle("Error!");
        alertDialog.setMessage("No internet connection. Please check your internet setting.");
        alertDialog.setCancelable(true);
        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }

    private void networkCircleService(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String originId= RegPrefManager.getInstance(this).getBusFromID();


       /* Cites cites=new Cites();
        cites.setOriginId(Integer.parseInt(originId));*/
    /*    String request=new Gson().toJson(cites);

        //Here the json data is add to a hash map with key data
        Map<String,String> params = new HashMap<String, String>();
        params.put("DestinationInput", request);*/

        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("OriginId",originId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject mainjson=new JSONObject();
        try {
            mainjson.put("DestinationInput",jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String value=mainjson.toString();

        Log.d("Tag", String.valueOf(value));

        Call<BusToCitiesResponse> call = apiService.postDestinationDetails(value);

        call.enqueue(new Callback<BusToCitiesResponse>() {
            @Override
            public void onResponse(Call<BusToCitiesResponse> call, Response<BusToCitiesResponse> response) {
                progressDialog.dismiss();
                boolean status=response.body().isStatus();
                if(status==true){
                    destinationCitiesModels=response.body().getData().getDestinationOutput().getDestinationCities();
                    if(destinationCitiesModels.size()>0){
                        busToCitiesAdapter = new BusToCitiesAdapter(destinationCitiesModels, getApplicationContext());
                        tocitiesRecyclerView.setAdapter(busToCitiesAdapter);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found",
                                Toast.LENGTH_LONG).show();
                        tocitiesRecyclerView.setVisibility(View.GONE);
                        // no_orders_text.setVisibility(View.VISIBLE);
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Try again. After some times",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BusToCitiesResponse> call, Throwable t) {

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Try again. After some times",Toast.LENGTH_LONG).show();
            }
        });
      /*  String request=new Gson().toJson(toCitesRequest);
        JsonElement je = new Gson().toJsonTree(request);
        JsonObject jsonObject=new JsonObject();
        jsonObject.add("DestinationInput",je);


        Log.d("Tag", String.valueOf(jsonObject));*/



    }

}
