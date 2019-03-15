package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class RequestWalletResponse {

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;
}
