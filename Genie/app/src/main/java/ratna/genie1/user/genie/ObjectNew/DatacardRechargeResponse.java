package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/15/2018.
 */

public class DatacardRechargeResponse {
    @SerializedName("ApiTransID")
    private String ApiTransID;
    @SerializedName("Status")
    private String Status;
    @SerializedName("ErrorMessage")
    private String ErrorMessage;
    @SerializedName("OperatorRef")
    private String OperatorRef;

    public String getApiTransID() {
        return ApiTransID;
    }

    public void setApiTransID(String apiTransID) {
        ApiTransID = apiTransID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public String getOperatorRef() {
        return OperatorRef;
    }

    public void setOperatorRef(String operatorRef) {
        OperatorRef = operatorRef;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    @SerializedName("TransactionDate")
    private String TransactionDate;

}
