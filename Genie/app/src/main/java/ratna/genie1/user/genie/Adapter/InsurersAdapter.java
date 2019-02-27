package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.InsuranceCrowdFinchActivity;
import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.Model.InsurerModel;
import ratna.genie1.user.genie.PayForElectricity;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class InsurersAdapter extends RecyclerView.Adapter<InsurersAdapter.ViewHolder> {

    private List<InsurerModel> insurerModels;
    private Context context;


    public InsurersAdapter(List<InsurerModel> insurerModels, Context context) {
        this.insurerModels = insurerModels;
        this.context = context;
    }

    @Override
    public InsurersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_water_boards,parent,false);
        return new InsurersAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(InsurersAdapter.ViewHolder holder, int position) {
       final InsurerModel listItem = insurerModels.get(position);

       // holder.mob_operator_id.setText(listItem.getElectricity_board_id());
        holder.operator_name.setText(listItem.getOperatorName());
        holder.operator_code.setText(listItem.getOperatorCode());
     //   holder.service_type.setText(listItem.getElectricity_board_type());
        holder.service_type.setVisibility(View.GONE);

        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).SetInsurer(listItem.getOperatorName(),listItem.getOperatorCode());

                context.startActivity(new Intent(context, InsuranceCrowdFinchActivity.class));
               // ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return insurerModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mob_operator_id,operator_name,service_type,operator_code;


        public ViewHolder(View itemView) {
            super(itemView);
            mob_operator_id = itemView.findViewById(R.id.mob_operator);
            operator_name = itemView.findViewById(R.id.operator_name);
            service_type = itemView.findViewById(R.id.service_type);
            operator_code = itemView.findViewById(R.id.operator_code);


        }
    }
    public void filterList(ArrayList<InsurerModel> filterdNames) {
        this.insurerModels = filterdNames;
        notifyDataSetChanged();
    }
}
