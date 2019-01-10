package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ratna.genie1.user.genie.R;


public class AllHotelAdapter extends RecyclerView.Adapter<AllHotelAdapter.MyViewHolder> {
    private String[] bookingId;
    private String[] details;

    private int mSelectedPosition = 0;
    Context context;


    public AllHotelAdapter(Context context, String[] bookingId, String[] details) {
        this.bookingId = bookingId;
        this.details = details;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(context).inflate(R.layout.custom_allhotels, parent, false);
        MyViewHolder holder = new MyViewHolder(view1);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.fees.setText(details[position]);

    }

    @Override
    public int getItemCount() {
        return bookingId.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, booking_name, time, adults, kids, status, instructions;
        public TextView fees;
        public ImageView hotel_image;


        public MyViewHolder(View view1) {
            super(view1);
            fees = view1.findViewById(R.id.fees);
            hotel_image = view1.findViewById(R.id.hotel_image);
        }
    }
}




