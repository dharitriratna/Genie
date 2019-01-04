package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.FlightActivity;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.FlightActivity;
import ratna.genie1.user.genie.helper.RegPrefManager;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class FlightPlaceCustomAdapter extends RecyclerView.Adapter<FlightPlaceCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> names;

    public FlightPlaceCustomAdapter(Context context, ArrayList<String> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(ratna.genie1.user.genie.R.layout.movies_citylayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.textViewName.setText(names.get(position));

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String placename= (String) holder.textViewName.getText();
                String place= RegPrefManager.getInstance(context).getPlace();
                if(place.equals("From")) {
                    RegPrefManager.getInstance(context).setFromPlace(placename);
                }
                else {
                    RegPrefManager.getInstance(context).setToPlace(placename);
                }
                context.startActivity(new Intent(context,FlightActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.textViewName);
        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<String> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}
