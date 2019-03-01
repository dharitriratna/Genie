package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.GasBillActivity;
import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.Model.GasProviderModel;
import ratna.genie1.user.genie.Model.WaterBoardModel;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.GasBillActivity;
import ratna.genie1.user.genie.Model.GasProviderModel;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class GasProviderAdapter extends RecyclerView.Adapter<GasProviderAdapter.ViewHolder> {

    private List<GasProviderModel> gasProviderModels;
    private Context context;


    public GasProviderAdapter(List<GasProviderModel> gasProviderModels, Context context) {
        this.gasProviderModels = gasProviderModels;
        this.context = context;
    }

    @Override
    public GasProviderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_gas_boards,parent,false);
        return new GasProviderAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(GasProviderAdapter.ViewHolder holder, int position) {
       final GasProviderModel listItem = gasProviderModels.get(position);

        holder.mob_operator_id.setText(listItem.getGas_board_id());
        holder.operator_name.setText(listItem.getGas_board_name());
        holder.operator_code.setText(listItem.getGas_board_code());
        holder.service_type.setText(listItem.getGas_board_type());

        holder.operator_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setGasBoard(listItem.getGas_board_name(),listItem.getGas_board_code());
                Intent myIntent = new Intent(context,GasBillActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(myIntent);
                context.startActivity(new Intent(context,GasBillActivity.class));
              //  ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return gasProviderModels.size();
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
    public void filterList(ArrayList<GasProviderModel> filterdNames) {
        this.gasProviderModels = filterdNames;
        notifyDataSetChanged();
    }
}
