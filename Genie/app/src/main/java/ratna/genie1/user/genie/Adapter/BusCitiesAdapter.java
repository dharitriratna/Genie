package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.BusCitiesModel;
import ratna.genie1.user.genie.ObjectNew.oRiginCities;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.ToCitesActivity;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.ToCitesActivity;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class BusCitiesAdapter extends RecyclerView.Adapter<BusCitiesAdapter.ViewHolder> {

    private ArrayList<oRiginCities> busCitiesModels;
    private Context context;

    public BusCitiesAdapter(ArrayList<oRiginCities> busCitiesModels, Context context) {
        this.busCitiesModels = busCitiesModels;
        this.context = context;
    }

    @Override
    public BusCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_buscities_boards, parent, false);
        return new BusCitiesAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(BusCitiesAdapter.ViewHolder holder, final int position) {
        oRiginCities listItem = busCitiesModels.get(position);

        holder.origin_name.setText(listItem.getOriginName());
        holder.origin_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oRiginCities cities=busCitiesModels.get(position);
                int id=cities.getOriginId();
                RegPrefManager.getInstance(context).setBusFromID(String.valueOf(id));
                RegPrefManager.getInstance(context).setBusFromName(cities.getOriginName());
                context.startActivity(new Intent(context, ToCitesActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return busCitiesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView origin_name, origin_id;


        public ViewHolder(View itemView) {
            super(itemView);
            origin_name = itemView.findViewById(ratna.genie1.user.genie.R.id.origin_name);
        }
    }

    public void filterList(ArrayList<oRiginCities> filterdNames) {
        this.busCitiesModels = filterdNames;
        notifyDataSetChanged();
    }
}