package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class planDescription {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(String circle_id) {
        this.circle_id = circle_id;
    }

    public String getRecharge_amount() {
        return recharge_amount;
    }

    public void setRecharge_amount(String recharge_amount) {
        this.recharge_amount = recharge_amount;
    }

    public String getRecharge_talktime() {
        return recharge_talktime;
    }

    public void setRecharge_talktime(String recharge_talktime) {
        this.recharge_talktime = recharge_talktime;
    }

    public String getRecharge_validity() {
        return recharge_validity;
    }

    public void setRecharge_validity(String recharge_validity) {
        this.recharge_validity = recharge_validity;
    }

    public String getRecharge_short_desc() {
        return recharge_short_desc;
    }

    public void setRecharge_short_desc(String recharge_short_desc) {
        this.recharge_short_desc = recharge_short_desc;
    }

    public String getRecharge_long_desc() {
        return recharge_long_desc;
    }

    public void setRecharge_long_desc(String recharge_long_desc) {
        this.recharge_long_desc = recharge_long_desc;
    }

    public String getHsn_sac() {
        return hsn_sac;
    }

    public void setHsn_sac(String hsn_sac) {
        this.hsn_sac = hsn_sac;
    }

    public String getRecharge_type() {
        return recharge_type;
    }

    public void setRecharge_type(String recharge_type) {
        this.recharge_type = recharge_type;
    }

    @SerializedName("id")
    private String id;
    @SerializedName("operator_id")
    private String operator_id;
    @SerializedName("circle_id")
    private String circle_id;
    @SerializedName("recharge_amount")
    private String recharge_amount;
    @SerializedName("recharge_talktime")
    private String recharge_talktime;
    @SerializedName("recharge_validity")
    private String recharge_validity;
    @SerializedName("recharge_short_desc")
    private String recharge_short_desc;
    @SerializedName("recharge_long_desc")
    private String recharge_long_desc;
    @SerializedName("hsn_sac")
    private String hsn_sac;
    @SerializedName("recharge_type")
    private String recharge_type;

}
