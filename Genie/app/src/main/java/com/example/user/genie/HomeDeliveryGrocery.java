package com.example.user.genie;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.Adapter.GoodHomeListAdapter;
import com.example.user.genie.Model.GoodHomeModel;

import java.util.ArrayList;
import java.util.Random;

public class HomeDeliveryGrocery extends AppCompatActivity  implements View.OnClickListener{
    Toolbar toolbar;

    private RecyclerView vertical_fragment_recycler_view;
    private TextView no_item;
    private Button select_btn,btn_submit;
    private ImageView selcted_image,add_beat;
    private GoodHomeListAdapter goodHomeListAdapter;
    private ArrayList<GoodHomeModel> goodHomeModelArrayList;
    private Random mRandom = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_delivery_grocery);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeDeliveryGrocery.this,MainActivity.class));
                finish();
            }
        });

        intialize();


    }
    private void intialize(){
        goodHomeModelArrayList=new ArrayList<>();
        GoodHomeModel goodHomeModel=new GoodHomeModel();
        goodHomeModel.setItem("");
        goodHomeModel.setQty("");
        goodHomeModelArrayList.add(goodHomeModel);

        no_item=findViewById(R.id.no_item);
        vertical_fragment_recycler_view=findViewById(R.id.vertical_fragment_recycler_view);
        select_btn=findViewById(R.id.select_btn);
        selcted_image=findViewById(R.id.selcted_image);
        btn_submit=findViewById(R.id.btn_submit);
        add_beat=findViewById(R.id.add_beat);

        add_beat.setOnClickListener(this);

        if(goodHomeModelArrayList.size()>0) {
            no_item.setVisibility(View.GONE);
            vertical_fragment_recycler_view.setVisibility(View.VISIBLE);

            vertical_fragment_recycler_view.setHasFixedSize(true);
            vertical_fragment_recycler_view.setLayoutManager(new LinearLayoutManager(this));

            goodHomeListAdapter = new GoodHomeListAdapter(HomeDeliveryGrocery.this, goodHomeModelArrayList);

            vertical_fragment_recycler_view.setAdapter(goodHomeListAdapter);
        }
        else {
            no_item.setVisibility(View.VISIBLE);
            vertical_fragment_recycler_view.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_beat:
                // Specify the position
                int position = 0;
              //  String itemLabel = "" + mRandom.nextInt(100);
                GoodHomeModel goodHomeModel=new GoodHomeModel();
                goodHomeModel.setItem("");
                goodHomeModel.setQty("");
                // Add an item to animals list
                goodHomeModelArrayList.add(position,goodHomeModel);

                /*
                    public final void notifyItemInserted (int position)
                        Notify any registered observers that the item reflected at position has been
                        newly inserted. The item previously at position is now at position position + 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                    position : Position of the newly inserted item in the data set

                */

                // Notify the adapter that an item inserted
                goodHomeListAdapter.notifyItemInserted(position);

                /*
                    public void scrollToPosition (int position)
                        Convenience method to scroll to a certain position. RecyclerView does not
                        implement scrolling logic, rather forwards the call to scrollToPosition(int)

                    Parameters
                    position : Scroll to this adapter position

                */
                // Scroll to newly added item position
                vertical_fragment_recycler_view.scrollToPosition(position);
                vertical_fragment_recycler_view.setVisibility(View.VISIBLE);
                no_item.setVisibility(View.GONE);

                // Show the added item label
              //  Toast.makeText(HomeDeliveryGrocery.this,"Added : " + "item",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
