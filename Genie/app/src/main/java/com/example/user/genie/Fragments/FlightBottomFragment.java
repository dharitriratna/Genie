package com.example.user.genie.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.user.genie.R;
import com.example.user.genie.helper.RegPrefManager;


public class FlightBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private TextView doneTv,adulTv,childTv,infantTv;
    private ImageView subImg,addImg,subchildImg,addchildImg,subinfantsmg,addinfantsImg;
    private RadioGroup radioClass;
    private RadioButton radioeconomy,radioPremiumEconomy,radioBusiness,radioButton;
    String adultno,childno,infactno,classtext;
    public FlightBottomFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_flight_bottom, container, false);
        doneTv=v.findViewById(R.id.doneTv);
        adulTv=(TextView)v.findViewById(R.id.adulTv);
        childTv=v.findViewById(R.id.childTv);
        infantTv=v.findViewById(R.id.infantTv);
        subImg=v.findViewById(R.id.subImg);
        addImg=v.findViewById(R.id.addImg);
        subchildImg=v.findViewById(R.id.subchildImg);
        addchildImg=v.findViewById(R.id.addchildImg);
        subinfantsmg=v.findViewById(R.id.subinfantsmg);
        addinfantsImg=v.findViewById(R.id.addinfantsImg);
        radioClass=v.findViewById(R.id.radioClass);
     //   radioeconomy=v.findViewById(R.id.radioeconomy);
     //   radioPremiumEconomy=v.findViewById(R.id.radioPremiumEconomy);
     //   radioBusiness=v.findViewById(R.id.radioBusiness);

        doneTv.setOnClickListener(this);
        subImg.setOnClickListener(this);
        addImg.setOnClickListener(this);
        subchildImg.setOnClickListener(this);
        addchildImg.setOnClickListener(this);
        subinfantsmg.setOnClickListener(this);
        addinfantsImg.setOnClickListener(this);


        radioClass.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                   // Toast.makeText(MainActivity.this, rb.getText(), Toast.LENGTH_SHORT).show();
                    classtext=rb.getText().toString();
                    RegPrefManager.getInstance(getContext()).setClassName(classtext);
                    Log.d("Tag",classtext);
                }
            }
        });
        return v;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.subImg:
                if(!adulTv.getText().toString().equals("1")){
                    int value=Integer.parseInt(adulTv.getText().toString());
                    value=value-1;
                    adulTv.setText(""+value);
                }
                break;
            case R.id.addImg:

                    int value=Integer.parseInt(adulTv.getText().toString());
                    value=value+1;
                    adulTv.setText(""+value);

                break;
            case R.id.subchildImg:
                if(!childTv.getText().toString().equals("1")){
                    int valuechild=Integer.parseInt(childTv.getText().toString());
                    valuechild=valuechild-1;
                    childTv.setText(""+valuechild);
                }
                break;
            case R.id.addchildImg:
                int valuechild=Integer.parseInt(childTv.getText().toString());
                valuechild=valuechild+1;
                childTv.setText(""+valuechild);
                break;
            case R.id.subinfantsmg:
                if(!infantTv.getText().toString().equals("1")){
                    int valueinfant=Integer.parseInt(infantTv.getText().toString());
                    valueinfant=valueinfant-1;
                    infantTv.setText(""+valueinfant);
                }
                break;
            case R.id.addinfantsImg:
                int valueinfant=Integer.parseInt(infantTv.getText().toString());
                valueinfant=valueinfant+1;
                infantTv.setText(""+valueinfant);
                break;

            case R.id.doneTv:
                    //get class

                adultno=adulTv.getText().toString();
                childno=childTv.getText().toString();
                infactno=infantTv.getText().toString();

                RegPrefManager.getInstance(getContext()).setAdultNo(adultno);
                RegPrefManager.getInstance(getContext()).setChildNo(childno);
                RegPrefManager.getInstance(getContext()).setInfantNo(infactno);
               dismiss();
                break;
        }
    }
}
