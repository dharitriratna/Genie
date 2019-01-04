package ratna.genie1.user.genie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/27/2018.
 */

public class MovieCityModel {
    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getProviderId() {
        return ProviderId;
    }

    public void setProviderId(int providerId) {
        ProviderId = providerId;
    }
    @SerializedName("City")
    private String City;
    @SerializedName("Status")
    private String Status;
    @SerializedName("ProviderId")
    private int ProviderId;

}
