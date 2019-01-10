package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.DataCardActivity;
import ratna.genie1.user.genie.LandLine;
import ratna.genie1.user.genie.Model.DataOperatorListModel;
import ratna.genie1.user.genie.ObjectNew.DataCardCircleResponse;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.DataCardActivity;
import ratna.genie1.user.genie.LandLine;
import ratna.genie1.user.genie.helper.RegPrefManager;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class DatacardOperatorCircleCustomAdapter extends RecyclerView.Adapter<DatacardOperatorCircleCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<DataCardCircleResponse> operatorList;

    public DatacardOperatorCircleCustomAdapter(Context context, ArrayList<DataCardCircleResponse> operatorList) {
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

        final DataCardCircleResponse data=operatorList.get(position);

        holder.textViewName.setText(data.getCircle_name());

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String back= RegPrefManager.getInstance(context).getBack();
                if(back.equals("Landline1")) {
                    RegPrefManager.getInstance(context).setLandlineCircle(data.getCircle_name(), data.getCircle_code());

                    context.startActivity(new Intent(context, LandLine.class));
                    ((Activity)context).finish();
                }
                else {
                    RegPrefManager.getInstance(context).setDataCardCircle(data.getCircle_name(), data.getCircle_code());

                    context.startActivity(new Intent(context, DataCardActivity.class));
                    ((Activity)context).finish();
                }
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

    public void filterList(ArrayList<DataCardCircleResponse > filterdNames) {
        this.operatorList = filterdNames;
        notifyDataSetChanged();
    }




}
