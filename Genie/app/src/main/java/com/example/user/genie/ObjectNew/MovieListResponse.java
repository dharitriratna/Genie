package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/27/2018.
 */

public class MovieListResponse {
    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<MovieListModel> getData() {
        return data;
    }

    public void setData(ArrayList<MovieListModel> data) {
        this.data = data;
    }

    @SerializedName("data")
    private ArrayList<MovieListModel> data;

}
