package com.example.user.genie.Fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.genie.R;

public class YourFragment extends Fragment {

    public static FragmentMain newInstance() {

        return new FragmentMain();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_main, container, false);


        String fontPath = "fonts/Raleway-Light.ttf";
        String fontPath2 = "fonts/Raleway-Thin.ttf";
        String fontPath3 = "fonts/Raleway_SemiBold.ttf";

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(getActivity().getAssets(), fontPath3);

        return v;

    }
}
