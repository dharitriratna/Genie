package com.example.user.genie.Model;

public class WaterBoardModel {

    public WaterBoardModel(String water_board_id, String water_board_name, String water_board_code, String water_board_type) {
        this.water_board_id = water_board_id;
        this.water_board_name = water_board_name;
        this.water_board_code = water_board_code;
        this.water_board_type = water_board_type;
    }

    public String getWater_board_id() {
        return water_board_id;
    }

    public String getWater_board_name() {
        return water_board_name;
    }

    public String getWater_board_code() {
        return water_board_code;
    }

    public String getWater_board_type() {
        return water_board_type;
    }

    private String water_board_id;
    private String water_board_name;
    private String water_board_code;
    private String water_board_type;

}
