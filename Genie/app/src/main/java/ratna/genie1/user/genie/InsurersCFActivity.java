package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

import ratna.genie1.user.genie.Adapter.ElectricityBoardsAdapter;
import ratna.genie1.user.genie.Adapter.InsurersAdapter;
import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.Model.InsurerModel;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class InsurersCFActivity extends AppCompatActivity {
    RecyclerView isurers_recyclerview;
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private InsurersAdapter insurersAdapter;
    private List<InsurerModel> insurerModels;
    EditText searchEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurers_cf);
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
        isurers_recyclerview = findViewById(R.id.isurers_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        isurers_recyclerview.setLayoutManager(manager);

        insurerModels = new ArrayList<>();
        getIsurers();
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<InsurerModel> filterdNames = new ArrayList<>();

        for(int i=0;i<insurerModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            InsurerModel circleModel=insurerModels.get(i);
            if(circleModel.getOperatorName().toUpperCase().contains(text.toUpperCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        insurersAdapter.filterList(filterdNames);
    }


    private void getIsurers() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/get_crowdfinchInsurance",
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
                                        String electricity_board_id = "", operatorName = "", operatorCode="", electricity_board_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                        //     electricity_board_id = o.getString("id");
                                        operatorName = o.getString("operator_name");

                                        operatorCode = o.getString("operator_code");
                                        //   electricity_board_type = o.getString("service_type");

                                        RegPrefManager.getInstance(InsurersCFActivity.this).setElectricityOperator(operatorName,operatorCode);

                                        InsurerModel item = new InsurerModel(
                                                operatorName, operatorCode);


                                        insurerModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    isurers_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            insurersAdapter = new InsurersAdapter(insurerModels, getApplicationContext());
                            isurers_recyclerview.setAdapter(insurersAdapter);


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
                        Toast.makeText(InsurersCFActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(InsurersCFActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(InsurersCFActivity.this);
        requestQueue.add(stringRequest);
    }

}
