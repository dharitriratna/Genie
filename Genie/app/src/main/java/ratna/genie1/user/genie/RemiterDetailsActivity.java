package ratna.genie1.user.genie;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ratna.genie1.user.genie.Adapter.RemiterDetailsCustomAdapter;
import ratna.genie1.user.genie.Model.BeneficiaryDetailsResponse;
import ratna.genie1.user.genie.MoneyTransfer.MoneyTransferActivity;
import ratna.genie1.user.genie.ObjectNew.RemiterDetailsResponse;
import ratna.genie1.user.genie.client.ApiClientGenie;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Adapter.RemiterDetailsCustomAdapter;
import ratna.genie1.user.genie.Model.BeneficiaryDetailsResponse;
import ratna.genie1.user.genie.client.ApiClientGenie1;
import ratna.genie1.user.genie.client.ApiInterface;
import ratna.genie1.user.genie.helper.RegPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemiterDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar toolbar;
    private AlertDialog.Builder alertDialog;
    ApiInterface apiService;
    ProgressDialog progressDialog;
    private TextView addBeneTV,noMesgTv;
    private EditText searchEd;
    private RecyclerView opeartorRecyclerview;
    private RemiterDetailsCustomAdapter adapter;
    private ArrayList<BeneficiaryDetailsResponse> beneficiaryArraylist;
    String groupId;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remiter_details);

        toolbar = findViewById(R.id.toolbar);
        apiService =
                ApiClientGenie1.getClient().create(ApiInterface.class);
        progressDialog =new ProgressDialog(this);
        alertDialog=new AlertDialog.Builder(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(RemiterDetailsActivity.this).getUserGroup();
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

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor1 = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor1.commit(); // commit changes


        Log.d("login_user", login_user);
        intialize();
    }

    private void intialize(){
        addBeneTV=findViewById(R.id.addBeneTV);
        addBeneTV.setOnClickListener(this);
        noMesgTv=findViewById(R.id.noMesgTv);
        searchEd=findViewById(R.id.searchEd);
        opeartorRecyclerview=findViewById(R.id.opeartorRecyclerview);
        beneficiaryArraylist=new ArrayList<>();
        if (isNetworkAvailable()) {
            networkBeneficiary();
        } else {
            noNetwrokErrorMessage();
        }

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addBeneTV:
                startActivity(new Intent(RemiterDetailsActivity.this,AddBeneficiaryActivity.class));
                finish();
                break;
        }
    }
    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<BeneficiaryDetailsResponse> filterdNames = new ArrayList<>();

        for(int i=0;i<beneficiaryArraylist.size();i++) {
            //looping through existing elements
            /*for (String s : placeList) {
                //if the existing elements contains the search input
                if (s.toLowerCase().contains(text.toLowerCase())) {
                    //adding the element to filtered list
                    filterdNames.add(s);
                }
            }*/
            BeneficiaryDetailsResponse model=beneficiaryArraylist.get(i);
            if(model.getName().toLowerCase().contains(text.toLowerCase())){
                filterdNames.add(model);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
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

    private void networkBeneficiary(){
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        String phone= RegPrefManager.getInstance(this).getRemiterPhone();
        Call<RemiterDetailsResponse> call=apiService.postRemiterDetails(login_user,phone);
        call.enqueue(new Callback<RemiterDetailsResponse>() {
            @Override
            public void onResponse(Call<RemiterDetailsResponse> call, Response<RemiterDetailsResponse> response) {
                try{

                progressDialog.dismiss();
                String remitterid=response.body().getData().getData().getRemitter().getId();
                RegPrefManager.getInstance(RemiterDetailsActivity.this).setRemitterId(remitterid);
                RegPrefManager.getInstance(RemiterDetailsActivity.this).setRemiterName(response.body().getData().getData().getRemitter().getName());
                RegPrefManager.getInstance(RemiterDetailsActivity.this).setRemiterPhone(response.body().getData().getData().getRemitter().getMobile());
                beneficiaryArraylist=response.body().getData().getData().getBeneficiary();
                if(beneficiaryArraylist.size()>0){
                    noMesgTv.setVisibility(View.GONE);
                    opeartorRecyclerview.setVisibility(View.VISIBLE);
                    opeartorRecyclerview.setHasFixedSize(true);
                    opeartorRecyclerview.setLayoutManager(new LinearLayoutManager(RemiterDetailsActivity.this));
                    //placeRecyclerview.setItemAnimator(new DefaultItemAnimator());
                    adapter=new RemiterDetailsCustomAdapter(RemiterDetailsActivity.this,beneficiaryArraylist);
                    opeartorRecyclerview.setAdapter(adapter);
                }
                else {
                    noMesgTv.setVisibility(View.VISIBLE);
                    opeartorRecyclerview.setVisibility(View.GONE);
                }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<RemiterDetailsResponse> call, Throwable t) {
                progressDialog.dismiss();
                noMesgTv.setVisibility(View.VISIBLE);
                opeartorRecyclerview.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
        //  return;
    }

}
