package ratna.genie1.user.genie.Adapter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import ratna.genie1.user.genie.R;

import java.util.ArrayList;

public class CustomGridViewAdapter  extends ArrayAdapter<ClipData.Item>
{

    Context context;
    int layoutResourceId;
    ArrayList<ClipData.Item> data = new ArrayList<ClipData.Item>();

    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<ClipData.Item> data)
    {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        RecordHolder holder = null;

        try
        {
            if (row == null)
            {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new RecordHolder();

                row.setTag(holder);
            }
            else
            {
                holder = (RecordHolder) row.getTag();
            }

            ClipData.Item item = data.get(position);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return row;
    }

    public static class RecordHolder
    {
        public TextView txtTitle;
        public ImageView imageItem;

    }
}
