package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class RequestAdminList {
    @SerializedName("adminApprove")
    private String adminApprove;

    public String getAdminApprove() {
        return adminApprove;
    }

    public void setAdminApprove(String adminApprove) {
        this.adminApprove = adminApprove;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRef_no() {
        return ref_no;
    }

    public void setRef_no(String ref_no) {
        this.ref_no = ref_no;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    @SerializedName("amount")
    private String amount;
    @SerializedName("id")
    private String id;
    @SerializedName("ref_no")
    private String ref_no;
    @SerializedName("requesterName")
    private String requesterName;
    @SerializedName("transaction_id")
    private String transaction_id;


}
