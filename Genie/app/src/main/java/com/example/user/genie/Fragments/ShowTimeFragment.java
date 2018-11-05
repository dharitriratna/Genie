package com.example.user.genie.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.Adapter.CinemaHallCustomAdapter;
import com.example.user.genie.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ShowTimeFragment extends Fragment implements View.OnClickListener {

    ArrayList<String> names;
   CinemaHallCustomAdapter cinemaHallCustomAdapter;
   TextView todayTv,tommorwTv,thirdTv;

    public ShowTimeFragment() {
        // Required empty public constructor
    }


    public static ShowTimeFragment newInstance(String param1, String param2) {
        ShowTimeFragment fragment = new ShowTimeFragment();

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
        View v= inflater.inflate(R.layout.fragment_show_time, container, false);
        RecyclerView cinemahallRecyclerview=(RecyclerView)v.findViewById(R.id.cinemahallRecyclerview);
         todayTv=(TextView)v.findViewById(R.id.todayTv);
         /*tommorwTv=(TextView)v.findViewById(R.id.tommorwTv);
         thirdTv=(TextView)v.findViewById(R.id.thirdTv);*/
        todayTv.setOnClickListener(this);
       /* tommorwTv.setOnClickListener(this);
        thirdTv.setOnClickListener(this);*/


        cinemahallRecyclerview.setHasFixedSize(true);
        cinemahallRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        names=new ArrayList<>();
        names.add("InOX");
        names.add("Keshri");


        cinemaHallCustomAdapter = new CinemaHallCustomAdapter(getActivity(),names);

        cinemahallRecyclerview.setAdapter(cinemaHallCustomAdapter);

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String today_date = df.format(cal.getTime());
        Log.d("TAG","Today==>"+today_date);
        String[] firstarry=today_date.split("-");
        String first=firstarry[0];
        String first2=firstarry[1];
        String first_date=first+" "+first2;
        todayTv.setText(first_date);
        //check_first_date=today_date;


        cal.add(Calendar.DAY_OF_YEAR,1);
        String tomorrow = df.format(cal.getTime());
        Log.d("TAG","tomorrow==>"+tomorrow);
        String[] secarry=tomorrow.split("-");
        String sec=secarry[0];
        String sec2=secarry[1];
        String tomorrow_date=sec+" "+sec2;
   //     tommorwTv.setText(tomorrow_date);
        //check_tw_date=tomorrow;

        String th=getNextDate(tomorrow);
        Log.d("TAG","th==>"+th);
      //  check_third_date=th;

        // cal.add(Calendar.DAY_OF_YEAR, 2);
        //String third = df.format(cal.getTime());

        // Log.d("TAG","third==>"+third);
        String[] thirdarry=th.split("-");
        String third1=thirdarry[0];
        String third2=thirdarry[1];
        String third=third1+" "+third2;
      //  thirdTv.setText(third);

        String fourth=getNextDate(th);
        Log.d("TAG","fourth==>"+fourth);

        // cal.add(Calendar.DAY_OF_YEAR, 3);
        // String four = df.format(cal.getTime());
        String[] fourarry=fourth.split("-");
        String four1=fourarry[0];
        String four2=fourarry[1];
        String four=four1+" "+four2;
        //check_four_date=fourth;
        return v;
    }

    public static String getNextDate(String  curDate) {
        final SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        final Date date;
        try {
            date = format.parse(curDate);

            final Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            return format.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  null;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.todayTv:
                todayTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                todayTv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                /*thirdTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                thirdTv.setBackgroundResource(R.drawable.edittext_top_bg);
                tommorwTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tommorwTv.setBackgroundResource(R.drawable.edittext_top_bg);

*/
                break;
         /*   case R.id.thirdTv:
                thirdTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                thirdTv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                todayTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                todayTv.setBackgroundResource(R.drawable.edittext_top_bg);
                tommorwTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                tommorwTv.setBackgroundResource(R.drawable.edittext_top_bg);
                break;*/



          /*  case R.id.tommorwTv:
                tommorwTv.setTextColor(getResources().getColor(R.color.colorPrimary));
                tommorwTv.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                todayTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                todayTv.setBackgroundResource(R.drawable.edittext_top_bg);
                thirdTv.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                thirdTv.setBackgroundResource(R.drawable.edittext_top_bg);
                 break;*/



        }
    }
}
