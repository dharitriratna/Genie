package ratna.genie1.user.genie.ObjectNew;

import ratna.genie1.user.genie.Model.MobileOperatorsModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/15/2018.
 */

public class MobileOperatorResponse {

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<MobileOperatorData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<MobileOperatorData> getData() {
        return data;
    }

    public void setData(ArrayList<MobileOperatorData> data) {
        this.data = data;
    }
}
