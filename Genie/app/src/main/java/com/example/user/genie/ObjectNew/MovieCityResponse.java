package com.example.user.genie.ObjectNew;

import com.example.user.genie.Model.MovieCityModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/27/2018.
 */

public class MovieCityResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<MovieCityModel> getData() {
        return data;
    }

    public void setData(ArrayList<MovieCityModel> data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private ArrayList<MovieCityModel> data;


}
