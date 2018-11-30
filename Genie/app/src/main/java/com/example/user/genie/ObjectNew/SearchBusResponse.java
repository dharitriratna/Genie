package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchBusResponse {

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
        @SerializedName("SearchOutput")
        private sSearchouput searchouput;

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int responseStatus) {
            ResponseStatus = responseStatus;
        }

        public sSearchouput getSearchouput() {
            return searchouput;
        }

        public void setSearchouput(sSearchouput searchouput) {
            this.searchouput = searchouput;
        }
    }

    public class sSearchouput{
        @SerializedName("AvailableBuses")
        private ArrayList<aVailableBuses> aVailableBusesArrayList;

        public ArrayList<fares> getFares() {
            return Fares;
        }

        public void setFares(ArrayList<fares> fares) {
            Fares = fares;
        }

        @SerializedName("Fares")
        private ArrayList<fares> Fares;

        @SerializedName("BoardingPoints")
        private ArrayList<boardingPoints> BoardingPoints;

        @SerializedName("DroppingPoints")
        private ArrayList<droppingPoints>DroppingPoints;

        public ArrayList<droppingPoints> getDroppingPoints() {
            return DroppingPoints;
        }

        public void setDroppingPoints(ArrayList<droppingPoints> droppingPoints) {
            DroppingPoints = droppingPoints;
        }

        public ArrayList<boardingPoints> getBoardingPoints() {
            return BoardingPoints;
        }

        public void setBoardingPoints(ArrayList<boardingPoints> boardingPoints) {
            BoardingPoints = boardingPoints;
        }

        public ArrayList<aVailableBuses> getaVailableBusesArrayList() {
            return aVailableBusesArrayList;
        }

        public void setaVailableBusesArrayList(ArrayList<aVailableBuses> aVailableBusesArrayList) {
            this.aVailableBusesArrayList = aVailableBusesArrayList;
        }
    }
}
