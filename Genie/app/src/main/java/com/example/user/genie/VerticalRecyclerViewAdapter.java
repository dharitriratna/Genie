package com.example.user.genie;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.user.genie.VerticalRecyclerViewAdapter.DataObjectHolder.PICK_PHOTO;

/**
 * Created by javierg on 25/01/2017.
 */

public class VerticalRecyclerViewAdapter extends RecyclerView
        .Adapter<VerticalRecyclerViewAdapter
        .DataObjectHolder> {


    private List<VerticalData> mDataset;
    private Context context;

    public VerticalRecyclerViewAdapter(List<VerticalData> myDataset) {
        this.context = context;
        this.mDataset = myDataset;

    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_list_item, parent, false);
        return new DataObjectHolder(view);
    }

    @Override
    public void onBindViewHolder(VerticalRecyclerViewAdapter.DataObjectHolder holder, int position) {
        holder.mLabel.setText(mDataset.get(position).getmTitle());
        holder.mDateTime.setText(mDataset.get(position).getmSubTitle());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private static VerticalRecyclerViewAdapter.MyClickListener sClickListener;

   public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener, com.example.user.genie.DataObjectHolder {
        EditText mLabel;
        EditText mDateTime;
        ImageView product_image,delete;
        Button btn_img_upload;
        static final int PICK_PHOTO = 1958;
        private final int requestCode = 20;
        private Context context;
        File file;
        private String imagefilePath="";


        DataObjectHolder(final View itemView) {
            super(itemView);
            mLabel = (EditText) itemView.findViewById(R.id.vertical_list_item_title);
            mDateTime = (EditText) itemView.findViewById(R.id.vertical_list_item_subtitle);
            product_image = (ImageView) itemView.findViewById(R.id.product_image);
            btn_img_upload = (Button) itemView.findViewById(R.id.btn_img_upload);
            delete = (ImageView) itemView.findViewById(R.id.delete);


       /*     delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemView

                }
            });*/

     /*       btn_img_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    context.startActivity(intent);
                }
            });*/

            itemView.setOnClickListener(this);
            }

        @Override
        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        }
    }

    void setOnItemClickListener(VerticalRecyclerViewAdapter.MyClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }

    void addItem(VerticalData dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    interface MyClickListener {
        void onItemClick(int position, View v);

    }


}
