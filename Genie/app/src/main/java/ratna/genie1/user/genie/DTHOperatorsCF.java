package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
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

import ratna.genie1.user.genie.Adapter.DTHOperatorAdapter;
import ratna.genie1.user.genie.Adapter.DTHOperatorCFAdapter;
import ratna.genie1.user.genie.Model.DTHOperatorsCFModel;
import ratna.genie1.user.genie.Model.DTHOperatorsModel;

public class DTHOperatorsCF extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private DTHOperatorCFAdapter operatorCFAdapter;
    private List<DTHOperatorsCFModel> dthOperatorsCFModels;
    RecyclerView dth_operators_recyclerview;
    EditText searchEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dthoperators_cf);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DTHOperatorsCF.this,DTHRecharge.class));
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
        dth_operators_recyclerview = findViewById(R.id.dth_operators_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        dth_operators_recyclerview.setLayoutManager(manager);

        dthOperatorsCFModels = new ArrayList<>();
        getMobileOperators();
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<DTHOperatorsCFModel> filterdNames = new ArrayList<>();

        for(int i=0;i<dthOperatorsCFModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            DTHOperatorsCFModel circleModel=dthOperatorsCFModels.get(i);
            if(circleModel.getDth_operator_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(circleModel);
            }
        }
        //calling a method of the adapter class and passing the filtered list
        operatorCFAdapter.filterList(filterdNames);
    }


    private void getMobileOperators() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getdth",
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
                                        String dth_operator_name = "", dth_operator_code="";
                                        JSONObject o = array.getJSONObject(i);

                                        dth_operator_name = o.getString("operator_name");

                                        dth_operator_code = o.getString("operator_code");

                                        DTHOperatorsCFModel item = new DTHOperatorsCFModel(
                                                dth_operator_name, dth_operator_code);


                                        Log.d("operator_name",dth_operator_name);
                                        dthOperatorsCFModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    dth_operators_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            operatorCFAdapter = new DTHOperatorCFAdapter(dthOperatorsCFModels, getApplicationContext());
                            dth_operators_recyclerview.setAdapter(operatorCFAdapter);


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
                        Toast.makeText(DTHOperatorsCF.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(getApplicationContext(), "Genie is away! Try after sometime", Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(DTHOperatorsCF.this);
        requestQueue.add(stringRequest);
    }

}
