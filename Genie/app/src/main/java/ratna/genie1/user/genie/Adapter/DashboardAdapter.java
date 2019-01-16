package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ratna.genie1.user.genie.Model.FSEListModel;
import ratna.genie1.user.genie.ObjectNew.DashboardList;
import ratna.genie1.user.genie.R;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<DashboardList> dashboardLists;
    private Context context;
    String demo;

    public DashboardAdapter(List<DashboardList> dashboardLists, Context context) {
        this.dashboardLists = dashboardLists;
        this.context = context;
    }

    @Override
    public DashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fsedashboardfragment, parent, false);
        return new DashboardAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(DashboardAdapter.ViewHolder holder, int position) {
        DashboardList listItem = dashboardLists.get(position);

        holder.userId.setText(listItem.getId());
        holder.userName.setText(listItem.getFirst_name());
        holder.userEmail.setText(listItem.getEmail());
        holder.userNumber.setText(listItem.getPhone());
        }

    @Override
    public int getItemCount() {
        return dashboardLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView userName, userEmail,userNumber, userId;
        private ImageView userImage;


        public ViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            userNumber = itemView.findViewById(R.id.userNumber);
            userId = itemView.findViewById(R.id.userId);
            userImage = itemView.findViewById(R.id.userImage);

        }
    }
    public void filterList(ArrayList<DashboardList> filterdNames) {
        this.dashboardLists = filterdNames;
        notifyDataSetChanged();
    }
}
