package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.MovieCityModel;
import ratna.genie1.user.genie.MoviesListActivity;
import ratna.genie1.user.genie.R;
import ratna.genie1.user.genie.helper.RegPrefManager;

import java.util.ArrayList;

import ratna.genie1.user.genie.Model.MovieCityModel;
import ratna.genie1.user.genie.MoviesListActivity;
import ratna.genie1.user.genie.helper.RegPrefManager;

/**
 * Created by RatnaDev008 on 10/29/2018.
 */

public class CityMoviesCustomAdapter extends RecyclerView.Adapter<CityMoviesCustomAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MovieCityModel> names;

    public CityMoviesCustomAdapter(Context context, ArrayList<MovieCityModel> names) {
        this.names = names;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(ratna.genie1.user.genie.R.layout.movies_citylayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final MovieCityModel movieCityModel=names.get(position);

        holder.textViewName.setText(movieCityModel.getCity());

        holder.textViewName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String city= (String) holder.textViewName.getText();
                RegPrefManager.getInstance(context).setMovieCityId(""+movieCityModel.getProviderId());
                Intent i=new Intent(context, MoviesListActivity.class);
                RegPrefManager.getInstance(context).setCity(city);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(ratna.genie1.user.genie.R.id.textViewName);
        }
    }

    public void filterList(ArrayList<MovieCityModel> filterdNames) {
        this.names = filterdNames;
        notifyDataSetChanged();
    }
}
