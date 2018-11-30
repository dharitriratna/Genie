package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class aVailableBuses {
    @SerializedName("ScheduleId")
    private int ScheduleId;

    @SerializedName("StationId")
    private String StationId;

    public int getScheduleId() {
        return ScheduleId;
    }

    public void setScheduleId(int scheduleId) {
        ScheduleId = scheduleId;
    }

    public String getStationId() {
        return StationId;
    }

    public void setStationId(String stationId) {
        StationId = stationId;
    }

    public String getBusType() {
        return BusType;
    }

    public void setBusType(String busType) {
        BusType = busType;
    }

    public String getBusName() {
        return BusName;
    }

    public void setBusName(String busName) {
        BusName = busName;
    }

    public String getTransportId() {
        return TransportId;
    }

    public void setTransportId(String transportId) {
        TransportId = transportId;
    }

    public String getTransportName() {
        return TransportName;
    }

    public void setTransportName(String transportName) {
        TransportName = transportName;
    }

    public ArrayList<fares> getFares() {
        return Fares;
    }

    public void setFares(ArrayList<fares> fares) {
        Fares = fares;
    }

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

    public ArrayList<boardingPoints> getBoardingPoints() {
        return BoardingPoints;
    }

    public void setBoardingPoints(ArrayList<boardingPoints> boardingPoints) {
        BoardingPoints = boardingPoints;
    }

    public ArrayList<droppingPoints> getDroppingPoints() {
        return DroppingPoints;
    }

    public void setDroppingPoints(ArrayList<droppingPoints> droppingPoints) {
        DroppingPoints = droppingPoints;
    }

    public String getCommission() {
        return Commission;
    }

    public void setCommission(String commission) {
        Commission = commission;
    }

    public String getTripId() {
        return TripId;
    }

    public void setTripId(String tripId) {
        TripId = tripId;
    }

    @SerializedName("BusType")
    private String BusType;

    @SerializedName("BusName")
    private String BusName;

    @SerializedName("TransportId")
    private String TransportId;

    @SerializedName("TransportName")
    private String TransportName;


    @SerializedName("Fares")
    private ArrayList<fares> Fares;

    @SerializedName("DepartureTime")
    private String  DepartureTime;

    @SerializedName("ArrivalTime")
    private String  ArrivalTime;

    @SerializedName("AvailableSeatCount")
    private String  AvailableSeatCount;

    @SerializedName("BoardingPoints")
    private ArrayList<boardingPoints>  BoardingPoints;

    @SerializedName("DroppingPoints")
    private ArrayList<droppingPoints>  DroppingPoints;

    @SerializedName("Commission")
    private String  Commission;

    @SerializedName("TripId")
    private String  TripId;




}
