package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/15/2018.
 */

public class getDataCardCircle {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<DataCardCircleResponse> getData() {
        return data;
    }

    public void setData(ArrayList<DataCardCircleResponse> data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<DataCardCircleResponse> data;


}
