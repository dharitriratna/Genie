package ratna.genie1.user.genie.Model;

public class MobileOperatorCircleModel {


    public MobileOperatorCircleModel(String operator_circle_id, String operator_circle_name, String operator_circle_code) {
        this.operator_circle_id = operator_circle_id;
        this.operator_circle_name = operator_circle_name;
        this.operator_circle_code = operator_circle_code;
    }

    public String getOperator_circle_id() {
        return operator_circle_id;
    }

    public String getOperator_circle_name() {
        return operator_circle_name;
    }

    public String getOperator_circle_code() {
        return operator_circle_code;
    }

    private String operator_circle_id;
    private String operator_circle_name;
    private String operator_circle_code;

}
