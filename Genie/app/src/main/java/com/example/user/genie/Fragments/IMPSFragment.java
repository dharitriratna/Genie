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
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.genie.MoneyTransfer.SendPaymentActivity;
import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;

import java.util.HashMap;


public class IMPSFragment extends Fragment implements View.OnClickListener{
private Button continueBtn;
private TextView nameEd,accontNumberEd,ifscEd;

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
        ifscEd=(EditText)v.findViewById(R.id.ifscEd);
        nameEd=(EditText)v.findViewById(R.id.nameEd);
        accontNumberEd=(EditText)v.findViewById(R.id.accontNumberEd);
        HashMap<String, String> remiter = RegPrefManager.getInstance(getContext()).getRemiterDetails();
        String name=remiter.get("Name");
        String accountName=remiter.get("Account");
        String ifsc=remiter.get("IFSC");
        ifscEd.setText(ifsc);
        nameEd.setText(name);
        accontNumberEd.setText(accountName);

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
