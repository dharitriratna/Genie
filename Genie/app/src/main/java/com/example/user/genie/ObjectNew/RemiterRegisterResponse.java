package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/20/2018.
 */

public class RemiterRegisterResponse {
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
        @SerializedName("statuscode")
        private String statuscode;

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

        public RemiterData getData() {
            return data;
        }

        public void setData(RemiterData data) {
            this.data = data;
        }

        @SerializedName("status")
        private String status;
        @SerializedName("data")
        private RemiterData data;

    }
    public class RemiterData{
        public RemiterId getRemitter() {
            return remitter;
        }

        public void setRemitter(RemiterId remitter) {
            this.remitter = remitter;
        }

        @SerializedName("remitter")
        private RemiterId remitter;
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
}
