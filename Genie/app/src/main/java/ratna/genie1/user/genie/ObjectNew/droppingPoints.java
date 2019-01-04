package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class droppingPoints {

    @SerializedName("DroppingId")
    private int DroppingId;

    @SerializedName("Place")
    private String Place;

    @SerializedName("Time")
    private String Time;

    @SerializedName("Address")
    private String Address;

    @SerializedName("LandMark")
    private String LandMark;

    @SerializedName("ContactNumber")
    private String ContactNumber;

    public int getDroppingId() {
        return DroppingId;
    }

    public void setDroppingId(int droppingId) {
        DroppingId = droppingId;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String landMark) {
        LandMark = landMark;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
