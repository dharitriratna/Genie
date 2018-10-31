package com.example.user.genie.Model;

public class ServicesModel {
    public ServicesModel(String service_id, String service_name, String service_fee, String service_img) {
        this.service_id = service_id;
        this.service_name = service_name;
        this.service_fee = service_fee;
        this.service_img = service_img;
    }

    public String getService_id() {
        return service_id;
    }

    public String getService_name() {
        return service_name;
    }

    public String getService_fee() {
        return service_fee;
    }

    public String getService_img() {
        return service_img;
    }

    private String service_id;
    private String service_name;
    private String service_fee;
    private String service_img;
}
