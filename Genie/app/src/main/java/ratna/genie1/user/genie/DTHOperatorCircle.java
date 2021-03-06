package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ratna.genie1.user.genie.Adapter.DTHOperatorCircleAdapter;
import ratna.genie1.user.genie.Adapter.MobileOperatorCircleAdapter;
import ratna.genie1.user.genie.Model.DTHOperatorCircleModel;
import ratna.genie1.user.genie.Model.MobileOperatorCircleModel;
import ratna.genie1.user.genie.helper.RegPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Adapter.DTHOperatorCircleAdapter;
import ratna.genie1.user.genie.Model.MobileOperatorCircleModel;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class DTHOperatorCircle extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private DTHOperatorCircleAdapter mobileOperatorCircleAdapter;
    private List<DTHOperatorCircleModel> mobileOperatorCircleModels;
    RecyclerView mob_operators_circle_recyclerview;
    String operator_name, operator_code;
    String number;
    EditText searchEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_operator_circle);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // onBackPressed();
                startActivity(new Intent(DTHOperatorCircle.this,MobileRecharge.class));
                finish();
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
        mob_operators_circle_recyclerview = findViewById(R.id.mob_operators_circle_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mob_operators_circle_recyclerview.setLayoutManager(manager);


        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        // commit changes
        String number=pref.getString("PHONE_NUMBER", null);
        editor.commit();
     //   Toast.makeText(this, number, Toast.LENGTH_SHORT).show();

        mobileOperatorCircleModels = new ArrayList<>();
        getMobileOperators();


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle != null) {

            operator_name = bundle.getString("OPERATOR_NAME");
            operator_code = bundle.getString("OPERATOR_CODE");
        //    number = bundle.getString("NUMBER");

//               Log.d("number", number);
        }


      /*  mob_operators_circle_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, mob_operators_circle_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                MobileOperatorCircleModel list = mobileOperatorCircleModels.get(position);
                String name = list.getOperator_circle_name();
                String circle_code = list.getOperator_circle_code();


                Intent intent = new Intent(MobileOperatorCircle.this,MobileRecharge.class);
                intent.putExtra("CIRCLE_NAME", name);
                intent.putExtra("CIRCLE_CODE", circle_code);
                intent.putExtra("OPERATOR_NAME", operator_name);
                intent.putExtra("OPERATOR_CODE", operator_code);
                startActivity(intent);
                finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<DTHOperatorCircleModel> filterdNames = new ArrayList<>();

        for(int i=0;i<mobileOperatorCircleModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            DTHOperatorCircleModel circleModel=mobileOperatorCircleModels.get(i);
            if(circleModel.getOperator_circle_name().toUpperCase().contains(text.toUpperCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mobileOperatorCircleAdapter.filterList(filterdNames);
    }


    private void getMobileOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getcircle",
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
                                        String operator_circle_id = "", operator_circle_name = "", operator_circle_code="";
                                        JSONObject o = array.getJSONObject(i);

                                        operator_circle_id = o.getString("id");
                                        operator_circle_name = o.getString("circle_name");

                                        operator_circle_code = o.getString("circle_code");
                                        RegPrefManager.getInstance(DTHOperatorCircle.this).setMobReCircleId(operator_circle_code);

                                        DTHOperatorCircleModel item = new DTHOperatorCircleModel(
                                                operator_circle_id,operator_circle_name, operator_circle_code);


                                     //   Log.d("operator_name",operator_name);
                                        mobileOperatorCircleModels.add(item);

                                    }
                                }
                                else {
                                Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();
                                    mob_operators_circle_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            mobileOperatorCircleAdapter = new DTHOperatorCircleAdapter(mobileOperatorCircleModels, getApplicationContext());
                            mob_operators_circle_recyclerview.setAdapter(mobileOperatorCircleAdapter);


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
                        Toast.makeText(DTHOperatorCircle.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DTHOperatorCircle.this);
        requestQueue.add(stringRequest);
    }

}
