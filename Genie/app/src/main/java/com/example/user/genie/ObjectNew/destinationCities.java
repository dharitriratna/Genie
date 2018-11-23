package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class destinationCities {
    @SerializedName("DestinationId")
    private int DestinationId;

    @SerializedName("DestinationName")
    private String DestinationName;

    public int getDestinationId() {
        return DestinationId;
    }

    public void setOriginId(int destinationId) {
        DestinationId = destinationId;
    }

    public String getDestinationName() {
        return DestinationName;
    }

    public void setDestinationName(String destinationName) {
        DestinationName = destinationName;
    }
}