package ratna.genie1.user.genie;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import ratna.genie1.user.genie.Adapter.AllAddressAdapter;
import ratna.genie1.user.genie.Adapter.OrderAdapter;
import ratna.genie1.user.genie.Fragments.AllOrdersFragment;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment2g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment3g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragment4g;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragmentFt;
import ratna.genie1.user.genie.Fragments.BrowsePlanDetailsFragmentSpecial;
import ratna.genie1.user.genie.Fragments.GiftcardOrdersFragment;
import ratna.genie1.user.genie.Fragments.RechargeandBillOrdersFragment;
import ratna.genie1.user.genie.Fragments.ShoppingOrdersFragment;
import ratna.genie1.user.genie.Fragments.TicketOrdersFragment;
import ratna.genie1.user.genie.Fragments.TravelOrdersFragment;
import ratna.genie1.user.genie.Model.AllAddressModel;
import ratna.genie1.user.genie.Model.OrderModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.OrderModel;

public class MyOrders extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView orders_recyclerview;
    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    String login_user="";
    ProgressDialog progressDialog;
    int i=0;
    private OrderAdapter orderAdapter;
    private List<OrderModel> orderModels;
    private TabLayout orders_tabs;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        orders_tabs = findViewById(R.id.orders_tabs);

      /*  progressDialog = new ProgressDialog(this);
        orders_recyclerview = findViewById(R.id.orders_recyclerview);
        GridLayoutManager manager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        orders_recyclerview.setLayoutManager(manager);

        orderModels = new ArrayList<>();*/

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        login_user=sharedpreferences.getString("FLAG", "");
        editor.commit(); // commit changes

        Log.d("login_user", login_user);


        viewPager = (ViewPager) findViewById(R.id.viewPager);
        /*ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);*/
        setupViewPager(viewPager);

        orders_tabs = (TabLayout) findViewById(R.id.orders_tabs);
        orders_tabs.setupWithViewPager(viewPager);
        setupTabIcons();
    }


    @SuppressLint("NewApi")
    private void setupTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabOne.setText("All");
        tabOne.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        orders_tabs.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabTwo.setText("Recharge & Bills");
        tabTwo.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        orders_tabs.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabThree.setText("Shopping");
        tabThree.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        orders_tabs.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFour.setText("Travel");
        tabFour.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        orders_tabs.getTabAt(3).setCustomView(tabFour);

        TextView tabFive = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabFive.setText("Tickets");
        tabFive.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        orders_tabs.getTabAt(4).setCustomView(tabFive);

        TextView tabSix = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabSix.setText("Gift Cards");
        tabSix.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        orders_tabs.getTabAt(5).setCustomView(tabSix);

      /*  TextView tabSeven = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabSeven.setText("Validity");
        tabSeven.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(6).setCustomView(tabSeven);

        TextView tabEight = (TextView)LayoutInflater.from(this).inflate(R.layout.custom_tablayout, null);
        tabEight.setText("Special");
        tabEight.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        htab_tabs.getTabAt(7).setCustomView(tabEight);*/

    }

    private void setupViewPager(ViewPager viewPager) {
        MyOrders.ViewPagerAdapter adapter = new MyOrders.ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new AllOrdersFragment(), "All");
        adapter.addFrag(new RechargeandBillOrdersFragment(), "Recharge & Bills");
        adapter.addFrag(new ShoppingOrdersFragment(), "Shopping");
        adapter.addFrag(new TravelOrdersFragment(), "Travel");
        adapter.addFrag(new TicketOrdersFragment(), "Tickets");
        adapter.addFrag(new GiftcardOrdersFragment(), "Gift Cards");
      /*  adapter.addFrag(new BrowsePlanDetailsFragment(), "SEVEN");
        adapter.addFrag(new BrowsePlanDetailsFragment(), "EIGHT");*/
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }




}
