package ratna.genie1.user.genie.ObjectNew;

import ratna.genie1.user.genie.Model.InsuranceDetails;
import com.google.gson.annotations.SerializedName;

import ratna.genie1.user.genie.Model.InsuranceDetails;

/**
 * Created by RatnaDev008 on 11/19/2018.
 */

public class InsuranceDetailResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public InsuranceDetails getData() {
        return data;
    }

    public void setData(InsuranceDetails data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private InsuranceDetails data;
}
