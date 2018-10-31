package com.example.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.user.genie.Adapter.CardAdapter;
import com.example.user.genie.Model.CardModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CardBilling extends AppCompatActivity {
    Toolbar toolbar;
    ProgressDialog progressDialog;
    int i=0;
    private CardAdapter ordersAdapter;
    private List<CardModel> cardModels;
    RecyclerView card_recyclerview;

    ImageView refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_billing);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);
        card_recyclerview = findViewById(R.id.card_recyclerview);
        refresh = findViewById(R.id.refresh);

/*
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCards();
            }
        });
*/

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        card_recyclerview.setLayoutManager(manager);

        cardModels = new ArrayList<>();
        getCards();

        card_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, card_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                CardModel list = cardModels.get(position);
                return false;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        card_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, card_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                CardModel list = cardModels.get(position);
                String b_id = list.getBank_id();
                String b_name = list.getBank_name();
                String b_url = list.getBank_url();


                Intent i = new Intent(CardBilling.this, Webview.class);
                i.putExtra("BANK_ID", b_id);
                i.putExtra("BANK_NAME", b_name);
                i.putExtra("BANK_URL", b_url);
                startActivity(i);

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {
                // Toast.makeText(Samagri.this, "Long Press", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    private void getCards() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "http://demo.ratnatechnology.co.in/genie/index.php/api/user/get_credit_card",
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
                                        String bank_id = "", bank_name = "", url = "";
                                        JSONObject o = array.getJSONObject(i);
                                        bank_id = o.getString("id");
                                        bank_name = o.getString("bank_name");
                                        url = o.getString("payment_url");

                                        CardModel item = new CardModel(
                                                bank_id,bank_name, url);


                                        cardModels.add(item);

                                    /*String strCurrentDate = order_date;
                                    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
                                    Date newDate = format.parse(strCurrentDate);

                                    format = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
                                    String date = format.format(newDate);*/
                                    }
                                }
                                else {
                                /*Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();*/
                                    card_recyclerview.setVisibility(View.GONE);
                                   // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            ordersAdapter = new CardAdapter(cardModels, getApplicationContext());
                            card_recyclerview.setAdapter(ordersAdapter);


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
                        Toast.makeText(CardBilling.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(CardBilling.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(CardBilling.this);
        requestQueue.add(stringRequest);
    }

}
