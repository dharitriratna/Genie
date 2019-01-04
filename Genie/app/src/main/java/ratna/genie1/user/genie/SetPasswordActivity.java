package ratna.genie1.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetPasswordActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText setPasswordEd;
    Button btnProceed;
    String regPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setPasswordEd = findViewById(R.id.setPasswordEd);
        btnProceed = findViewById(R.id.btnProceed);

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regPassword = setPasswordEd.getText().toString().trim();

                if (regPassword.length() < 1){
                    setPasswordEd.setError("Please Enter Your Password");
                }
                else {
                    startActivity(new Intent(getApplicationContext(),FSERegisterPaymentActivity.class));
                    finish();
                }
            }
        });
    }
}
