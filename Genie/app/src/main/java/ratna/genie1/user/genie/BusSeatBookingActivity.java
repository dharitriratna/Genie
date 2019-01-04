package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Adapter.AirplaneAdapter;
import ratna.genie1.user.genie.Adapter.CustomGridViewAdapter;
import ratna.genie1.user.genie.Adapter.ViewPagerAdapter;
import ratna.genie1.user.genie.Fragments.BusSeatCumSleeperFragment;
import ratna.genie1.user.genie.Fragments.BusSeatFragment;
import ratna.genie1.user.genie.Fragments.BusSleeperFragment;
import ratna.genie1.user.genie.Fragments.ShowTimeFragment;
import ratna.genie1.user.genie.Fragments.TrailerFragment;
import ratna.genie1.user.genie.Utils.AbstractItem;
import ratna.genie1.user.genie.Utils.CenterItem;
import ratna.genie1.user.genie.Utils.EdgeItem;
import ratna.genie1.user.genie.Utils.EmptyItem;


import java.util.ArrayList;
import java.util.List;

public class BusSeatBookingActivity extends AppCompatActivity  {
    Toolbar toolbar;
    private static final int COLUMNS = 5;
    private TextView txtSeatSelected;
    private TabLayout htab_tabs;
    private ViewPager viewPager;


    @SuppressLint({"ResourceType", "NewApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_seat_booking);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
       /* txtSeatSelected = (TextView)findViewById(R.id.txt_seat_selected);

        List<AbstractItem> items = new ArrayList<>();
        for (int i=0; i<50; i++) {

            if (i%COLUMNS==0 || i%COLUMNS==4) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i%COLUMNS==1 || i%COLUMNS==3) {
                items.add(new CenterItem(String.valueOf(i)));
            } else {
                items.add(new EmptyItem(String.valueOf(i)));
            }
        }

        GridLayoutManager manager = new GridLayoutManager(this, COLUMNS);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
        recyclerView.setLayoutManager(manager);

        AirplaneAdapter adapter = new AirplaneAdapter(this, items);
        recyclerView.setAdapter(adapter);*/

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);

        htab_tabs = (TabLayout) findViewById(R.id.htab_tabs);
        htab_tabs.setupWithViewPager(viewPager);
        setupTabIcons();
    }



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("Seats");

        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
        htab_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Sleepers");

        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        // tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_call, 0, 0);
        htab_tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabThree.setText("Seat/Sleepers");

        tabThree.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(2).setCustomView(tabThree);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BusSeatFragment(), "Seats Only");
        adapter.addFragment(new BusSleeperFragment(), "Sleepers");
        adapter.addFragment(new BusSeatCumSleeperFragment(), "Seat/Sleepers");

        viewPager.setAdapter(adapter);
    }
}