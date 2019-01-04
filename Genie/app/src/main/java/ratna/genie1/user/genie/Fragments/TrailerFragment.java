package ratna.genie1.user.genie.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ratna.genie1.user.genie.Adapter.MovielistAdapter;
import ratna.genie1.user.genie.Model.Movies;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.SeatActivity;


import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.Movies;
import ratna.genie1.user.genie.SeatActivity;


public class TrailerFragment extends Fragment implements View.OnClickListener{
    String[] web = {
            "Ranjha Refugee",
            "Badhaii ho",
            "Baazaar",
            "Baazaar"


    } ;
    int[] imageId = {
            ratna.genie1.user.genie.R.drawable.movie1,
            ratna.genie1.user.genie.R.drawable.movie2,
            ratna.genie1.user.genie.R.drawable.movie3,
            ratna.genie1.user.genie.R.drawable.image5


    };
    MovielistAdapter horizontalAdapter;
    private List<Movies> data;
    private Button bookticketbut;
    public TrailerFragment() {
        // Required empty public constructor
    }


    public static TrailerFragment newInstance(String param1, String param2) {
        TrailerFragment fragment = new TrailerFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(ratna.genie1.user.genie.R.layout.fragment_trailer, container, false);
        TextView showmoreTv=(TextView)v.findViewById(ratna.genie1.user.genie.R.id.showmoreTv);
        TextView showlessTv=(TextView)v.findViewById(ratna.genie1.user.genie.R.id.showlessTv);
        RecyclerView service_recyclerview=(RecyclerView)v.findViewById(ratna.genie1.user.genie.R.id.service_recyclerview);
        bookticketbut=(Button)v.findViewById(ratna.genie1.user.genie.R.id.bookticketbut);
        bookticketbut.setOnClickListener(this);
        data = fill_with_data();
        horizontalAdapter=new MovielistAdapter(data, getActivity());

        final LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        service_recyclerview.setLayoutManager(horizontalLayoutManager);
        service_recyclerview.setAdapter(horizontalAdapter);

        return v;
    }

    public List<Movies> fill_with_data() {

        List<Movies> data = new ArrayList<>();
        data.add(new Movies( ratna.genie1.user.genie.R.drawable.movie1, "Image 1"));
        data.add(new Movies( ratna.genie1.user.genie.R.drawable.image5, "Image 2"));
        data.add(new Movies(  ratna.genie1.user.genie.R.drawable.movie2, "Image 3"));




        return data;
    }
    @Override
    public void onClick(View view) {
    switch (view.getId()){
        case ratna.genie1.user.genie.R.id.bookticketbut:
            Intent i=new Intent(getActivity(), SeatActivity.class);
            getActivity().startActivity(i);
            break;
    }
    }
}
