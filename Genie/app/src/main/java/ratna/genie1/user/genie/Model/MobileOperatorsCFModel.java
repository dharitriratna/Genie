package ratna.genie1.user.genie.Model;

public class MobileOperatorsCFModel {

    public MobileOperatorsCFModel(String operator_name, String operator_code) {

        this.operator_name = operator_name;
        this.operator_code = operator_code;

    }


    public String getOperator_name() {
        return operator_name;
    }

    public String getOperator_code() {
        return operator_code;
    }



    private String operator_name;
    private String operator_code;


}
