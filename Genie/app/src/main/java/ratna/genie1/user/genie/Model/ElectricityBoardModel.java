package ratna.genie1.user.genie.Model;

public class ElectricityBoardModel {

    public ElectricityBoardModel(String electricity_board_id, String electricity_board_name, String electricity_board_code, String electricity_board_type) {
        this.electricity_board_id = electricity_board_id;
        this.electricity_board_name = electricity_board_name;
        this.electricity_board_code = electricity_board_code;
        this.electricity_board_type = electricity_board_type;
    }

    public String getElectricity_board_id() {
        return electricity_board_id;
    }

    public String getElectricity_board_name() {
        return electricity_board_name;
    }

    public String getElectricity_board_code() {
        return electricity_board_code;
    }

    public String getElectricity_board_type() {
        return electricity_board_type;
    }

    private String electricity_board_id;
    private String electricity_board_name;
    private String electricity_board_code;
    private String electricity_board_type;

}
