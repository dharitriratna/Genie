package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.EventsModel;
import ratna.genie1.user.genie.Model.GiftsModel;
import ratna.genie1.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GiftsAdapter extends RecyclerView.Adapter<GiftsAdapter.ViewHolder> {

    private List<GiftsModel> giftsModels;
    private Context context;
    String demo;

    public GiftsAdapter(List<GiftsModel> giftsModels, Context context) {
        this.giftsModels = giftsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_gifts_row, parent, false);
        return new ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        GiftsModel listItem = giftsModels.get(position);


        holder.gift_id.setText(listItem.getGift_id());
        holder.gift_name.setText(listItem.getGift_name());
        holder.gift_price.setText(context.getResources().getString(R.string.rupee) + listItem.getGift_price());
        Picasso.with(context).load(listItem.getGift_image()).into(holder.gift_image);



    }

    @Override
    public int getItemCount() {
        return giftsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView gift_name, gift_price, gift_id;
        private ImageView gift_image;


        public ViewHolder(View itemView) {
            super(itemView);
            gift_name = itemView.findViewById(R.id.gift_name);
            gift_price = itemView.findViewById(R.id.gift_price);
            gift_id = itemView.findViewById(R.id.gift_id);
            gift_image = itemView.findViewById(R.id.gift_image);

        }
    }
}
