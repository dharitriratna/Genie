package com.example.user.genie.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.genie.MoneyTransfer.SendPaymentActivity;
import com.example.user.genie.R;


public class IMPSFragment extends Fragment implements View.OnClickListener{
private Button continueBtn;

    public IMPSFragment() {
        // Required empty public constructor
    }


    public static IMPSFragment newInstance(String param1, String param2) {
        IMPSFragment fragment = new IMPSFragment();

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
        View v= inflater.inflate(R.layout.fragment_im, container, false);
        continueBtn=(Button)v.findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.continueBtn:
                startActivity(new Intent(getContext(), SendPaymentActivity.class));
                getActivity().finish();
                break;
        }
    }
}
