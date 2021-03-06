package ratna.genie1.user.genie.ObjectNew;

import ratna.genie1.user.genie.Model.AllInsurarModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import ratna.genie1.user.genie.Model.AllInsurarModel;

/**
 * Created by RatnaDev008 on 11/19/2018.
 */

public class GetInsuranseResponse {
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<AllInsurarModel> getData() {
        return data;
    }

    public void setData(ArrayList<AllInsurarModel> data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<AllInsurarModel> data;

}
