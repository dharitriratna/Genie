package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusToCitiesResponse  {
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

    @SerializedName("status")
        private boolean status;
        @SerializedName("data")
        private Data data;

        public class Data{
            public int getResponseStatus() {
                return ResponseStatus;
            }

            public void setResponseStatus(int responseStatus) {
                ResponseStatus = responseStatus;
            }

            public dDestinationOutput getDestinationOutput() {
                return DestinationOutput;
            }

            public void setDestinationOutput(dDestinationOutput destinationOutput) {
                DestinationOutput = destinationOutput;
            }

            @SerializedName("ResponseStatus")
            private int ResponseStatus;
            @SerializedName("DestinationOutput")
            private dDestinationOutput DestinationOutput;


        }

        public class dDestinationOutput{
            public ArrayList<destinationCities> getDestinationCities() {
                return DestinationCities;
            }

            public void setDestinationCities(ArrayList<destinationCities> destinationCities) {
                DestinationCities = destinationCities;
            }

            @SerializedName("DestinationCities")
            private ArrayList<destinationCities> DestinationCities;

        }
    }

