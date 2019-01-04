package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class FSESignupResponse {
    @SerializedName("status")
    private boolean status;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @SerializedName("message")
    private String message;
    @SerializedName("user_id")
    private int user_id;
    @SerializedName("phone")
    private String phone;
}
