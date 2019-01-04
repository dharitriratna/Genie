package ratna.genie1.user.genie.Model;

public class RechargeOrderModel {


    public RechargeOrderModel(String orderNumber, String orderDateandtime, String orderStatus, String customerNumber, String operatorName, String rechargeType, String rechargeAmount) {
        this.orderNumber = orderNumber;
        this.orderDateandtime = orderDateandtime;
        this.orderStatus = orderStatus;
        this.customerNumber = customerNumber;
        this.operatorName = operatorName;
        this.rechargeType = rechargeType;
        this.rechargeAmount = rechargeAmount;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDateandtime() {
        return orderDateandtime;
    }

    public void setOrderDateandtime(String orderDateandtime) {
        this.orderDateandtime = orderDateandtime;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(String rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    String orderNumber;
    String orderDateandtime;
    String orderStatus;
    String customerNumber;
    String operatorName;
    String rechargeType;
    String rechargeAmount;


}
