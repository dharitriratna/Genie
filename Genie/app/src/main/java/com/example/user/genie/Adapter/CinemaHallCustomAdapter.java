package com.example.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.genie.MoviesListActivity;
import com.example.user.genie.R;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class CinemaHallCustomAdapter extends RecyclerView.Adapter<CinemaHallCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> names;

    public CinemaHallCustomAdapter(Context context, ArrayList<String> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cinemahalladapterlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cinemaTV.setText(names.get(position));
        holder.cinemaTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, MoviesListActivity.class);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView cinemaTV;

        ViewHolder(View itemView) {
            super(itemView);

            cinemaTV = (TextView) itemView.findViewById(R.id.cinemaTV);
        }
    }

}
