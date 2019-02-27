package ratna.genie1.user.genie;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class RemitterValidateActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView remitterId;
    EditText phoneTv;
    EditText otpTv;
    Button validateBtn;
    private AlertDialog.Builder alertDialog;

    String remitterIdStr;
    String phoneTvStr;
    String otpTvStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remitter_validate);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        alertDialog = new AlertDialog.Builder(this);

        remitterId = findViewById(R.id.remitterId);
        phoneTv = findViewById(R.id.phoneTv);
        otpTv = findViewById(R.id.otpTv);
        validateBtn = findViewById(R.id.validateBtn);

        remitterIdStr = RegPrefManager.getInstance(RemitterValidateActivity.this).getRemitterId();

        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneTvStr = phoneTv.getText().toString().trim();
                otpTvStr = otpTv.getText().toString().trim();

                if (phoneTvStr.length() < 1){
                    phoneTv.setError("Enter Phone No");
                }
                else if (otpTvStr.length() < 1){
                    otpTv.setError("Enter OTP");
                }
                else {
                    if (isNetworkAvailable()) {
                      //  new AsynRemitterValidate().execute();//register add beneficiary
                    }
                    else {
                        noNetwrokErrorMessage();
                    }
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
}
