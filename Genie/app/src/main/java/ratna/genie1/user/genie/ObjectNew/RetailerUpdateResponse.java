package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class RetailerUpdateResponse {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private FSEUpdateResponse.Data data;

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

    public FSEUpdateResponse.Data getData() {
        return data;
    }

    public void setData(FSEUpdateResponse.Data data) {
        this.data = data;
    }

    public class Data{


        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @SerializedName("first_name")
        private String first_name;
        @SerializedName("phone")
        private String phone;
        @SerializedName("email")
        private String email;
        @SerializedName("username")
        private String username;

    }
}
