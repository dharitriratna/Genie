package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.EventsModel;
import ratna.genie1.user.genie.Model.WaterBoardModel;
import ratna.genie1.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> {

    private List<EventsModel> eventsModels;
    private Context context;

    public EventsAdapter(List<EventsModel> eventsModels, Context context) {
        this.eventsModels = eventsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_events,parent,false);
        return new ViewHolder(v);
    }


    @Override

    public void onBindViewHolder(ViewHolder holder, int position) {
        EventsModel listItem = eventsModels.get(position);

        holder.event_id.setText(listItem.getEvent_id());
        holder.event_name.setText(listItem.getEvent_name());
        holder.event_entry_fees.setText("Starting From" +" "+context.getResources().getString(R.string.rupee)+listItem.getEvent_price());
        Picasso.with(context).load(listItem.getEvent_image()).into(holder.event_image);

    }

    @Override
    public int getItemCount() {
        return eventsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView event_name,event_entry_fees,event_id;
        private ImageView event_image;


        public ViewHolder(View itemView) {
            super(itemView);
            event_name = itemView.findViewById(R.id.event_name);
            event_entry_fees = itemView.findViewById(R.id.event_entry_fees);
            event_id = itemView.findViewById(R.id.event_id);
            event_image = itemView.findViewById(R.id.event_image);

        }

    }
}
