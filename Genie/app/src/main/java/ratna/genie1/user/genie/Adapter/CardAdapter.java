package ratna.genie1.user.genie.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ratna.genie1.user.genie.Model.CardModel;
import ratna.genie1.user.genie.R;
import com.squareup.picasso.Picasso;

import java.util.List;



/**
 * Created by user on 24-Feb-18.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

private List<CardModel> cardModels;
private Context context;

   /* String fontPath = "fonts/Raleway-Light.ttf";
    String fontPath2 = "fonts/Raleway-Thin.ttf";
    String fontPath3 = "fonts/Raleway_SemiBold.ttf";*/

public CardAdapter(List<CardModel> cardModels, Context context) {
        this.cardModels = cardModels;
        this.context = context;
        }

  @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cards,parent,false);
        return new ViewHolder(v);
        }


    @Override

   public void onBindViewHolder(ViewHolder holder, int position) {
        CardModel listItem = cardModels.get(position);

      /*  Typeface tf = Typeface.createFromAsset(context.getAssets(), fontPath);
        Typeface tf2 = Typeface.createFromAsset(context.getAssets(), fontPath2);
        Typeface tf3 = Typeface.createFromAsset(context.getAssets(), fontPath3);*/


     //   holder.samagri_amt.setTypeface(tf3);

        holder.id_bank.setText(listItem.getBank_id());
        holder.bank_name.setText(listItem.getBank_name());
        //   holder.pro_name.setTypeface(tf3);
        holder.url.setText(listItem.getBank_url());

        }

 @Override
  public int getItemCount() {
        return cardModels.size();
        }

   public class ViewHolder extends RecyclerView.ViewHolder {

    private TextView id_bank,bank_name,url;



    public ViewHolder(View itemView) {
        super(itemView);
        id_bank = itemView.findViewById(R.id.id_bank);
        bank_name = itemView.findViewById(R.id.bank_name);
        url = itemView.findViewById(R.id.url);



    }

}
}
