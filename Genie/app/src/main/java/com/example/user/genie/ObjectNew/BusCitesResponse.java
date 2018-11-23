package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BusCitesResponse {
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

    public class Data{
        @SerializedName("ResponseStatus")
        private int ResponseStatus;
        @SerializedName("OriginOutput")
        private oRiginOutput OriginOutput;

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int responseStatus) {
            ResponseStatus = responseStatus;
        }

        public oRiginOutput getOriginOutput() {
            return OriginOutput;
        }

        public void setOriginOutput(oRiginOutput originOutput) {
            OriginOutput = originOutput;
        }
    }

    public class  oRiginOutput{
        public ArrayList<oRiginCities> getOriginCities() {
            return OriginCities;
        }

        public void setOriginCities(ArrayList<oRiginCities> originCities) {
            OriginCities = originCities;
        }

        @SerializedName("OriginCities")
        private ArrayList<oRiginCities> OriginCities;
    }

}
