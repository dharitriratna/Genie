package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/22/2018.
 */

public class BeneficiaryDeleteResponse {
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
        @SerializedName("statuscode")
        private String statuscode;

        public String getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(String statuscode) {
            this.statuscode = statuscode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public OTPData getData() {
            return data;
        }

        public void setData(OTPData data) {
            this.data = data;
        }

        @SerializedName("status")
        private String status;
        @SerializedName("data")
        private OTPData data;
    }
    public class OTPData{
        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        @SerializedName("otp")
        private String otp;
    }
}
