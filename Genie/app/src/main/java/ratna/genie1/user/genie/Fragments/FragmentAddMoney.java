package ratna.genie1.user.genie.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ratna.genie1.user.genie.CustomHttpClient;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.RequestWalletActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import ratna.genie1.user.genie.CustomHttpClient;

public class FragmentAddMoney extends Fragment {
    EditText amountTv;
    Button btnProceed;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";

    String SendingAmount;
    String ReferralCode;
    Spinner paymentmethodspinner;
    String paymentMethod;
    EditText refCode;

    public static FragmentSendMoney newInstance() {

        return new FragmentSendMoney();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(ratna.genie1.user.genie.R.layout.activity_request_wallet, container, false);
        amountTv =v.findViewById(ratna.genie1.user.genie.R.id.amountTv);
        refCode = v.findViewById(ratna.genie1.user.genie.R.id.refCode);
        btnProceed = v.findViewById(ratna.genie1.user.genie.R.id.btnProceed);
        paymentmethodspinner=v.findViewById(ratna.genie1.user.genie.R.id.paymentmethodspinner);

        paymentmethodspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                paymentMethod= paymentmethodspinner.getItemAtPosition(paymentmethodspinner.getSelectedItemPosition()).toString();
                Toast.makeText(getActivity(),paymentMethod,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); //

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendingAmount = amountTv.getText().toString().trim();
                ReferralCode = refCode.getText().toString().trim();

                if (SendingAmount.length() < 1){
                    amountTv.setText("Please Enter Amount");
                }
                else if (ReferralCode.length() < 1){
                    refCode.setText("Please Enter Code");
                }
                else {
                    new Asynctask().execute();
                }
            }
        });

        return v;
    }

    private class Asynctask extends AsyncTask<Void, Void, Void> {
        ProgressDialog pDialog;
        String success = null,message="",status="true";

        @Override
        protected Void doInBackground(Void... params) {
            pDialog.show();
            ArrayList<NameValuePair> cred = new ArrayList<NameValuePair>();
            cred.add(new BasicNameValuePair("user_id",login_user));
            cred.add(new BasicNameValuePair("amount",SendingAmount ));
            cred.add(new BasicNameValuePair("ref_no",ReferralCode ));
            cred.add(new BasicNameValuePair("payment_method",paymentMethod ));
            Log.v("RES","Sending data " +login_user +SendingAmount+ReferralCode+paymentMethod );

            String urlRouteList=" https://genieservice.in/api/service/moneyTransferReq";
            try {
                String route_response = CustomHttpClient.executeHttpPost(urlRouteList, cred);

                success = route_response;
                JSONObject jsonObject = new JSONObject(success);

                // user_email=jsonObject.getString("user_email");
                status=jsonObject.getString("status");
                if(status.equals("false")) {
                    message = jsonObject.getString("message");
                }
                String data=jsonObject.getString("message");
                // Toast.makeText(Cart.this, sum_total, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject1 = new JSONObject(data);
               /* String user_id=jsonObject1.getString("user_id");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("FLAG", user_id);
                Log.d("user_id", user_id);
                editor.commit();
                String user_phone = jsonObject1.getString("phone");
                RegPrefManager.getInstance(AddMoneyActivity.this).setPhoneNo(user_phone);*/
                //   String user_email=jsonObject1.getString("user_email");

            } catch (Exception e)

            {
                Log.v("Connection error", e.toString());

            }return null;
        }
        protected void onPostExecute(Void result) {
            pDialog.dismiss();

            if(status.equals("true"))
            {
                Toast.makeText(getActivity(),"Sucessfully requested", Toast.LENGTH_LONG).show();
                //   startActivity(new Intent(getApplicationContext(),VerifyWalletOTPActivity.class));
                getActivity().finish();

                //    startActivity(new Intent(AddMoneyActivity.this,OTPActivity.class));finish();
            }
            else{
                Toast.makeText(getActivity(),message, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Loading In...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
    }

}
