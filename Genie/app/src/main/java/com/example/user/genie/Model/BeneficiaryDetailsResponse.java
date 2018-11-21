package com.example.user.genie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/21/2018.
 */

public class BeneficiaryDetailsResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("account")
    private String account;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLast_success_date() {
        return last_success_date;
    }

    public void setLast_success_date(String last_success_date) {
        this.last_success_date = last_success_date;
    }

    public String getLast_success_name() {
        return last_success_name;
    }

    public void setLast_success_name(String last_success_name) {
        this.last_success_name = last_success_name;
    }

    public String getLast_success_imps() {
        return last_success_imps;
    }

    public void setLast_success_imps(String last_success_imps) {
        this.last_success_imps = last_success_imps;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getImps() {
        return imps;
    }

    public void setImps(String imps) {
        this.imps = imps;
    }

    @SerializedName("bank")
    private String bank;
    @SerializedName("status")
    private String status;
    @SerializedName("last_success_date")
    private String last_success_date;
    @SerializedName("last_success_name")
    private String last_success_name;
    @SerializedName("last_success_imps")
    private String last_success_imps;
    @SerializedName("ifsc")
    private String ifsc;
    @SerializedName("imps")
    private String imps;

}
