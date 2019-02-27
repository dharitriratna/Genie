package ratna.genie1.user.genie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import ratna.genie1.user.genie.helper.RegPrefManager;

public class BePartnerActivity extends AppCompatActivity {
    Toolbar toolbar;
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_be_partner);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

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
    }
}
