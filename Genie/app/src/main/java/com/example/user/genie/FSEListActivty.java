package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.Adapter.FSEListAdapter;
import com.example.user.genie.Adapter.GiftsAdapter;
import com.example.user.genie.Model.FSEListModel;
import com.example.user.genie.Model.GiftsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FSEListActivty extends AppCompatActivity {
    Toolbar toolbar;
    ImageView add_fse;
    RecyclerView fselistRecyclerview;
    private ProgressDialog progressDialog;
    private int i =0;
    private FSEListAdapter fseListAdapter;
    private List<FSEListModel> fseListModels;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fselist_activty);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        add_fse = findViewById(R.id.add_fse);
        add_fse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FSEListActivty.this,FSESignupActivity.class));
            }
        });

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit c

        fselistRecyclerview=findViewById(R.id.fselistRecyclerview);
        progressDialog = new ProgressDialog(this);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        fselistRecyclerview.setLayoutManager(manager);

        fseListModels = new ArrayList<>();
        getFseLists();

        fselistRecyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, fselistRecyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                FSEListModel list = fseListModels.get(position);
               /* String giftId = list.getGift_id();
                String giftName = list.getGift_name();
                String giftPrice = list.getGift_price();
                String giftImage = list.getGift_image();

                Intent intent = new Intent(GiftsList.this,SurprisePlanner.class);
                intent.putExtra("GIFT_ID",giftId);
                intent.putExtra("GIFT_NAME",giftName);
                intent.putExtra("GIFT_PRICE",giftPrice);
                intent.putExtra("GIFT_IMAGE",giftImage);
                startActivity(intent);*/
                // finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void getFseLists() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/index.php/api/user/getListFse?user_id="+login_user,
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
                                        String id = "", username = "", phone="", email = "", first_name="",address_proof="",line1="";
                                        JSONObject o = array.getJSONObject(i);

                                        id = o.getString("id");
                                        username = o.getString("username");

                                        phone = o.getString("phone");
                                        email = o.getString("email");
                                        first_name = o.getString("first_name");
                                        address_proof = o.getString("address_proof");
                                        line1 = o.getString("line1");

                                        FSEListModel item = new FSEListModel(
                                                id,username, phone, email,first_name,address_proof,line1);


                                        //   Log.d("operator_name",event_name);
                                        fseListModels.add(item);

                                    }
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "No Data Found",
                                            Toast.LENGTH_LONG).show();
                                    fselistRecyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            fseListAdapter = new FSEListAdapter(fseListModels, getApplicationContext());
                            fselistRecyclerview.setAdapter(fseListAdapter);


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
                        Toast.makeText(FSEListActivty.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(FSEListActivty.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(FSEListActivty.this);
        requestQueue.add(stringRequest);
    }

}
