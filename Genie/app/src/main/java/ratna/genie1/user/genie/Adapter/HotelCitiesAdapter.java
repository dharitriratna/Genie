package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.GasProviderModel;
import ratna.genie1.user.genie.Model.HotelCitiesModel;
import ratna.genie1.user.genie.R;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.HotelCitiesModel;

public class HotelCitiesAdapter extends RecyclerView.Adapter<HotelCitiesAdapter.ViewHolder> {

    private List<HotelCitiesModel> hotelCitiesModels;
    private Context context;


    public HotelCitiesAdapter(List<HotelCitiesModel> hotelCitiesModels, Context context) {
        this.hotelCitiesModels = hotelCitiesModels;
        this.context = context;
    }

    @Override
    public HotelCitiesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_cities_hotels,parent,false);
        return new HotelCitiesAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(HotelCitiesAdapter.ViewHolder holder, int position) {
        HotelCitiesModel listItem = hotelCitiesModels.get(position);

    }

    @Override
    public int getItemCount() {
        return hotelCitiesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mob_operator_id,operator_name,service_type,operator_code;


        public ViewHolder(View itemView) {
            super(itemView);
            mob_operator_id = itemView.findViewById(ratna.genie1.user.genie.R.id.mob_operator);
            operator_name = itemView.findViewById(ratna.genie1.user.genie.R.id.operator_name);
            service_type = itemView.findViewById(ratna.genie1.user.genie.R.id.service_type);
            operator_code = itemView.findViewById(ratna.genie1.user.genie.R.id.operator_code);

        }
    }
    public void filterList(ArrayList<HotelCitiesModel> filterdNames) {
        this.hotelCitiesModels = filterdNames;
        notifyDataSetChanged();
    }
}
