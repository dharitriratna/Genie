package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.RentFilterModel;
import ratna.genie1.user.genie.ObjectNew.RentResponse;
import ratna.genie1.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ratna.genie1.user.genie.Model.RentFilterModel;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class RentCustomAdapter1 extends RecyclerView.Adapter<RentCustomAdapter1.ViewHolder> {
    private Context context;
    private ArrayList<RentFilterModel> names;

    public RentCustomAdapter1(Context context, ArrayList<RentFilterModel> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(ratna.genie1.user.genie.R.layout.rentlayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {

        RentFilterModel data=names.get(position);
        holder.nameTv.setText(data.getCategory());
        holder.addressTv.setText("Address"+"\n"+data.getAddress());
        holder.priceTv.setText("Rs."+data.getPrice());
        holder.phoneTv.setText("Phone: "+data.getPhone());
        String image=data.getImage_url();
        String latitude=data.getLatitude();
        String longitude=data.getLongitude();
        final double lat=Double.valueOf(latitude);
        final double lang=Double.valueOf(longitude);
        if(data.getImage_url()!=null){
            Picasso.with(context).load(data.getImage_url()).fit().into(holder.image);
        }
        holder.phoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RentFilterModel data=names.get(position);
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+data.getPhone()));
                context.startActivity(intent);
            }
        });
        holder.addressTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(android.content.Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:"+lat+","+lang));
                Intent.createChooser(intent,"Launch Maps");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        TextView nameTv,priceTv,addressTv,phoneTv;

        ViewHolder(View itemView) {
            super(itemView);

            addressTv = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.addressTv);
            priceTv = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.priceTv);
            nameTv = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.nameTv);
            image = (ImageView) itemView.findViewById(ratna.genie1.user.genie.R.id.image);
            phoneTv=(TextView)itemView.findViewById(ratna.genie1.user.genie.R.id.phoneTv);
        }
    }
    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<RentFilterModel> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}
