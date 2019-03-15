package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.MobileRecharge;
import ratna.genie1.user.genie.Model.MobileOperatorsCFModel;
import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

public class MobileOperatorsCFAdapter extends RecyclerView.Adapter<MobileOperatorsCFAdapter.ViewHolder> {

    private List<MobileOperatorsCFModel> mobileOperatorsCFModels;
    private Context context;
    private Filter filter;


    public MobileOperatorsCFAdapter(List<MobileOperatorsCFModel> mobileOperatorsCFModels, Context context) {
        this.mobileOperatorsCFModels = mobileOperatorsCFModels;
        this.context = context;

    }


    @Override
    public MobileOperatorsCFAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mobile_operators,parent,false);
        return new MobileOperatorsCFAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(MobileOperatorsCFAdapter.ViewHolder holder, int position) {
        final MobileOperatorsCFModel listItem = mobileOperatorsCFModels.get(position);

        holder.operator_name.setText(listItem.getOperator_name());
        holder.operator_code.setText(listItem.getOperator_code());



        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                 //   someMethodThatThrowsRuntimeException();
                    RegPrefManager.getInstance(context).setMobileOperator(listItem.getOperator_name(),listItem.getOperator_code());
                    Intent myIntent = new Intent(context,MobileRecharge.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(myIntent);
                 //   context.startActivity(new Intent(context,MobileRecharge.class));
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                    // do something with the runtime exception
                }


              //  ((Activity)context).finish();
            }
        });

    }



    @Override
    public int getItemCount() {
        return mobileOperatorsCFModels.size();
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

    public void filterList(ArrayList<MobileOperatorsCFModel> filterdNames) {
        this.mobileOperatorsCFModels = filterdNames;
        notifyDataSetChanged();
    }
}
