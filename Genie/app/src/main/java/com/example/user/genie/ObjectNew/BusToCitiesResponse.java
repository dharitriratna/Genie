package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusToCitiesResponse  {
    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private Data data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        @SerializedName("ResponseStatus")
        private int ResponseStatus;
        @SerializedName("DestinationOutput")
        private destinationOutput DestinationOutput;

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int responseStatus) {
            ResponseStatus = responseStatus;
        }

        public destinationOutput getDestinationOutput() {
            return DestinationOutput;
        }

        public void setDestinationOutput(destinationOutput destinationOutput) {
            DestinationOutput = destinationOutput;
        }
    }


    public class  destinationOutput{


        @SerializedName("DestinationCities")
        private ArrayList<destinationCities> DestinationCities;

        public ArrayList<destinationCities> getDestinationCities() {
            return DestinationCities;
        }

        public void setDestinationCities(ArrayList<destinationCities> destinationCities) {
            this.DestinationCities = destinationCities;
        }
    }
    }

