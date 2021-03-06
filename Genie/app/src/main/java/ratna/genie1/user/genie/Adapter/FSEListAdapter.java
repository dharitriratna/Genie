package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.ElectricityBoardModel;
import ratna.genie1.user.genie.Model.FSEListModel;
import ratna.genie1.user.genie.Model.GiftsModel;
import ratna.genie1.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FSEListAdapter extends RecyclerView.Adapter<FSEListAdapter.ViewHolder> {

    private List<FSEListModel> fseListModels;
    private Context context;
    String demo;

    public FSEListAdapter(List<FSEListModel> fseListModels, Context context) {
        this.fseListModels = fseListModels;
        this.context = context;
    }

    @Override
    public FSEListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fse_lists, parent, false);
        return new FSEListAdapter.ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(FSEListAdapter.ViewHolder holder, int position) {
        FSEListModel listItem = fseListModels.get(position);

        holder.userId.setText(listItem.getId());
        holder.userName.setText(listItem.getFirst_name());
        holder.userEmail.setText(listItem.getEmail());
        holder.userNumber.setText(listItem.getPhone());
        }

    @Override
    public int getItemCount() {
        return fseListModels.size();
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
    public void filterList(ArrayList<FSEListModel> filterdNames) {
        this.fseListModels = filterdNames;
        notifyDataSetChanged();
    }
}
