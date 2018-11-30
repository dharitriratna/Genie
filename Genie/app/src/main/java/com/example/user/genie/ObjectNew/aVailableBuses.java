package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

public class aVailableBuses {
    @SerializedName("ScheduleId")
    private int ScheduleId;

    @SerializedName("StationId")
    private String StationId;

    @SerializedName("BusType")
    private String BusType;

    @SerializedName("BusName")
    private String BusName;

    @SerializedName("TransportId")
    private String TransportId;

    @SerializedName("TransportName")
    private String TransportName;

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
}
