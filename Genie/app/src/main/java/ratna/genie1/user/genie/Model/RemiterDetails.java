package ratna.genie1.user.genie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/21/2018.
 */

public class RemiterDetails {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getKycstatus() {
        return kycstatus;
    }

    public void setKycstatus(String kycstatus) {
        this.kycstatus = kycstatus;
    }

    public int getConsumedlimit() {
        return consumedlimit;
    }

    public void setConsumedlimit(int consumedlimit) {
        this.consumedlimit = consumedlimit;
    }

    public int getRemaininglimit() {
        return remaininglimit;
    }

    public void setRemaininglimit(int remaininglimit) {
        this.remaininglimit = remaininglimit;
    }

    public String getKycdocs() {
        return kycdocs;
    }

    public void setKycdocs(String kycdocs) {
        this.kycdocs = kycdocs;
    }

    public int getPerm_txn_limit() {
        return perm_txn_limit;
    }

    public void setPerm_txn_limit(int perm_txn_limit) {
        this.perm_txn_limit = perm_txn_limit;
    }

    @SerializedName("mobile")
    private String mobile;
    @SerializedName("address")
    private String address;
    @SerializedName("pincode")
    private String pincode;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("kycstatus")
    private String kycstatus;

    @SerializedName("consumedlimit")
    private int consumedlimit;
    @SerializedName("remaininglimit")
    private int remaininglimit;
    @SerializedName("kycdocs")
    private String kycdocs;
    @SerializedName("perm_txn_limit")
    private int perm_txn_limit;

}
