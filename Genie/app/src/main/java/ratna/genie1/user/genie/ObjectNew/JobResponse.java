package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/9/2018.
 */

public class JobResponse {
    @SerializedName("status")
    @Expose
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SerializedName("data")
    private String data;
}
