package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ratna.genie1.user.genie.helper.RegPrefManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class ViewFSEProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView edit, fseuserimage;
    TextView ownername, owneremail, ownernumber, add_proof, shopaddress, city, pincode, state, country;
    ProgressDialog progressDialog;
    int i = 0;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_fseprofile2);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        progressDialog = new ProgressDialog(this);


        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes


        Log.d("login_user", login_user);

        ownername = findViewById(R.id.ownername);
        owneremail = findViewById(R.id.owneremail);
        ownernumber = findViewById(R.id.ownernumber);
        add_proof = findViewById(R.id.add_proof);
        shopaddress = findViewById(R.id.shopaddress);
        city = findViewById(R.id.city);
        pincode = findViewById(R.id.pincode);
        state = findViewById(R.id.state);
        country = findViewById(R.id.country);
        edit=findViewById(R.id.edit);
        fseuserimage=findViewById(R.id.shopimage);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewFSEProfileActivity.this,UpdateFSEProfileActivity.class));
             //   finish();
            }
        });

        getProfileDetails();
    }

    private void getProfileDetails() {
        progressDialog.setMessage("loading...");
        progressDialog.show();
        StringRequest stringRequest=new
                StringRequest(Request.Method.GET, "https://genieservice.in/api/user/getprofile?user_id="+login_user,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        //   Toast.makeText(UpdateProfile.this, response, Toast.LENGTH_SHORT).show();
                        try {
                            // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                            Log.d("onResponse:", response);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                String user_id=object.getString("id");
                                String name = object.getString("first_name");
                                String mobile=object.getString("phone");
                                String email_address=object.getString("email");
                                String frontphoto=object.getString("front_photo_address");
                                String backphoto=object.getString("back_photo_address");
                                String adProof = object.getString("address_proof");
                                String address = object.getString("line1");
                                String City = object.getString("city");
                                String Pin = object.getString("pin");
                                String State = object.getString("state");
                                String Country = object.getString("country");

                                ownername.setText(name);
                                owneremail.setText(email_address);
                                ownernumber.setText(mobile);
                                add_proof.setText(adProof);
                                shopaddress.setText(address);
                                city.setText(City);
                                pincode.setText(Pin);
                                state.setText(State);
                                country.setText(Country);



                                Picasso.with(getApplicationContext())
                                        .load(frontphoto)
                                        .into(fseuserimage);


                        /*        Picasso.with(getApplicationContext())
                                        .load("http://demo.ratnatechnology.co.in/dogai/uploads/IMG_20180612_150603.jpg")
                                        .placeholder(R.drawable.round_background)   // optional
                                        .error(R.mipmap.ic_launcher)
                                        .into(view_img);


                                full_name.setText(name);
                                phone_no.setText(mobile);
                                email_id.setText(email_address);*/

                                RegPrefManager.getInstance(ViewFSEProfileActivity.this).setPhoneNo(mobile);
                                RegPrefManager.getInstance(ViewFSEProfileActivity.this).setUserName(name);
                                RegPrefManager.getInstance(ViewFSEProfileActivity.this).setUserEmail(email_address);



//                                userid.setText(user_id);

                              /* if (spin_gender.equals(gender)){
                                spin_gender.getSelectedItem();
                            }
                            if (spin_country.equals(country)){
                                spin_country.getSelectedItem();
                            }*/
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.getMessage() == null) {
                    if (i < 3) {
                        Log.e("Retry due to error ", "for time : " + i);
                        i++;
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(ViewFSEProfileActivity.this, "Check your network connection.",
                                Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(ViewFSEProfileActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(ViewFSEProfileActivity.this);
        requestQueue.add(stringRequest);
    }

}
