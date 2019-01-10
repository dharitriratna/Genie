package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.FSEListModel;
import ratna.genie1.user.genie.Model.RetailerListModel;
import ratna.genie1.user.genie.R;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.RetailerListModel;

public class RetailerListAdapter extends RecyclerView.Adapter<RetailerListAdapter.ViewHolder> {

    private List<RetailerListModel> retailerListModels;
    private Context context;
    String demo;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

    public RetailerListAdapter(List<RetailerListModel> retailerListModels, Context context) {
        this.retailerListModels = retailerListModels;
        this.context = context;
    }

    @Override
    public RetailerListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(ratna.genie1.user.genie.R.layout.row_fse_lists, parent, false);
        return new RetailerListAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(RetailerListAdapter.ViewHolder holder, int position) {
        RetailerListModel listItem = retailerListModels.get(position);

        holder.userId.setText(listItem.getId());
        holder.userName.setText(listItem.getFirst_name());
        holder.userEmail.setText(listItem.getEmail());
        holder.userNumber.setText(listItem.getPhone());

    }

    @Override
    public int getItemCount() {
        return retailerListModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName, userEmail,userNumber, userId;
        private ImageView userImage;


        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(ratna.genie1.user.genie.R.id.userName);
            userEmail = itemView.findViewById(ratna.genie1.user.genie.R.id.userEmail);
            userNumber = itemView.findViewById(ratna.genie1.user.genie.R.id.userNumber);
            userId = itemView.findViewById(ratna.genie1.user.genie.R.id.userId);
            userImage = itemView.findViewById(ratna.genie1.user.genie.R.id.userImage);

        }
    }

    public void filterList(ArrayList<RetailerListModel> filterdNames) {
        this.retailerListModels = filterdNames;
        notifyDataSetChanged();
    }
}
