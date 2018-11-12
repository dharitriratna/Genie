package com.example.user.genie.Model;

public class GiftsModel {

    String gift_id;

    public GiftsModel(String gift_id, String gift_name, String gift_price, String gift_image) {
        this.gift_id = gift_id;
        this.gift_name = gift_name;
        this.gift_price = gift_price;
        this.gift_image = gift_image;
    }

    public String getGift_id() {
        return gift_id;
    }

    public String getGift_name() {
        return gift_name;
    }

    public String getGift_price() {
        return gift_price;
    }

    public String getGift_image() {
        return gift_image;
    }

    String gift_name;
    String gift_price;
    String gift_image;
}
