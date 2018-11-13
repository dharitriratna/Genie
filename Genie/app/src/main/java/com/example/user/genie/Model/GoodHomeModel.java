package com.example.user.genie.Model;

/**
 * Created by RatnaDev008 on 11/12/2018.
 */

public class GoodHomeModel {
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    private String item;
    private String qty;

    public boolean isFlagItemqty() {
        return flagItemqty;
    }

    public void setFlagItemqty(boolean flagItemqty) {
        this.flagItemqty = flagItemqty;
    }

    public boolean flagItemqty;
}
