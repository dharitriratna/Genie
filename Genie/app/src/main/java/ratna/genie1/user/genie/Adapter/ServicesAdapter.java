package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.CardModel;
import ratna.genie1.user.genie.Model.ServicesModel;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ServicesAdapter  extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private List<ServicesModel> servicesModels;
    private Context context;
    private int lastPosition = -1;


    public ServicesAdapter(List<ServicesModel> servicesModels, Context context) {
        this.servicesModels = servicesModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_services,parent,false);
        return new ViewHolder(v);
    }

    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        ServicesModel listItem = servicesModels.get(position);


        holder.service_id.setText(listItem.getService_id());
        holder.service_fees.setText(listItem.getService_fee());
        //   holder.pro_name.setTypeface(tf3);
        holder.service_type_name.setText(listItem.getService_name());
        Picasso.with(context).load(listItem.getService_img()).into(holder.service_image);
        RegPrefManager.getInstance(context).setImageServiceSave(listItem.getService_img());
        setAnimation(holder.itemView, position);

    }


    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return servicesModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView service_id,service_type_name,service_fees;
        private ImageView service_image;

        public ViewHolder(View itemView) {
            super(itemView);
            service_id = itemView.findViewById(R.id.service_id);
            service_fees = itemView.findViewById(R.id.service_fees);
            service_type_name = itemView.findViewById(R.id.service_type_name);
            service_image = itemView.findViewById(R.id.service_image);

        }
    }
}

