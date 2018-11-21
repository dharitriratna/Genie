package com.example.user.genie.ObjectNew;

import com.example.user.genie.Model.BeneficiaryDetailsResponse;
import com.example.user.genie.Model.RemiterDetails;
import com.example.user.genie.Model.RemitterLimitResponse;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/21/2018.
 */

public class RemiterDetailsResponse {
    @SerializedName("status")
    private boolean status;

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

    @SerializedName("data")
    private Data data;
    public class Data{
        @SerializedName("status")
        private String status;
        @SerializedName("statuscode")
        private String statuscode;

        @SerializedName("data")
        private RemiterData data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public RemiterData getData() {
            return data;
        }

        public void setData(RemiterData data) {
            this.data = data;
        }
    }
    public class RemiterData{
        @SerializedName("remitter")
        private RemiterDetails remitter;

        public RemiterDetails getRemitter() {
            return remitter;
        }

        public void setRemitter(RemiterDetails remitter) {
            this.remitter = remitter;
        }

        public ArrayList<BeneficiaryDetailsResponse> getBeneficiary() {
            return beneficiary;
        }

        public void setBeneficiary(ArrayList<BeneficiaryDetailsResponse> beneficiary) {
            this.beneficiary = beneficiary;
        }

        public ArrayList<RemitterLimitResponse> getRemitter_limit() {
            return remitter_limit;
        }

        public void setRemitter_limit(ArrayList<RemitterLimitResponse> remitter_limit) {
            this.remitter_limit = remitter_limit;
        }

        @SerializedName("beneficiary")
        private ArrayList<BeneficiaryDetailsResponse> beneficiary;

        @SerializedName("remitter_limit")
        private ArrayList<RemitterLimitResponse> remitter_limit;
    }
}
