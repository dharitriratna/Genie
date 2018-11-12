package com.example.user.genie.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.genie.Model.GoodHomeModel;
import com.example.user.genie.Model.TrainSeatAvailableModel;
import com.example.user.genie.R;
import com.example.user.genie.TrainAvailability;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class GoodHomeListAdapter extends RecyclerView.Adapter<GoodHomeListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<GoodHomeModel> names;
    private ArrayList<String> seatAvail;


    public GoodHomeListAdapter(Context context, ArrayList<GoodHomeModel> names) {
        this.names = names;
        this.context=context;
        seatAvail=new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vertical_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GoodHomeModel goodHomeModel=names.get(position);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Remove the item on remove/button click
                names.remove(position);

                /*
                    public final void notifyItemRemoved (int position)
                        Notify any registered observers that the item previously located at position
                        has been removed from the data set. The items previously located at and
                        after position may now be found at oldPosition - 1.

                        This is a structural change event. Representations of other existing items
                        in the data set are still considered up to date and will not be rebound,
                        though their positions may be altered.

                    Parameters
                        position : Position of the item that has now been removed
                */
                notifyItemRemoved(position);

                /*
                    public final void notifyItemRangeChanged (int positionStart, int itemCount)
                        Notify any registered observers that the itemCount items starting at
                        position positionStart have changed. Equivalent to calling
                        notifyItemRangeChanged(position, itemCount, null);.

                        This is an item change event, not a structural change event. It indicates
                        that any reflection of the data in the given position range is out of date
                        and should be updated. The items in the given range retain the same identity.

                    Parameters
                        positionStart : Position of the first item that has changed
                        itemCount : Number of items that have changed
                */
                notifyItemRangeChanged(position,names.size());

                // Show the removed item label
           //     Toast.makeText(mContext,"Removed : " + itemLabel,Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        EditText vertical_list_item_title,vertical_list_item_subtitle;
        ImageView delete;

        ViewHolder(View itemView) {
            super(itemView);

            vertical_list_item_title = (EditText) itemView.findViewById(R.id.vertical_list_item_title);
            vertical_list_item_subtitle=(EditText)itemView.findViewById(R.id.vertical_list_item_subtitle);
            delete=(ImageView)itemView.findViewById(R.id.delete);

        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<GoodHomeModel> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }


}
