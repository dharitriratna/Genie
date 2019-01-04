package ratna.genie1.user.genie;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ratna.genie1.user.genie.Adapter.AllAddressAdapter;
import ratna.genie1.user.genie.Adapter.CardAdapter;
import ratna.genie1.user.genie.Model.AllAddressModel;
import ratna.genie1.user.genie.Model.CardModel;

import java.util.ArrayList;
import java.util.List;

public class AllAddress extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView get_address_recyclerview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_address);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        get_address_recyclerview = findViewById(R.id.get_address_recyclerview);

        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        get_address_recyclerview.setLayoutManager(manager);

    }
}
