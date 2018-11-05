package com.example.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by RatnaDev008 on 11/5/2018.
 */

public class GenPNRResponse {
    @SerializedName("doj")
    private String doj;
    @SerializedName("chart_prepared")
    private boolean chart_prepared;
    @SerializedName("total_passengers")
    private int total_passengers;
    @SerializedName("debit")
    private int debit;
    @SerializedName("pnr")
    private String pnr;
    @SerializedName("passengers")
    private ArrayList<passengersList> passengers;
    @SerializedName("boarding_point")
    private boardingpoint boarding_point;
    @SerializedName("from_station")
    private fromstation from_station;
    @SerializedName("journey_class")
    private journeyclass journey_class;
    @SerializedName("train")
    private Train train;
    @SerializedName("to_station")
    private Tostation to_station;

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public boolean isChart_prepared() {
        return chart_prepared;
    }

    public void setChart_prepared(boolean chart_prepared) {
        this.chart_prepared = chart_prepared;
    }

    public int getTotal_passengers() {
        return total_passengers;
    }

    public void setTotal_passengers(int total_passengers) {
        this.total_passengers = total_passengers;
    }

    public int getDebit() {
        return debit;
    }

    public void setDebit(int debit) {
        this.debit = debit;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public ArrayList<passengersList> getPassengers() {
        return passengers;
    }

    public void setPassengers(ArrayList<passengersList> passengers) {
        this.passengers = passengers;
    }

    public boardingpoint getBoarding_point() {
        return boarding_point;
    }

    public void setBoarding_point(boardingpoint boarding_point) {
        this.boarding_point = boarding_point;
    }

    public fromstation getFrom_station() {
        return from_station;
    }

    public void setFrom_station(fromstation from_station) {
        this.from_station = from_station;
    }

    public journeyclass getJourney_class() {
        return journey_class;
    }

    public void setJourney_class(journeyclass journey_class) {
        this.journey_class = journey_class;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Tostation getTo_station() {
        return to_station;
    }

    public void setTo_station(Tostation to_station) {
        this.to_station = to_station;
    }

    public reservationupto getReservation_upto() {
        return reservation_upto;
    }

    public void setReservation_upto(reservationupto reservation_upto) {
        this.reservation_upto = reservation_upto;
    }

    public int getResponse_code() {
        return response_code;
    }

    public void setResponse_code(int response_code) {
        this.response_code = response_code;
    }

    @SerializedName("reservation_upto")
    private reservationupto reservation_upto;
    @SerializedName("response_code")
    private int response_code;
    public class passengersList{
        @SerializedName("no")
        private int no;
        @SerializedName("current_status")
        private String current_status;

        public int getNo() {
            return no;
        }

        public void setNo(int no) {
            this.no = no;
        }

        public String getCurrent_status() {
            return current_status;
        }

        public void setCurrent_status(String current_status) {
            this.current_status = current_status;
        }

        public String getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(String booking_status) {
            this.booking_status = booking_status;
        }

        @SerializedName("booking_status")
        private String booking_status;

    }

    public class boardingpoint{
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("lat")
        private float lat;
        @SerializedName("lng")
        private float lng;

    }
    public class fromstation{

        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("lat")
        private float lat;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        @SerializedName("lng")
        private float lng;
    }
    public class journeyclass{
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
    }
    public class Train{
        @SerializedName("name")
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public ArrayList<Days> getDays() {
            return days;
        }

        public void setDays(ArrayList<Days> days) {
            this.days = days;
        }

        public ArrayList<Classes> getClasses() {
            return classes;
        }

        public void setClasses(ArrayList<Classes> classes) {
            this.classes = classes;
        }

        @SerializedName("number")
        private int number;
        @SerializedName("days")
        private ArrayList<Days> days;
        @SerializedName("classes")
        private ArrayList<Classes> classes;
    }
    public class Tostation{
        @SerializedName("code")
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        @SerializedName("name")
        private String name;
        @SerializedName("lat")
        private float lat;
        @SerializedName("lng")
        private float lng;
    }
    public class  reservationupto{
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getLat() {
            return lat;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }

        public float getLng() {
            return lng;
        }

        public void setLng(float lng) {
            this.lng = lng;
        }

        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("lat")
        private float lat;
        @SerializedName("lng")
        private float lng;
    }
    public class Days{
        @SerializedName("code")
        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getRuns() {
            return runs;
        }

        public void setRuns(String runs) {
            this.runs = runs;
        }

        @SerializedName("runs")
        private String runs;
    }
    public class Classes{
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvailable() {
            return available;
        }

        public void setAvailable(String available) {
            this.available = available;
        }

        @SerializedName("code")
        private String code;
        @SerializedName("name")
        private String name;
        @SerializedName("available")
        private String available;
    }
}
