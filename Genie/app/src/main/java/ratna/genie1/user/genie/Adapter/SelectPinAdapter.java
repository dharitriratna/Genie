package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.CardModel;
import ratna.genie1.user.genie.Model.SelectPinModel;
import ratna.genie1.user.genie.R;

import java.util.List;

public class SelectPinAdapter extends RecyclerView.Adapter<SelectPinAdapter.ViewHolder> {

    private List<SelectPinModel> selectPinModels;
    private Context context;



    public SelectPinAdapter(List<SelectPinModel> selectPinModels, Context context) {
        this.selectPinModels = selectPinModels;
        this.context = context;
    }

    @Override
    public SelectPinAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pins,parent,false);
        return new SelectPinAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(SelectPinAdapter.ViewHolder holder, int position) {
        SelectPinModel listItem = selectPinModels.get(position);


        holder.text_pin_id.setText(listItem.getCity_id());
        holder.text_area_name.setText(listItem.getArea_name());
        //   holder.pro_name.setTypeface(tf3);
        holder.text_pin.setText(listItem.getCode());

    }

    @Override
    public int getItemCount() {
        return selectPinModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView text_pin_id,text_area_name,text_pin;

        public ViewHolder(View itemView) {
            super(itemView);
            text_pin_id = itemView.findViewById(R.id.text_pin_id);
            text_area_name = itemView.findViewById(R.id.text_area_name);
            text_pin = itemView.findViewById(R.id.text_pin);
        }

    }
}
