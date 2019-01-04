package ratna.genie1.user.genie.Fragments;

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

import ratna.genie1.user.genie.MoneyTransfer.SendPaymentActivity;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.HashMap;

import ratna.genie1.user.genie.MoneyTransfer.SendPaymentActivity;
import ratna.genie1.user.genie.helper.RegPrefManager;


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
        View v= inflater.inflate(ratna.genie1.user.genie.R.layout.fragment_im, container, false);
        continueBtn=(Button)v.findViewById(ratna.genie1.user.genie.R.id.continueBtn);
        ifscEd=(EditText)v.findViewById(ratna.genie1.user.genie.R.id.ifscEd);
        nameEd=(EditText)v.findViewById(ratna.genie1.user.genie.R.id.nameEd);
        accontNumberEd=(EditText)v.findViewById(ratna.genie1.user.genie.R.id.accontNumberEd);
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
            case ratna.genie1.user.genie.R.id.continueBtn:
                startActivity(new Intent(getContext(), SendPaymentActivity.class));
                getActivity().finish();
                break;
        }
    }
}
