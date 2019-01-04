package ratna.genie1.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import ratna.genie1.user.genie.helper.RegPrefManager;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class FlightTravellerInfoActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView listTv,priceTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_traveller_info);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onBackPressed();
                startActivity(new Intent(FlightTravellerInfoActivity.this,FlightBaggageActivity.class));
                finish();
            }
        });
        listTv=toolbar.findViewById(R.id.listTv);
        String fromplace= RegPrefManager.getInstance(this).getFromPlace();
        String toplace= RegPrefManager.getInstance(this).getToPlace();
        listTv.setText(fromplace+" - "+toplace);

        priceTv=findViewById(R.id.priceTv);
        priceTv.setText(getResources().getString(R.string.rupee)+"22,600");
    }
}
