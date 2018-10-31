package com.example.user.genie.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.genie.R;

public class FragmentProfile extends Fragment {

    public static FragmentProfile newInstance() {

        return new FragmentProfile();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_profile, container, false);
        //    seasonal_offer=v.findViewById(R.id.seasonal_offer);


        //  Picasso.with(getActivity()).load("http://demo.ratnatechnology.co.in/pujaphulo/uploads/17.png").into(subimage);
        // Picasso.with(getActivity()).load("http://demo.ratnatechnology.co.in/pujaphulo/uploads/13.png").into(decorimage);
        //  Picasso.with(getActivity()).load("http://demo.ratnatechnology.co.in/pujaphulo/uploads/14.png").into(samagriimage);
        //Picasso.with(getActivity()).load("http://demo.ratnatechnology.co.in/pujaphulo/uploads/16.png").into(lightimage);


        String fontPath = "fonts/Raleway-Light.ttf";
        String fontPath2 = "fonts/Raleway-Thin.ttf";
        String fontPath3 = "fonts/Raleway_SemiBold.ttf";

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(getActivity().getAssets(), fontPath3);

        return v;


    }
}
