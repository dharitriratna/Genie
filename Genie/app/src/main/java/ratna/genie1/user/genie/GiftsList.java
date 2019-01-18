package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ratna.genie1.user.genie.Adapter.EventsAdapter;
import ratna.genie1.user.genie.Adapter.GiftsAdapter;
import ratna.genie1.user.genie.Model.EventsModel;
import ratna.genie1.user.genie.Model.GiftsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Adapter.GiftsAdapter;
import ratna.genie1.user.genie.Model.GiftsModel;
import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class GiftsList extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView gifts_recyclerview;
    ProgressDialog progressDialog;
    int i=0;
    private GiftsAdapter giftsAdapter;
    private List<GiftsModel> giftsModels;
    String groupId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gifts_list);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(GiftsList.this).getUserGroup();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (groupId.equals("4")){
                    startActivity(new Intent(getApplicationContext(),MainActivity2.class));
                    finish();
                }
                else if (groupId.equals("5")){
                    startActivity(new Intent(getApplicationContext(),MainActivity3.class));
                    finish();
                }
                else if (groupId.equals("3")){
                    startActivity(new Intent(getApplicationContext(),MainActivity4.class));
                    finish();
                }
                else if (groupId.equals("2")){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
            }
        });

        gifts_recyclerview = findViewById(R.id.gifts_recyclerview);
        progressDialog = new ProgressDialog(this);

        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        gifts_recyclerview.setLayoutManager(manager);

        giftsModels = new ArrayList<>();
        getGifts();

        gifts_recyclerview.addOnItemTouchListener(new RecyclerTouchListener(this, gifts_recyclerview, new RecyclerTouchListener.ClickListener() {
            @Override
            public boolean onClick(View view, int position) {
                GiftsModel list = giftsModels.get(position);
                String giftId = list.getGift_id();
                String giftName = list.getGift_name();
                String giftPrice = list.getGift_price();
                String giftImage = list.getGift_image();

                Intent intent = new Intent(GiftsList.this,SurprisePlanner.class);
                intent.putExtra("GIFT_ID",giftId);
                intent.putExtra("GIFT_NAME",giftName);
                intent.putExtra("GIFT_PRICE",giftPrice);
                intent.putExtra("GIFT_IMAGE",giftImage);
                startActivity(intent);
               // finish();

                return true;
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }


    private void getGifts() {
        progressDialog.setMessage("Loading");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                "https://genieservice.in/api/service/getallgift",
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
                                        String event_id = "", event_name = "", event_price="", event_image = "";
                                        JSONObject o = array.getJSONObject(i);

                                        event_id = o.getString("id");
                                        event_name = o.getString("name");

                                        event_price = o.getString("price");
                                        event_image = o.getString("image");

                                        GiftsModel item = new GiftsModel(
                                                event_id,event_name, event_price, event_image);


                                     //   Log.d("operator_name",event_name);
                                        giftsModels.add(item);

                                    }
                                }
                                else {
                                Toast.makeText(getApplicationContext(), "No Data Found",
                                        Toast.LENGTH_LONG).show();
                                    gifts_recyclerview.setVisibility(View.GONE);
                                    // no_orders_text.setVisibility(View.VISIBLE);
                                }
                            }


                            giftsAdapter = new GiftsAdapter(giftsModels, getApplicationContext());
                            gifts_recyclerview.setAdapter(giftsAdapter);


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
                        Toast.makeText(GiftsList.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(GiftsList.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(GiftsList.this);
        requestQueue.add(stringRequest);
    }

}
