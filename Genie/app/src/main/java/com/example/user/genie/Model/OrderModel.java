package com.example.user.genie.Model;

public class OrderModel {

    public OrderModel(String order_id, String name, String order_line, String order_landmark, String order_city, String order_state, String order_country, String order_pin, String order_amount, String order_subtotal) {
        this.order_id = order_id;
        this.name = name;
        this.order_line = order_line;
        this.order_landmark = order_landmark;
        this.order_city = order_city;
        this.order_state = order_state;
        this.order_country = order_country;
        this.order_pin = order_pin;
        this.order_amount = order_amount;
        this.order_subtotal = order_subtotal;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getName() {
        return name;
    }

    public String getOrder_line() {
        return order_line;
    }

    public String getOrder_landmark() {
        return order_landmark;
    }

    public String getOrder_city() {
        return order_city;
    }

    public String getOrder_state() {
        return order_state;
    }

    public String getOrder_country() {
        return order_country;
    }

    public String getOrder_pin() {
        return order_pin;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public String getOrder_subtotal() {
        return order_subtotal;
    }

    String order_id;
    String name;
    String order_line;
    String order_landmark;
    String order_city;
    String order_state;
    String order_country;
    String order_pin;
    String order_amount;
    String order_subtotal;

}
