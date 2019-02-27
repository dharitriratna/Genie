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
import ratna.genie1.user.genie.Adapter.ElectricityBoardsAdapter;
import ratna.genie1.user.genie.Adapter.WaterBoardAdapter;
import ratna.genie1.user.genie.Model.DTHOperatorsModel;
import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.Model.MobileOperatorCircleModel;
import ratna.genie1.user.genie.Model.WaterBoardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class ElectricityBoards extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private ElectricityBoardsAdapter electricityBoardsAdapter;
    private List<ElectricityBoardModel> electricityBoardModels;
    RecyclerView electricity_board_recyclerview;
    EditText searchEd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_boards);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        electricity_board_recyclerview = findViewById(R.id.electricity_board_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        electricity_board_recyclerview.setLayoutManager(manager);

        electricityBoardModels = new ArrayList<>();
        getElectricityBoards();

        /*electricity_board_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, electricity_board_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                ElectricityBoardModel list = electricityBoardModels.get(position);
                String name = list.getElectricity_board_name();
                String circle_code = list.getElectricity_board_code();


                Intent intent = new Intent(ElectricityBoards.this,PayForElectricity.class);
                intent.putExtra("BOARD_NAME", name);
                intent.putExtra("BOARD_CODE", circle_code);

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
        ArrayList<ElectricityBoardModel> filterdNames = new ArrayList<>();

        for(int i=0;i<electricityBoardModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            ElectricityBoardModel circleModel=electricityBoardModels.get(i);
            if(circleModel.getElectricity_board_name().toUpperCase().contains(text.toUpperCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        electricityBoardsAdapter.filterList(filterdNames);
    }


    private void getElectricityBoards() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getCrowdfinch_Electricity",
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
                                        String electricity_board_id = "", electricity_board_name = "", electricity_board_code="", electricity_board_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                   //     electricity_board_id = o.getString("id");
                                        electricity_board_name = o.getString("operator_name");

                                        electricity_board_code = o.getString("operator_code");
                                     //   electricity_board_type = o.getString("service_type");

                                        RegPrefManager.getInstance(ElectricityBoards.this).setElectricityOperator(electricity_board_name,electricity_board_code);

                                        ElectricityBoardModel item = new ElectricityBoardModel(
                                                electricity_board_name, electricity_board_code);


                                        electricityBoardModels.add(item);

                                    }
                                }
                                else {
                                Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();
                                    electricity_board_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            electricityBoardsAdapter = new ElectricityBoardsAdapter(electricityBoardModels, getApplicationContext());
                            electricity_board_recyclerview.setAdapter(electricityBoardsAdapter);


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
                        Toast.makeText(ElectricityBoards.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(ElectricityBoards.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ElectricityBoards.this);
        requestQueue.add(stringRequest);
    }

}
