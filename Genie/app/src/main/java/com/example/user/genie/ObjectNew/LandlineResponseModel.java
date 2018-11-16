package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/15/2018.
 */

public class LandlineResponseModel {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<LandLineData> getData() {
        return data;
    }

    public void setData(ArrayList<LandLineData> data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<LandLineData> data;
}
