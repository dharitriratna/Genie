package ratna.genie1.user.genie.Model;

/**
 * Created by RatnaDev008 on 11/15/2018.
 */

public class DataOperatorListModel {

    private String  id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getOperator_code() {
        return operator_code;
    }

    public void setOperator_code(String operator_code) {
        this.operator_code = operator_code;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getBilling_model() {
        return billing_model;
    }

    public void setBilling_model(String billing_model) {
        this.billing_model = billing_model;
    }

    public String getAmount_range() {
        return amount_range;
    }

    public void setAmount_range(String amount_range) {
        this.amount_range = amount_range;
    }

    public String getPartial_pay() {
        return partial_pay;
    }

    public void setPartial_pay(String partial_pay) {
        this.partial_pay = partial_pay;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getHsn_sac() {
        return hsn_sac;
    }

    public void setHsn_sac(String hsn_sac) {
        this.hsn_sac = hsn_sac;
    }

    public String getAccount_display() {
        return account_display;
    }

    public void setAccount_display(String account_display) {
        this.account_display = account_display;
    }

    public String getOptional_parameter() {
        return optional_parameter;
    }

    public void setOptional_parameter(String optional_parameter) {
        this.optional_parameter = optional_parameter;
    }

    private String  operator_name;
    private String  operator_code;
    private String  service_type;
    private String  billing_model;
    private String  amount_range;
    private String  partial_pay;
    private String  tds;
    private String  hsn_sac;
    private String  account_display;
    private String  optional_parameter;
}
