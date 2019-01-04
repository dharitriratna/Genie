package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.JobSearchModel;
import ratna.genie1.user.genie.R;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class JobSearchAdapter extends RecyclerView.Adapter<JobSearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<JobSearchModel> names;

    public JobSearchAdapter(Context context, ArrayList<JobSearchModel> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobsearchlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
       // TrainSeatAvailableModel trainSeatAvailableModel=names.get(position);
        //holder.seatTv.setText(names.get(position));
        /*destTimeTv,arriveTimeTv,destplaceTv,arriveplaceTv*/
        JobSearchModel jobSearchModel=names.get(position);
        holder.titleTv.setText(jobSearchModel.getJobtitle());
        holder.comapnyTv.setText(jobSearchModel.getCompanyname());
        holder.locationTv.setText(jobSearchModel.getLocation());
        holder.detailTv.setText(jobSearchModel.getDetails());

    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTv,comapnyTv,locationTv,detailTv;

        ViewHolder(View itemView) {
            super(itemView);

            detailTv = (TextView) itemView.findViewById(R.id.detailTv);
            locationTv=(TextView)itemView.findViewById(R.id.locationTv);
            comapnyTv=(TextView)itemView.findViewById(R.id.comapnyTv);
            titleTv=(TextView)itemView.findViewById(R.id.titleTv);

        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<JobSearchModel> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}
