package com.example.user.genie.Model;

public class destinationInput{
    public destinationInput() {
    }

    public destinationInput(int originId) {
        OriginId = originId;
    }

    public int getOriginId() {
        return OriginId;
    }

    public void setOriginId(int originId) {
        OriginId = originId;
    }


    private int OriginId;
}