package ratna.genie1.user.genie.Model;

public class DTHOperatorsModel {

    public DTHOperatorsModel(String dth_operator_id, String dth_operator_name, String dth_operator_code, String dth_service_type) {
        this.dth_operator_id = dth_operator_id;
        this.dth_operator_name = dth_operator_name;
        this.dth_operator_code = dth_operator_code;
        this.dth_service_type = dth_service_type;
    }

    public String getDth_operator_id() {
        return dth_operator_id;
    }

    public String getDth_operator_name() {
        return dth_operator_name;
    }

    public String getDth_operator_code() {
        return dth_operator_code;
    }

    public String getDth_service_type() {
        return dth_service_type;
    }

    private String dth_operator_id;
    private String dth_operator_name;
    private String dth_operator_code;
    private String dth_service_type;

}
