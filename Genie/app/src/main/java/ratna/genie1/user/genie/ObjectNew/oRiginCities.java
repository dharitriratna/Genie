package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class oRiginCities{
    @SerializedName("OriginId")
    private int OriginId;

    @SerializedName("OriginName")
    private String OriginName;

    public int getOriginId() {
        return OriginId;
    }

    public void setOriginId(int originId) {
        OriginId = originId;
    }

    public String getOriginName() {
        return OriginName;
    }

    public void setOriginName(String originName) {
        OriginName = originName;
    }
}