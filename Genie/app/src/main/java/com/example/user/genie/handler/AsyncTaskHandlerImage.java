package com.example.user.genie.handler;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;



/**
 * Created by Techinflo on 17-09-16.
 */
public class AsyncTaskHandlerImage extends AsyncTask<String, String, String> {
    //private AsyncTaskCompleteListener callback;
    private AsyncTaskImageListener callback;
    private ProgressDialog progressDialog;
    private String url,path,category,price,desc,phone,address;
    private String userid;
    private Context context;
    public AsyncTaskHandlerImage(Context context, String url, String userid,String category,String price,
                                 String desc,String phone,String address, String path) {
        this.url = url;
        this.userid=userid;
        this.path=path;
        this.category=category;
        this.price=price;
        this.desc=desc;
        this.phone=phone;
        this.address=address;

        this.context = context;
        this.callback = (AsyncTaskImageListener) context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Processing...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }
    @Override
    protected String doInBackground(String... strings) {
        String result = NewworkHandlerImage.getNetworkData(url, userid,category,price,desc,phone,address,path);

        return result;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if ((this.progressDialog != null)
                && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
        if(result!=null){
           callback.onTaskCompleteImage(result);
        }
    }


    }
