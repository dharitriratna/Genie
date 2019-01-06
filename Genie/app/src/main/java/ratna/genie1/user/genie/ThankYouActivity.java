package ratna.genie1.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ratna.genie1.user.genie.helper.RegPrefManager;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class ThankYouActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    TextView myorder;
    Button continue_shopping;
    String back;
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        groupId = RegPrefManager.getInstance(getApplicationContext()).getUserGroup();
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
        myorder = findViewById(R.id.myorder);
        continue_shopping = findViewById(R.id.continue_shopping);
        myorder.setOnClickListener(this);
        continue_shopping.setOnClickListener(this);

        back= RegPrefManager.getInstance(this).getBack();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.myorder:
                /*if(back.equals("CabBook")){
                 startActivity(new Intent(ThankYouActivity.this,CabBookingActivity.class));
                 finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), MyOrders.class));
                    finish();
                }*/
                startActivity(new Intent(getApplicationContext(), MyOrders.class));
                finish();
                break;
            case R.id.continue_shopping:
              /*  if(back.equals("CabBook")){
                    startActivity(new Intent(ThankYouActivity.this,CabBookingActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }*/
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                break;
        }
    }
}
