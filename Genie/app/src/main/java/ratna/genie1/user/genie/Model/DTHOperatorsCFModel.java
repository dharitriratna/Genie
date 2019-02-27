package ratna.genie1.user.genie.Model;

public class DTHOperatorsCFModel {

    public DTHOperatorsCFModel( String dth_operator_name, String dth_operator_code) {

        this.dth_operator_name = dth_operator_name;
        this.dth_operator_code = dth_operator_code;

    }


    public String getDth_operator_name() {
        return dth_operator_name;
    }

    public String getDth_operator_code() {
        return dth_operator_code;
    }



    private String dth_operator_name;
    private String dth_operator_code;


}
