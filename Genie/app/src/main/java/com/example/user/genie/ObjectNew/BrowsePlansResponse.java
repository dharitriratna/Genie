package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BrowsePlansResponse {
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
        @SerializedName("PlanDescription")
        private ArrayList<planDescription> PlanDescription;

        public ArrayList<planDescription> getPlanDescription() {
            return PlanDescription;
        }

        public void setPlanDescription(ArrayList<planDescription> planDescription) {
            PlanDescription = planDescription;
        }
    }
}
