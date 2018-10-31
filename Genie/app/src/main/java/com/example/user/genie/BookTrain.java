package com.example.user.genie;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.genie.common.ApiKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BookTrain extends AppCompatActivity {
    Toolbar toolbar;
    Button search_trains,check_pnr_status;
    EditText from_dest,to_dest,enter_pnr,train_number;
    RadioGroup radioGroup;
    TextView  dept_date;
    RadioButton book_ticket, check_pnr;
    LinearLayout from_to_layout, dept_date_layout,pnr_layout, train_number_layout;
    ProgressDialog progressDialog;
    String pnrNo;
    String train_no,destination_from, destination_to, departure_date;

    private AlertDialog.Builder alertDialog;
    private DatePickerDialog fromDatePickerDialog;
    Calendar mcurrenttime;
    private SimpleDateFormat dateFormatter;
    String Date_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_train);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mcurrenttime = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);

        alertDialog=new AlertDialog.Builder(this);

        progressDialog = new ProgressDialog(this);
        search_trains = findViewById(R.id.search_trains);
        check_pnr_status = findViewById(R.id.check_pnr_status);
        train_number = findViewById(R.id.train_number);
        from_dest = findViewById(R.id.from_dest);
        to_dest = findViewById(R.id.to_dest);
        dept_date = findViewById(R.id.dept_date);
        enter_pnr = findViewById(R.id.enter_pnr);
        book_ticket = findViewById(R.id.book_ticket);
        check_pnr = findViewById(R.id.check_pnr);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.clearCheck();
        int selectedId=radioGroup.getCheckedRadioButtonId();
        from_to_layout = findViewById(R.id.from_to_layout);
        dept_date_layout = findViewById(R.id.dept_date_layout);
        pnr_layout = findViewById(R.id.pnr_layout);
        train_number_layout = findViewById(R.id.train_number_layout);


        dept_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                fromDatePickerDialog = new DatePickerDialog(BookTrain.this, new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        Date_ = dateFormatter.format(newDate.getTime());
                        dept_date.setText(Date_);
                        dept_date.setCursorVisible(false);

                    }},
                        mcurrenttime.get(Calendar.YEAR), mcurrenttime.get(Calendar.MONTH), mcurrenttime.get(Calendar.DAY_OF_MONTH));
                fromDatePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                fromDatePickerDialog.show();
            }
        });

        book_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_ticket.setChecked(true);
                search_trains.setVisibility(View.VISIBLE);
                check_pnr_status.setVisibility(View.GONE);
                pnr_layout.setVisibility(View.GONE);
                from_to_layout.setVisibility(View.VISIBLE);
                dept_date_layout.setVisibility(View.VISIBLE);
             //   train_number_layout.setVisibility(View.VISIBLE);
               // dept_date.setVisibility(View.VISIBLE);
            }
        });
        check_pnr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_trains.setVisibility(View.GONE);
                check_pnr_status.setVisibility(View.VISIBLE);
                pnr_layout.setVisibility(View.VISIBLE);
                from_to_layout.setVisibility(View.GONE);
                dept_date_layout.setVisibility(View.GONE);
            //    train_number_layout.setVisibility(View.GONE);
               // dept_date.setVisibility(View.GONE);
            }
        });

        search_trains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                train_no = train_number.getText().toString().trim();
                destination_from = from_dest.getText().toString().trim();
                destination_to = to_dest.getText().toString().trim();
                departure_date = dept_date.getText().toString().trim();

                if (destination_from.length() < 1){
                    from_dest.setError("Enter Departure Destination");
                }
                else if (destination_to.length() < 1){
                    to_dest.setError("Enter Arriving Destination");
                }
                else if (departure_date.length() < 1){
                    dept_date.setError("Enter Departure Date");
                }
                else {
                    Toast.makeText(BookTrain.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });

        check_pnr_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pnrNo = enter_pnr.getText().toString().trim();

                if(checkString(pnrNo))
                {
                    if(isNetworkAvailable())
                        loadPnrStatus();
                    else
                        noNetwrokErrorMessage();
                }
                else{
                    displayErrorMessage(pnrNo);
                }
            }
        });
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


    public void invalidInputMessage(int code){
        if(code==204) {
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Invalid Pnr No.");
            alertDialog.setCancelable(true);
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }

        else if(code==403){
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Request quota for the api is exceeded.");
            alertDialog.setCancelable(true);
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }

        else
        {
            alertDialog.setTitle("Error!");
            alertDialog.setMessage("Something went wrong with the server. Please refresh after some time");
            alertDialog.setCancelable(true);
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        }
    }

    public boolean checkString(String s)
    {
        boolean isValid=true;
        if(s.isEmpty())
            isValid=false;
        return isValid;
    }

    public boolean checkResponseCode(int code)
    {
        boolean isValid=true;
        if(code!=200)
            isValid=false;
        return isValid;
    }

    public void displayErrorMessage(String s){
        alertDialog.setTitle("Attention!");
        alertDialog.setMessage("Please enter a valid 10 digit pnr no and then click check status.");
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

    private void loadPnrStatus() {
        String url="https://api.railwayapi.com/v2/pnr-status/pnr/"+pnrNo+"/apikey/"+ ApiKey.API_KEY_sk+"/";
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response","hfhfhfgggg");
                        String trainNo="";
                        String trainName="";
                        String trainInfo="";
                        String source="";
                        String destination="";
                        String dateofjourney="";

                        int countPassenger=0;
                        String chartStatus="";
                        String classCode="";
                        ArrayList<String> status= new ArrayList<>();

                        progressDialog.cancel();
                        try {
                            JSONObject object= new JSONObject(response);
                            int responseCode=object.getInt("response_code");
                            if(checkResponseCode(responseCode)){
                                trainNo=object.getString("number");
                                trainName=object.getString("name");
                           //     trainInfo=trainName+"-"+trainNo;
                                dateofjourney=object.getString("doj");
                                JSONObject src=object.getJSONObject("from_station");
                                String name=src.getString("name");
                                String code=src.getString("code");
                                source=name+" "+code;
                                JSONObject dest=object.getJSONObject("to_station");
                                name=dest.getString("name");
                                code=dest.getString("code");
                                destination=name+" "+code;
                                countPassenger=object.getInt("total_passengers");
                                chartStatus=object.getString("chart_prepared");
                                classCode=object.getString("journey_class");
                                JSONArray passengerArray=object.getJSONArray("passengers");
                                for(int i=0;i<passengerArray.length();i++){
                                    JSONObject passObject=passengerArray.getJSONObject(i);
                                    String bookingStatus=passObject.getString("booking_status");
                                    String currentStatus=passObject.getString("current_status");
                                    status.add(i,bookingStatus+"-"+currentStatus);

                                }

                                Log.e("details",trainName+"\n"+trainNo+"\n"+source+"\n"+destination+"\n"+dateofjourney+"\n"+responseCode+"\n"+countPassenger);

                                Toast.makeText(BookTrain.this, response, Toast.LENGTH_SHORT).show();
                                //   tring train, String src,String dest,String date,String c, int count,String chart,LinkedHashMap<String ,String> status
                              /*  PnrStatusFragment pnrStatusFragment= PnrStatusFragment.newInstance(trainInfo,source,destination,dateofjourney,
                                        classCode,countPassenger,chartStatus,status);
                                FragmentManager fragmentManager=getActivity().getFragmentManager();
                                pnrStatusFragment.show(fragmentManager,"pnr");*/
                            }
                            else
                            {
                                invalidInputMessage(responseCode);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressDialog.dismiss();
                        alertDialog.setTitle("Error!");
                        alertDialog.setMessage("Some problem has occurred with the server. Please try after some time.");
                        alertDialog.setCancelable(true);
                        alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog msg=alertDialog.create();
                        msg.show();

                    }
                });

        RequestQueue requestQueue= Volley.newRequestQueue(BookTrain.this);
        requestQueue.add(request);
    }
    }



