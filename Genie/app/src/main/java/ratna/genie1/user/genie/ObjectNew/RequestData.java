package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class RequestData {



    @SerializedName("amount")
    private String amount;
    @SerializedName("transaction_id")
    private String transaction_id;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getAdminApprove() {
        return adminApprove;
    }

    public void setAdminApprove(String adminApprove) {
        this.adminApprove = adminApprove;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @SerializedName("ref_no")
    private String ref_no;
    @SerializedName("adminApprove")
    private String adminApprove;
    @SerializedName("requesterName")
    private String requesterName;
    @SerializedName("id")
    private String id;
}
