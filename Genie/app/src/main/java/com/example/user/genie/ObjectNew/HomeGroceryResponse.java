package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/30/2018.
 */

public class HomeGroceryResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private String data;
}
