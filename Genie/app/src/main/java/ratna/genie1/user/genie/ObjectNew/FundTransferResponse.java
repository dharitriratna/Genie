package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/22/2018.
 */

public class FundTransferResponse {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private Data data;

    public class Data{
        @SerializedName("ipay_id")
        private String ipay_id;

        public String getIpay_id() {
            return ipay_id;
        }

        public void setIpay_id(String ipay_id) {
            this.ipay_id = ipay_id;
        }

        public String getAgentid() {
            return agentid;
        }

        public void setAgentid(String agentid) {
            this.agentid = agentid;
        }

        public String getCharged_amt() {
            return charged_amt;
        }

        public void setCharged_amt(String charged_amt) {
            this.charged_amt = charged_amt;
        }

        public String getCyrus_id() {
            return cyrus_id;
        }

        public void setCyrus_id(String cyrus_id) {
            this.cyrus_id = cyrus_id;
        }

        public String getLocked_amt() {
            return locked_amt;
        }

        public void setLocked_amt(String locked_amt) {
            this.locked_amt = locked_amt;
        }

        public String getOpening_bal() {
            return opening_bal;
        }

        public void setOpening_bal(String opening_bal) {
            this.opening_bal = opening_bal;
        }

        public String getOpr_id() {
            return opr_id;
        }

        public void setOpr_id(String opr_id) {
            this.opr_id = opr_id;
        }

        public String getTrans_amt() {
            return trans_amt;
        }

        public void setTrans_amt(String trans_amt) {
            this.trans_amt = trans_amt;
        }

        @SerializedName("agentid")
        private String agentid;
        @SerializedName("charged_amt")
        private String charged_amt;
        @SerializedName("cyrus_id")
        private String cyrus_id;
        @SerializedName("locked_amt")
        private String locked_amt;
        @SerializedName("opening_bal")
        private String opening_bal;
        @SerializedName("opr_id")
        private String opr_id;
        @SerializedName("trans_amt")
        private String trans_amt;


    }
}
