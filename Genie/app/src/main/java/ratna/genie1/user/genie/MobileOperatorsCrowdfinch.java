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
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Adapter.MobileOperatorsAdapter;
import ratna.genie1.user.genie.Adapter.MobileOperatorsCFAdapter;
import ratna.genie1.user.genie.Model.MobileOperatorsCFModel;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiInterface;

public class MobileOperatorsCrowdfinch extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    RecyclerView mob_operators_recyclerview;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;

    private MobileOperatorsCFAdapter mobileOperatorsCFAdapter;
    private List<MobileOperatorsCFModel> mobileOperatorsCFModels;
    EditText searchEd;
    String opSearch;
    TextView noMesgTv;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_operators_crowdfinch);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MobileOperatorsCrowdfinch.this,MobileRecharge.class));
                finish();
            }
        });
        searchEd = findViewById(R.id.searchEd);
        noMesgTv = findViewById(R.id.noMesgTv);

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
        mob_operators_recyclerview = findViewById(R.id.mob_operators_recyclerview);
        apiService =
                ApiClientGenie.getClient().create(ApiInterface.class);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mob_operators_recyclerview.setLayoutManager(manager);



        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("PHONE_NUMBER", null);
        editor.commit();

        mobileOperatorsCFModels = new ArrayList<>();

        if (isNetworkAvailable()){
            getMobileOperators();
        }
        else {
            noNetwrokErrorMessage();
        }

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

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<MobileOperatorsCFModel> filterdNames = new ArrayList<>();

        for(int i=0;i<mobileOperatorsCFModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            MobileOperatorsCFModel model=mobileOperatorsCFModels.get(i);
            if(model.getOperator_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(model);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mobileOperatorsCFAdapter.filterList(filterdNames);
    }



    private void getMobileOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/recharge/get_crowdfinch_mobileoperator",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try {
                            Log.d("onResponse:", s);
                            JSONObject jsonObject = new JSONObject(s);
                            JSONObject object=null;
                            String message="";

                            if (jsonObject!=null){
                                JSONArray array = jsonObject.getJSONArray("data");
                                if (array.length()>0) {

                                    for (int i = 0; i < array.length(); i++) {
                                        String  operator_name = "", operator_code="";
                                        JSONObject o = array.getJSONObject(i);


                                        operator_name = o.getString("crowdfinch_operator_name");

                                        operator_code = o.getString("crowdfinch_code");
                                        /*SharedPreferences sharedPreferences = getSharedPreferences(mypreference,
                                                Context.MODE_MULTI_PROCESS);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();  //deb done code for one time login
                                        editor.putString("Op_Code", operator_code);
                                        editor.commit();*/


                                        MobileOperatorsCFModel item = new MobileOperatorsCFModel(
                                                operator_name, operator_code);


                                        Log.d("operator_name",operator_name);
                                        Log.d("op_code",operator_code);
                                        mobileOperatorsCFModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    mob_operators_recyclerview.setVisibility(View.GONE);
                                    noMesgTv.setVisibility(View.VISIBLE);
                                }
                            }

                            mobileOperatorsCFAdapter = new MobileOperatorsCFAdapter(mobileOperatorsCFModels, getApplicationContext());
                            mob_operators_recyclerview.setAdapter(mobileOperatorsCFAdapter);



                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MobileOperatorsCrowdfinch.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MobileOperatorsCrowdfinch.this);
        requestQueue.add(stringRequest);
    }

}
