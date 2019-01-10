package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.R;

public class AllBusSearchAdapter extends RecyclerView.Adapter<AllBusSearchAdapter.MyViewHolder> {
    private String[] bookingId;
    private String[] busName;
    private String[] details;
    private String[] journeyDuration;

    private int mSelectedPosition = 0;
    Context context;


    public AllBusSearchAdapter(Context context, String[] bookingId,String[] busName, String[] details, String[]journeyDuration) {
        this.bookingId = bookingId;
        this.busName = busName;
        this.details = details;
        this.journeyDuration = journeyDuration;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_allbuses, parent, false);
        MyViewHolder holder = new MyViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.busNames.setText(busName[position]);
        holder.fees.setText(details[position]);
        holder.duration.setText(journeyDuration[position]);
    }

    @Override
    public int getItemCount() {
        return bookingId.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, booking_name, time, adults, kids, status, instructions;
        public TextView fees,busNames, duration;
        public ImageView bus_banner;


        public MyViewHolder(View view1) {
            super(view1);
            fees = view1.findViewById(R.id.fees);
            busNames = view1.findViewById(R.id.busName);
            duration = view1.findViewById(R.id.duration);
            bus_banner = view1.findViewById(R.id.bus_banner);
        }
    }
}

