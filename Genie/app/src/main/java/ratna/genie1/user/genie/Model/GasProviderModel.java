package ratna.genie1.user.genie.Model;

public class GasProviderModel {


    public GasProviderModel(String gas_board_id, String gas_board_name, String gas_board_code, String gas_board_type) {
        this.gas_board_id = gas_board_id;
        this.gas_board_name = gas_board_name;
        this.gas_board_code = gas_board_code;
        this.gas_board_type = gas_board_type;
    }

    public String getGas_board_id() {
        return gas_board_id;
    }

    public String getGas_board_name() {
        return gas_board_name;
    }

    public String getGas_board_code() {
        return gas_board_code;
    }

    public String getGas_board_type() {
        return gas_board_type;
    }

    private String gas_board_id;
    private String gas_board_name;
    private String gas_board_code;
    private String gas_board_type;

}
