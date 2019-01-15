package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class WalletTotalBalanceResponse {

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private Data data;
    @SerializedName("message")
    private String message;

    public class Data{
        public String getTotalWalletBalnce() {
            return totalWalletBalnce;
        }

        public void setTotalWalletBalnce(String totalWalletBalnce) {
            this.totalWalletBalnce = totalWalletBalnce;
        }

        @SerializedName("totalWalletBalnce")
        private String totalWalletBalnce;
    }


}
