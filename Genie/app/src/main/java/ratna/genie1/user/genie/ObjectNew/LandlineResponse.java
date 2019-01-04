package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/16/2018.
 */

public class LandlineResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @SerializedName("status")
    private boolean status;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @SerializedName("data")
    private Data data;

    public class Data{
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

}
