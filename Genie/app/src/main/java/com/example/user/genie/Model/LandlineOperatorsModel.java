package com.example.user.genie.Model;

public class LandlineOperatorsModel {

    public LandlineOperatorsModel(String operator_id, String operator_name, String operator_code, String service_type) {
        this.operator_id = operator_id;
        this.operator_name = operator_name;
        this.operator_code = operator_code;
        this.service_type = service_type;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public String getService_type() {
        return service_type;
    }

    private String operator_id;
    private String operator_name;
    private String operator_code;
    private String service_type;

}
