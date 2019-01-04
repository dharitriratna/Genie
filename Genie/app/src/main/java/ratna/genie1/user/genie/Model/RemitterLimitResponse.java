package ratna.genie1.user.genie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/21/2018.
 */

public class RemitterLimitResponse {
    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Limit getLimit() {
        return limit;
    }

    public void setLimit(Limit limit) {
        this.limit = limit;
    }

    @SerializedName("status")
    private String status;
    @SerializedName("mode")
    private Mode mode;
    @SerializedName("limit")
    private Limit limit;

    public class Limit{
        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getConsumed() {
            return consumed;
        }

        public void setConsumed(String consumed) {
            this.consumed = consumed;
        }

        public String getRemaining() {
            return remaining;
        }

        public void setRemaining(String remaining) {
            this.remaining = remaining;
        }

        @SerializedName("total")
        private String total;
        @SerializedName("consumed")
        private String consumed;
        @SerializedName("remaining")
        private String remaining;
    }
    public class Mode{
        public String getImps() {
            return imps;
        }

        public void setImps(String imps) {
            this.imps = imps;
        }

        public String getNeft() {
            return neft;
        }

        public void setNeft(String neft) {
            this.neft = neft;
        }

        @SerializedName("imps")
        private String imps;
        @SerializedName("neft")
        private String neft;
    }
}
