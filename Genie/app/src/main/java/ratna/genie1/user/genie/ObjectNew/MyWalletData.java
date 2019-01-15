package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/28/2018.
 */

public class MyWalletData {


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public String getUpdated_on() {
        return updated_on;
    }

    public void setUpdated_on(String updated_on) {
        this.updated_on = updated_on;
    }



    @SerializedName("balance")
    private String balance;
    @SerializedName("created_on")
    private String created_on;
    @SerializedName("updated_on")
    private String updated_on;

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    @SerializedName("senderFirstName")
    private String senderFirstName;
}
