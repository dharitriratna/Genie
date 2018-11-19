package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/19/2018.
 */

public class InsurancePaymentResponse {
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
        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public int getErrorcode() {
            return Errorcode;
        }

        public void setErrorcode(int errorcode) {
            Errorcode = errorcode;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

        @SerializedName("Status")
        private String Status;
        @SerializedName("Errorcode")
        private int Errorcode;
        @SerializedName("Message")
        private String Message;
    }
}
