package com.example.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.genie.Model.Movies;
import com.example.user.genie.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class MovielistAdapter extends RecyclerView.Adapter<MovielistAdapter.MyViewHolder> {
    List<Movies> horizontalList = Collections.emptyList();
    Context context;


    public MovielistAdapter(List<Movies> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtview;
        public MyViewHolder(View view) {
            super(view);
            imageView=(ImageView) view.findViewById(R.id.imageview);
           // txtview=(TextView) view.findViewById(R.id.txtview);
        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movielistdetaillayout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.imageView.setImageResource(horizontalList.get(position).imageId);
       // holder.txtview.setText(horizontalList.get(position).txt);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String list = horizontalList.get(position).txt.toString();
               // Toast.makeText(MainActivity.this, list, Toast.LENGTH_SHORT).show();
            }

        });

    }


    @Override
    public int getItemCount()
    {
        return horizontalList.size();
    }

}
