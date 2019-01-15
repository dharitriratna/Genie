package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/28/2018.
 */

public class RequestResponse {


    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<RequestAdminList> getData() {
        return data;
    }

    public void setData(ArrayList<RequestAdminList> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("data")
    private ArrayList<RequestAdminList> data;
    @SerializedName("message")
    private String message;



}
