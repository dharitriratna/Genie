package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/20/2018.
 */

public class BeneficiaryRegisterResponse {
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
        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public DataID getData() {
            return data;
        }

        public void setData(DataID data) {
            this.data = data;
        }

        @SerializedName("statuscode")
        private String statuscode;
        @SerializedName("status")
        private String status;
        @SerializedName("data")
        private DataID data;



    }

    public class DataID{
        @SerializedName("remitter")
        private RemiterId remitter;

        public RemiterId getRemitter() {
            return remitter;
        }

        public void setRemitter(RemiterId remitter) {
            this.remitter = remitter;
        }

        public BeneficiaryId getBeneficiary() {
            return beneficiary;
        }

        public void setBeneficiary(BeneficiaryId beneficiary) {
            this.beneficiary = beneficiary;
        }

        @SerializedName("beneficiary")
        private BeneficiaryId beneficiary;

    }

    public class RemiterId{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @SerializedName("id")
        private String id;
    }
    public class BeneficiaryId{
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        @SerializedName("id")
        private String id;
    }
}
