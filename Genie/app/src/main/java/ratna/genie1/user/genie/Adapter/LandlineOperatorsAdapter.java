package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.LandlineOperatorsModel;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.R;

import java.util.List;

import ratna.genie1.user.genie.Model.LandlineOperatorsModel;

public class LandlineOperatorsAdapter extends RecyclerView.Adapter<LandlineOperatorsAdapter.ViewHolder> {

    private List<LandlineOperatorsModel> landlineOperatorsModels;
    private Context context;



    public LandlineOperatorsAdapter(List<LandlineOperatorsModel> landlineOperatorsModels, Context context) {
        this.landlineOperatorsModels = landlineOperatorsModels;
        this.context = context;
    }

    @Override
    public LandlineOperatorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_landline_operators,parent,false);
        return new LandlineOperatorsAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(LandlineOperatorsAdapter.ViewHolder holder, int position) {
        LandlineOperatorsModel listItem = landlineOperatorsModels.get(position);

        holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.operator_code.setText(listItem.getOperator_code());
        holder.service_type.setText(listItem.getService_type());
    }

    @Override
    public int getItemCount() {
        return landlineOperatorsModels.size();
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
}
