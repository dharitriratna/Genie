package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/20/2018.
 */

public class DatacardResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public DatacardRechargeResponse getData() {
        return data;
    }

    public void setData(DatacardRechargeResponse data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private DatacardRechargeResponse data;
}
