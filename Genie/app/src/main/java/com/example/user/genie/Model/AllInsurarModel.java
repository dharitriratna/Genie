package com.example.user.genie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/19/2018.
 */

public class AllInsurarModel {
    @SerializedName("id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SerializedName("name")
    private String name;
}
