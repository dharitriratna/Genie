package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/28/2018.
 */

public class DashboardResponse {


    @SerializedName("status")
    private boolean status;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

 /*   public ArrayList<DashboardList> getData() {
        return data;
    }

    public void setData(ArrayList<DashboardList> data) {
        this.data = data;
    }
*/
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    @SerializedName("data")
    private int data;
    @SerializedName("message")
    private String message;



}
