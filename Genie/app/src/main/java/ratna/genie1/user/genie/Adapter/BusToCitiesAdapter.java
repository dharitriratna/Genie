package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.BusBookingActivity;
import ratna.genie1.user.genie.ObjectNew.destinationCities;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

public class BusToCitiesAdapter extends RecyclerView.Adapter<BusToCitiesAdapter.ViewHolder> {

    private ArrayList<destinationCities> destinationCitiesModels;
    private Context context;

    public BusToCitiesAdapter(ArrayList<destinationCities> destinationCitiesModels, Context context) {
        this.destinationCitiesModels = destinationCitiesModels;
        this.context = context;
    }

    @Override
    public BusToCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bustocities_boards, parent, false);
        return new BusToCitiesAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(BusToCitiesAdapter.ViewHolder holder, final int position) {
        destinationCities listItem = destinationCitiesModels.get(position);


        holder.origin_name.setText(listItem.getDestinationName());
        holder.origin_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                destinationCities cities=destinationCitiesModels.get(position);
                int id=cities.getDestinationId();
                RegPrefManager.getInstance(context).setBusToID(String.valueOf(id));
                RegPrefManager.getInstance(context).setBusToName(cities.getDestinationName());

                Intent intent = new Intent(context,BusBookingActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return destinationCitiesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView origin_name, origin_id;
        public ViewHolder(View itemView) {
            super(itemView);
            origin_name = itemView.findViewById(R.id.origin_name);
        }
    }

    public void filterList(ArrayList<destinationCities> filterdNames) {
        this.destinationCitiesModels = filterdNames;
        notifyDataSetChanged();
    }
}