package com.example.user.genie;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.user.genie.Adapter.GridMoviesAdapter;
import com.example.user.genie.Adapter.MovielistAdapter;
import com.example.user.genie.Adapter.MoviesGridListAdapter;
import com.example.user.genie.Adapter.UpcomingMovielistAdapter;
import com.example.user.genie.Model.Movies;

import java.util.ArrayList;
import java.util.List;

public class MoviesListActivity extends AppCompatActivity implements GridMoviesAdapter.ItemClickListener {
    RecyclerView service_recyclerview,upcomingMovieRecyclerview,rvNumbers;
    MovielistAdapter horizontalAdapter;
    UpcomingMovielistAdapter upcomingAdapter;
    private List<Movies> data;
    final int duration = 50;
    final int pixelsToMove =30;
    Toolbar toolbar;
    //private GridView gridList;
    MoviesGridListAdapter gridadapter;
   public GridMoviesAdapter gridMoviesAdapter;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    String[] web = {
            "Ranjha Refugee",
            "Badhaii ho",
            "Baazaar",
            "Baazaar"


    } ;
    int[] imageId = {
            R.drawable.air_ticket_ad,
            R.drawable.ad_movie,
            R.drawable.ad_money_transfer,
            R.drawable.ad_money_transfer


    };

    private final Runnable SCROLLING_RUNNABLE = new Runnable() {

        @Override
        public void run() {
            service_recyclerview.smoothScrollBy(pixelsToMove, 0);
            mHandler.postDelayed(this, duration);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        initialize();
    }
    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
     //   gridList=findViewById(R.id.gridList);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        service_recyclerview=findViewById(R.id.service_recyclerview);
        upcomingMovieRecyclerview=findViewById(R.id.upcomingMovieRecyclerview);
        data = fill_with_data();


        horizontalAdapter=new MovielistAdapter(data, getApplication());

        final LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MoviesListActivity.this, LinearLayoutManager.HORIZONTAL, false);
        service_recyclerview.setLayoutManager(horizontalLayoutManager);
        service_recyclerview.setAdapter(horizontalAdapter);

        upcomingAdapter=new UpcomingMovielistAdapter(data, getApplication());

         LinearLayoutManager horizontalLayoutManager1 = new LinearLayoutManager(MoviesListActivity.this, LinearLayoutManager.HORIZONTAL, false);
        upcomingMovieRecyclerview.setLayoutManager(horizontalLayoutManager1);
        upcomingMovieRecyclerview.setAdapter(upcomingAdapter);

        service_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastItem = horizontalLayoutManager.findLastCompletelyVisibleItemPosition();
                if(lastItem == horizontalLayoutManager.getItemCount()-1){
                    mHandler.removeCallbacks(SCROLLING_RUNNABLE);
                    Handler postHandler = new Handler();
                    postHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            service_recyclerview.setAdapter(null);
                            service_recyclerview.setAdapter(horizontalAdapter);
                            mHandler.postDelayed(SCROLLING_RUNNABLE, 3000);
                        }
                    }, 5000);
                }
            }
        });
        mHandler.postDelayed(SCROLLING_RUNNABLE, 3000);

        /* gridadapter = new MoviesGridListAdapter(MoviesListActivity.this, web, imageId);
       // grid=(GridView)findViewById(R.id.grid);
        gridList.setAdapter(gridadapter);

        gridList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent ii=new Intent(MoviesListActivity.this,MovieShowDetailsActivity.class);
                startActivity(ii);
            }
        });*/

        int numberOfColumns = 2;
        rvNumbers=findViewById(R.id.rvNumbers);
        rvNumbers.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        gridMoviesAdapter = new GridMoviesAdapter(MoviesListActivity.this, web,imageId);
      //  gridMoviesAdapter.setClickListener(this);
        rvNumbers.setAdapter(gridMoviesAdapter);
    }


    public List<Movies> fill_with_data() {

        List<Movies> data = new ArrayList<>();
        data.add(new Movies( R.drawable.air_ticket_ad, "Image 1"));
        data.add(new Movies( R.drawable.ad_movie, "Image 2"));
        data.add(new Movies(  R.drawable.ad_money_transfer, "Image 3"));




        return data;
    }

    @Override
    public void onItemClick(View view, int position) {

    }
}
