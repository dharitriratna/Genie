package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class OperatorFinderResponse {
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
        public int getStatus() {
            return Status;
        }

        public void setStatus(int status) {
            Status = status;
        }

        public String getOperatorCode() {
            return OperatorCode;
        }

        public void setOperatorCode(String operatorCode) {
            OperatorCode = operatorCode;
        }

        public String getCircleCode() {
            return CircleCode;
        }

        public void setCircleCode(String circleCode) {
            CircleCode = circleCode;
        }

        @SerializedName("Status")
        private int Status;
        @SerializedName("OperatorCode")
        private String OperatorCode;
        @SerializedName("CircleCode")
        private String CircleCode;
    }
}
