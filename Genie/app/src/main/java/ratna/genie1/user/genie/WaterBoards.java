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
import ratna.genie1.user.genie.Adapter.DTHOperatorAdapter;
import ratna.genie1.user.genie.Adapter.WaterBoardAdapter;
import ratna.genie1.user.genie.Model.DTHOperatorsModel;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.Model.WaterBoardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.WaterBoardModel;

public class WaterBoards extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private WaterBoardAdapter waterBoardAdapter;
    private List<WaterBoardModel> waterBoardModels;
    RecyclerView water_board_recyclerview;
    EditText searchEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_boards);
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
        water_board_recyclerview = findViewById(R.id.water_board_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        water_board_recyclerview.setLayoutManager(manager);

        waterBoardModels = new ArrayList<>();
        getWaterBoards();

      /*  water_board_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, water_board_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                WaterBoardModel list = waterBoardModels.get(position);
                String operator_name = list.getWater_board_name();

                Intent intent = new Intent(WaterBoards.this,WaterBill.class);
                intent.putExtra("OPERATOR_NAME",operator_name);
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
        ArrayList<WaterBoardModel> filterdNames = new ArrayList<>();

        for(int i=0;i<waterBoardModels.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            WaterBoardModel circleModel=waterBoardModels.get(i);
            if(circleModel.getWater_board_name().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(circleModel);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        waterBoardAdapter.filterList(filterdNames);
    }


    private void getWaterBoards() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getwater",
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
                                        String water_board_id = "", water_board_name = "", water_board_code="", water_board_type = "";
                                        JSONObject o = array.getJSONObject(i);

                                        water_board_id = o.getString("id");
                                        water_board_name = o.getString("operator_name");

                                        water_board_code = o.getString("operator_code");
                                        water_board_type = o.getString("service_type");

                                        WaterBoardModel item = new WaterBoardModel(
                                                water_board_id,water_board_name, water_board_code, water_board_type);


                                        waterBoardModels.add(item);

                                    }
                                }
                                else {
                                Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();
                                    water_board_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }

                            waterBoardAdapter = new WaterBoardAdapter(waterBoardModels, getApplicationContext());
                            water_board_recyclerview.setAdapter(waterBoardAdapter);


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
                        Toast.makeText(WaterBoards.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(WaterBoards.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(WaterBoards.this);
        requestQueue.add(stringRequest);
    }

}
