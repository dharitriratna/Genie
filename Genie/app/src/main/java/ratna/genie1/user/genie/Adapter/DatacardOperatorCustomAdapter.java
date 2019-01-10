package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import ratna.genie1.user.genie.CabBookingActivity;
import ratna.genie1.user.genie.DataCardActivity;
import ratna.genie1.user.genie.Model.DataOperatorListModel;
import ratna.genie1.user.genie.ObjectNew.getDataCardOperatorResponse;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class DatacardOperatorCustomAdapter extends RecyclerView.Adapter<DatacardOperatorCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataOperatorListModel> operatorList;

    public DatacardOperatorCustomAdapter(Context context, ArrayList<DataOperatorListModel> operatorList) {
        this.operatorList = operatorList;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movies_citylayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final DataOperatorListModel data=operatorList.get(position);

        holder.textViewName.setText(data.getOperator_name());

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegPrefManager.getInstance(context).setDataCardOperator(data.getOperator_name(),data.getOperator_code());

                context.startActivity(new Intent(context,DataCardActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return operatorList.size();
    }




    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }
    }

    public void filterList(ArrayList<DataOperatorListModel > filterdNames) {
        this.operatorList = filterdNames;
        notifyDataSetChanged();
    }




}
