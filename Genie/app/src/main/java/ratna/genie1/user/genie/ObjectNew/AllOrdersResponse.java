package ratna.genie1.user.genie.ObjectNew;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AllOrdersResponse {

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<allOrdersArrayListResponse> getData() {
        return data;
    }

    public void setData(ArrayList<allOrdersArrayListResponse> data) {
        this.data = data;
    }

    @SerializedName("status")
    private boolean status;
    @SerializedName("data")
    private ArrayList<allOrdersArrayListResponse> data;


    public class allOrdersArrayListResponse {
        @SerializedName("first_name")
        String first_name;

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getTransactionDate() {
            return TransactionDate;
        }

        public void setTransactionDate(String transactionDate) {
            TransactionDate = transactionDate;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getErrorMessage() {
            return ErrorMessage;
        }

        public void setErrorMessage(String errorMessage) {
            ErrorMessage = errorMessage;
        }

        public String getOperatorRef() {
            return OperatorRef;
        }

        public void setOperatorRef(String operatorRef) {
            OperatorRef = operatorRef;
        }

        public String getRecharge_no() {
            return recharge_no;
        }

        public void setRecharge_no(String recharge_no) {
            this.recharge_no = recharge_no;
        }

        public String getItem_name() {
            return item_name;
        }

        public void setItem_name(String item_name) {
            this.item_name = item_name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public String getTax_amount() {
            return tax_amount;
        }

        public void setTax_amount(String tax_amount) {
            this.tax_amount = tax_amount;
        }

        public String getService_order_id() {
            return service_order_id;
        }

        public void setService_order_id(String service_order_id) {
            this.service_order_id = service_order_id;
        }

        @SerializedName("serviceName")
        String serviceName;
        @SerializedName("TransactionDate")
        String TransactionDate;
        @SerializedName("status")
        String status;
        @SerializedName("amount")
        String amount;
        @SerializedName("ErrorMessage")
        String ErrorMessage;
        @SerializedName("OperatorRef")
        String OperatorRef;
        @SerializedName("recharge_no")
        String recharge_no;
        @SerializedName("item_name")
        String item_name;
        @SerializedName("quantity")
        String quantity;
        @SerializedName("unit")
        String unit;
        @SerializedName("price")
        String price;
        @SerializedName("subtotal")
        String subtotal;
        @SerializedName("tax_amount")
        String tax_amount;
        @SerializedName("service_order_id")
        String service_order_id;

        public String getApiTransID() {
            return ApiTransID;
        }

        public void setApiTransID(String apiTransID) {
            ApiTransID = apiTransID;
        }

        @SerializedName("ApiTransID")
        String ApiTransID;
    }

}
