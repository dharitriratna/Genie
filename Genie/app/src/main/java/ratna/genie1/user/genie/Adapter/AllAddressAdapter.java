package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.AllAddress;
import ratna.genie1.user.genie.Model.AllAddressModel;
import ratna.genie1.user.genie.Model.CardModel;
import ratna.genie1.user.genie.R;

import java.util.List;

public class AllAddressAdapter extends RecyclerView.Adapter<AllAddressAdapter.ViewHolder> {

    private List<AllAddressModel> addressModels;
    private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public AllAddressAdapter(List<AllAddressModel> addressModels, Context context) {
        this.addressModels = addressModels;
        this.context = context;
    }

    @Override
    public AllAddressAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_address,parent,false);
        return new AllAddressAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(AllAddressAdapter.ViewHolder holder, int position) {
        AllAddressModel listItem = addressModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


        //   holder.samagri_amt.setTypeface(tf3);

        holder.id.setText(listItem.getAdd_id());
        holder.name.setText(listItem.getName());
        //   holder.pro_name.setTypeface(tf3);
        holder.lane.setText(listItem.getAddress_line());
        holder.landmark.setText(listItem.getAddress_landmark());
        holder.city.setText("City : "+listItem.getAdd_city());
        holder.state.setText("State : "+listItem.getAdd_state());
        holder.country.setText("Country : "+listItem.getAdd_country());
        holder.pin.setText("Pin : "+listItem.getAdd_pin());

    }

    @Override
    public int getItemCount() {
        return addressModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id,name,lane,landmark,city,state,country,pin;

        public ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            lane = itemView.findViewById(R.id.lane);
            landmark = itemView.findViewById(R.id.landmark);
            city = itemView.findViewById(R.id.city);
            state = itemView.findViewById(R.id.state);
            country = itemView.findViewById(R.id.country);
            pin = itemView.findViewById(R.id.pin);
        }
    }
}

