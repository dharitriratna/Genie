package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class fares {

    @SerializedName("SeatTypeId")
    private int SeatTypeId;

    public int getSeatTypeId() {
        return SeatTypeId;
    }

    public void setSeatTypeId(int seatTypeId) {
        SeatTypeId = seatTypeId;
    }

    public String getSeatType() {
        return SeatType;
    }

    public void setSeatType(String seatType) {
        SeatType = seatType;
    }

    public String getFare() {
        return Fare;
    }

    public void setFare(String fare) {
        Fare = fare;
    }

    public String getServiceTax() {
        return ServiceTax;
    }

    public void setServiceTax(String serviceTax) {
        ServiceTax = serviceTax;
    }

    public String getConvenienceFee() {
        return ConvenienceFee;
    }

    public void setConvenienceFee(String convenienceFee) {
        ConvenienceFee = convenienceFee;
    }

    @SerializedName("SeatType")
    private String SeatType;

    @SerializedName("Fare")
    private String Fare;

    @SerializedName("ServiceTax")
    private String ServiceTax;

    @SerializedName("ConvenienceFee")
    private String ConvenienceFee;





}
