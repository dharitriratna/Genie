package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.DTHRecharge;
import ratna.genie1.user.genie.Model.DTHOperatorsCFModel;
import ratna.genie1.user.genie.Model.DTHOperatorsModel;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class DTHOperatorCFAdapter extends RecyclerView.Adapter<DTHOperatorCFAdapter.ViewHolder> {

    private List<DTHOperatorsCFModel> dthOperatorsCFModels;
    private Context context;

    public DTHOperatorCFAdapter(List<DTHOperatorsCFModel> operatorsModels, Context context) {
        this.dthOperatorsCFModels = operatorsModels;
        this.context = context;
    }

    @Override
    public DTHOperatorCFAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mobile_operators,parent,false);
        return new DTHOperatorCFAdapter.ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(DTHOperatorCFAdapter.ViewHolder holder, int position) {
       final DTHOperatorsCFModel listItem = dthOperatorsCFModels.get(position);


     //   holder.mob_operator_id.setText(listItem.getDth_operator_id());
        holder.operator_name.setText(listItem.getDth_operator_name());
        holder.operator_code.setText(listItem.getDth_operator_code());
      //  holder.service_type.setText(listItem.getDth_service_type());
        holder.service_type.setVisibility(View.GONE);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setDTHOperator(listItem.getDth_operator_name(),listItem.getDth_operator_code());

                context.startActivity(new Intent(context, DTHRecharge.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return dthOperatorsCFModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mob_operator_id,operator_name,service_type,operator_code;
        private CardView card_view;

        public ViewHolder(View itemView) {
            super(itemView);
            mob_operator_id = itemView.findViewById(R.id.mob_operator);
            operator_name = itemView.findViewById(R.id.operator_name);
            service_type = itemView.findViewById(R.id.service_type);
            operator_code = itemView.findViewById(R.id.operator_code);
            card_view=itemView.findViewById(R.id.card_view);
        }
    }

    public void filterList(ArrayList<DTHOperatorsCFModel> filterdNames) {
        this.dthOperatorsCFModels = filterdNames;
        notifyDataSetChanged();
    }
}
