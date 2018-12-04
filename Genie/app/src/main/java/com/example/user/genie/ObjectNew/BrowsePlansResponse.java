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
        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int responseStatus) {
            ResponseStatus = responseStatus;
        }

        @SerializedName("Status")
        private int ResponseStatus;
        @SerializedName("PlanDescription")
        private ArrayList<planDescription> PlanDescription;
        @SerializedName("input")
        private InputPlans input;

        public ArrayList<planDescription> getPlanDescription() {
            return PlanDescription;
        }

        public void setPlanDescription(ArrayList<planDescription> planDescription) {
            PlanDescription = planDescription;
        }
    }

    public class InputPlans{
        public String getMemberid() {
            return memberid;
        }

        public void setMemberid(String memberid) {
            this.memberid = memberid;
        }

        public String getPin() {
            return pin;
        }

        public void setPin(String pin) {
            this.pin = pin;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getCircle() {
            return circle;
        }

        public void setCircle(String circle) {
            this.circle = circle;
        }

        public String getRctype() {
            return rctype;
        }

        public void setRctype(String rctype) {
            this.rctype = rctype;
        }

        public String getPageid() {
            return pageid;
        }

        public void setPageid(String pageid) {
            this.pageid = pageid;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        @SerializedName("memberid")
        private String memberid;
        @SerializedName("pin")
        private String pin;
        @SerializedName("phone")
        private String phone;
        @SerializedName("operator")
        private String operator;
        @SerializedName("circle")
        private String circle;
        @SerializedName("rctype")
        private String rctype;
        @SerializedName("pageid")
        private String pageid;
        @SerializedName("user_id")
        private String user_id;
    }
}
