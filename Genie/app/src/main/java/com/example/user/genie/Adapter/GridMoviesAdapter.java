package com.example.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.user.genie.MovieShowDetailsActivity;
import com.example.user.genie.R;

/**
 * Created by RatnaDev008 on 10/30/2018.
 */

public class GridMoviesAdapter extends RecyclerView.Adapter<GridMoviesAdapter.ViewHolder> {
    private String[] mData;
    private  int[] Imageid;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public GridMoviesAdapter(Context context, String[] data,int[] Imageid) {
        this.mInflater = LayoutInflater.from(context);
        this.context=context;
        this.mData = data;
        this.Imageid=Imageid;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.gridmovieslayout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(mData[position]);
        holder.imageView.setImageResource(Imageid[position]);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent movies=new Intent(context,MovieShowDetailsActivity.class);
                context.startActivity(movies);
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView,languagetv;
        ImageView imageView;


        ViewHolder(View itemView) {
            super(itemView);

             textView = (TextView) itemView.findViewById(R.id.moviestv);
             languagetv = (TextView) itemView.findViewById(R.id.languagetv);
             imageView = (ImageView)itemView.findViewById(R.id.grid_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
