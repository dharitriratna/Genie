package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.DataCardActivity;
import ratna.genie1.user.genie.InsuranceDetailsActivity;
import ratna.genie1.user.genie.LandLine;
import ratna.genie1.user.genie.Model.AllInsurarModel;
import ratna.genie1.user.genie.Model.InsuranceDetails;
import ratna.genie1.user.genie.ObjectNew.DataCardCircleResponse;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.InsuranceDetailsActivity;
import ratna.genie1.user.genie.Model.AllInsurarModel;
import ratna.genie1.user.genie.helper.RegPrefManager;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class AllInsuranceCustomAdapter extends RecyclerView.Adapter<AllInsuranceCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AllInsurarModel> operatorList;



    public AllInsuranceCustomAdapter(Context context, ArrayList<AllInsurarModel> operatorList) {
        this.operatorList = operatorList;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(ratna.genie1.user.genie.R.layout.movies_citylayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        final AllInsurarModel data=operatorList.get(position);

        holder.textViewName.setText(data.getName());

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    RegPrefManager.getInstance(context).setInsuranceId(data.getId());
                    RegPrefManager.getInstance(context).setServiceName(data.getName());
                    context.startActivity(new Intent(context, InsuranceDetailsActivity.class));

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

            textViewName = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.textViewName);
        }
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<AllInsurarModel > filterdNames) {
        this.operatorList = filterdNames;
        notifyDataSetChanged();
    }




}
