package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/16/2018.
 */

public class LandlineResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @SerializedName("status")
    private boolean status;

}
