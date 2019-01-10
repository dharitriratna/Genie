package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import ratna.genie1.user.genie.LandLine;
import ratna.genie1.user.genie.MobileOperators;
import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.Model.DataOperatorListModel;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.ObjectNew.MobileOperatorData;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

public class MobileOperatorsAdapter extends RecyclerView.Adapter<MobileOperatorsAdapter.ViewHolder> {

    private List<MobileOperatorsModel> operatorsModels;
    private Context context;
    private Filter filter;


    public MobileOperatorsAdapter(List<MobileOperatorsModel> operatorsModels, Context context) {
        this.operatorsModels = operatorsModels;
        this.context = context;

    }


    @Override
    public MobileOperatorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mobile_operators,parent,false);
        return new MobileOperatorsAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(MobileOperatorsAdapter.ViewHolder holder, int position) {
        final MobileOperatorsModel listItem = operatorsModels.get(position);


        holder.mob_operator_id.setText(listItem.getOperator_id());
        holder.operator_name.setText(listItem.getOperator_name());
        holder.operator_code.setText(listItem.getOperator_code());

        holder.service_type.setText(listItem.getService_type());

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setMobileOperator(listItem.getOperator_name(),listItem.getOperator_code());

                context.startActivity(new Intent(context,MobileRecharge.class));
              //  ((Activity)context).finish();
            }
        });

    }



    @Override
    public int getItemCount() {
        return operatorsModels.size();
    }


    private void notifyDataSetInvalidated() {
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

    public void filterList(ArrayList<MobileOperatorsModel> filterdNames) {
        this.operatorsModels = filterdNames;
        notifyDataSetChanged();
    }
}
