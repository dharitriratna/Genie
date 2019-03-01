package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.Model.DTHOperatorsModel;
import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.Model.WaterBoardModel;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.WaterBill;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.WaterBoardModel;
import ratna.genie1.user.genie.WaterBill;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class WaterBoardAdapter extends RecyclerView.Adapter<WaterBoardAdapter.ViewHolder> {

    private List<WaterBoardModel> waterBoardModels;
    private Context context;


    public WaterBoardAdapter(List<WaterBoardModel> waterBoardModels, Context context) {
        this.waterBoardModels = waterBoardModels;
        this.context = context;
    }

    @Override
    public WaterBoardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_water_boards,parent,false);
        return new WaterBoardAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(WaterBoardAdapter.ViewHolder holder, int position) {
       final WaterBoardModel listItem = waterBoardModels.get(position);


        holder.mob_operator_id.setText(listItem.getWater_board_id());
        holder.operator_name.setText(listItem.getWater_board_name());
        holder.operator_code.setText(listItem.getWater_board_code());
        holder.service_type.setText(listItem.getWater_board_type());


        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    RegPrefManager.getInstance(context).setWaterBoard(listItem.getWater_board_name(),listItem.getWater_board_code());
                    Intent myIntent = new Intent(context,WaterBill.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                   // context.startActivity(new Intent(context,WaterBill.class));
                    // ((Activity)context).finish();
                }catch (RuntimeException ex){
                    ex.printStackTrace();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return waterBoardModels.size();
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
    public void filterList(ArrayList<WaterBoardModel> filterdNames) {
        this.waterBoardModels = filterdNames;
        notifyDataSetChanged();
    }
}
