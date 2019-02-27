package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.DTHRecharge;
import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.Model.WaterBoardModel;
import ratna.genie1.user.genie.PayForElectricity;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.PayForElectricity;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class ElectricityBoardsAdapter extends RecyclerView.Adapter<ElectricityBoardsAdapter.ViewHolder> {

    private List<ElectricityBoardModel> electricityBoardModels;
    private Context context;


    public ElectricityBoardsAdapter(List<ElectricityBoardModel> electricityBoardModels, Context context) {
        this.electricityBoardModels = electricityBoardModels;
        this.context = context;
    }

    @Override
    public ElectricityBoardsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_water_boards,parent,false);
        return new ElectricityBoardsAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(ElectricityBoardsAdapter.ViewHolder holder, int position) {
       final ElectricityBoardModel listItem = electricityBoardModels.get(position);

       // holder.mob_operator_id.setText(listItem.getElectricity_board_id());
        holder.operator_name.setText(listItem.getElectricity_board_name());
        holder.operator_code.setText(listItem.getElectricity_board_code());
     //   holder.service_type.setText(listItem.getElectricity_board_type());
        holder.service_type.setVisibility(View.GONE);

        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).SetElectricityBoard(listItem.getElectricity_board_name(),listItem.getElectricity_board_code());

                context.startActivity(new Intent(context, PayForElectricity.class));
               // ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return electricityBoardModels.size();
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
    public void filterList(ArrayList<ElectricityBoardModel> filterdNames) {
        this.electricityBoardModels = filterdNames;
        notifyDataSetChanged();
    }
}
