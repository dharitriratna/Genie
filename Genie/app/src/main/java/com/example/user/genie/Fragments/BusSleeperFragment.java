package com.example.user.genie.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Adapter.AirplaneAdapter;

import com.example.user.genie.R;
import com.example.user.genie.Utils.AbstractItem;
import com.example.user.genie.Utils.CenterItem;
import com.example.user.genie.Utils.EdgeItem;
import com.example.user.genie.Utils.EmptyItem;
import com.example.user.genie.Utils.OnSeatSelected;

import java.util.ArrayList;
import java.util.List;

public class BusSleeperFragment extends Fragment  {
    private TextView txtSeatSelected;
    private static final int COLUMNS = 5;

    public static BusSleeperFragment newInstance() {

        return new BusSleeperFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.bus_sleeper_fragment, container, false);
        txtSeatSelected = (TextView)v.findViewById(R.id.txt_seat_selected);


        String fontPath = "fonts/Raleway-Light.ttf";
        String fontPath2 = "fonts/Raleway-Thin.ttf";
        String fontPath3 = "fonts/Raleway_SemiBold.ttf";

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(getActivity().getAssets(), fontPath3);

   /*     List<AbstractItem> items = new ArrayList<>();
        for (int i=0; i<50; i++) {

            if (i%COLUMNS==0 || i%COLUMNS==4) {
                items.add(new EdgeItem(String.valueOf(i)));
            } else if (i%COLUMNS==1 || i%COLUMNS==3) {
                items.add(new CenterItem(String.valueOf(i)));
            } else {
                items.add(new EmptyItem(String.valueOf(i)));
            }
        }

        GridLayoutManager manager = new GridLayoutManager(getActivity(), COLUMNS);
        RecyclerView recyclerView = (RecyclerView)v.findViewById(R.id.lst_items);
        recyclerView.setLayoutManager(manager);

        BusSleeperAdapter adapter = new BusSleeperAdapter(getActivity(), items);
        recyclerView.setAdapter(adapter);
*/

        return v;
    }

    /*@Override
    public void onSeatSelected(int count) {

        txtSeatSelected.setText("Book "+count+" seats");
    }*/


}
