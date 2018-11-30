package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class fares {

    @SerializedName("SeatTypeId")
    private int SeatTypeId;

    @SerializedName("SeatType")
    private String SeatType;

    @SerializedName("Fare")
    private String Fare;

    @SerializedName("ServiceTax")
    private String ServiceTax;

    @SerializedName("ConvenienceFee")
    private String ConvenienceFee;

    @SerializedName("DepartureTime")
    private String DepartureTime;

    @SerializedName("ArrivalTime")
    private String ArrivalTime;

    @SerializedName("AvailableSeatCount")
    private String AvailableSeatCount;

    public String getDepartureTime() {
        return DepartureTime;
    }

    public void setDepartureTime(String departureTime) {
        DepartureTime = departureTime;
    }

    public String getArrivalTime() {
        return ArrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        ArrivalTime = arrivalTime;
    }

    public String getAvailableSeatCount() {
        return AvailableSeatCount;
    }

    public void setAvailableSeatCount(String availableSeatCount) {
        AvailableSeatCount = availableSeatCount;
    }

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
}
