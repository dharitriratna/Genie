package ratna.genie1.user.genie.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RatnaDev008 on 11/19/2018.
 */

public class InsuranceDetails {
    @SerializedName("Status")
    private String Status;
    @SerializedName("Errorcode")
    private int Errorcode;
    @SerializedName("Message")
    private String Message;
    @SerializedName("Merchant_Name")
    private String Merchant_Name;
    @SerializedName("Policy_Number")
    private String Policy_Number;
    @SerializedName("Customer_Name")
    private String Customer_Name;
    @SerializedName("DOB")
    private String DOB;
    @SerializedName("Email")
    private String Email;
    @SerializedName("Mobile")
    private String Mobile;
    @SerializedName("Address")
    private String Address;
    @SerializedName("Due_Form")
    private String Due_Form;
    @SerializedName("Due_To")
    private String Due_To;
    @SerializedName("Number_of_Installment")
    private String Number_of_Installment;
    @SerializedName("Total_Premium")
    private String Total_Premium;
    @SerializedName("Due_Amount")
    private String Due_Amount;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getErrorcode() {
        return Errorcode;
    }

    public void setErrorcode(int errorcode) {
        Errorcode = errorcode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getMerchant_Name() {
        return Merchant_Name;
    }

    public void setMerchant_Name(String merchant_Name) {
        Merchant_Name = merchant_Name;
    }

    public String getPolicy_Number() {
        return Policy_Number;
    }

    public void setPolicy_Number(String policy_Number) {
        Policy_Number = policy_Number;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getDue_Form() {
        return Due_Form;
    }

    public void setDue_Form(String due_Form) {
        Due_Form = due_Form;
    }

    public String getDue_To() {
        return Due_To;
    }

    public void setDue_To(String due_To) {
        Due_To = due_To;
    }

    public String getNumber_of_Installment() {
        return Number_of_Installment;
    }

    public void setNumber_of_Installment(String number_of_Installment) {
        Number_of_Installment = number_of_Installment;
    }

    public String getTotal_Premium() {
        return Total_Premium;
    }

    public void setTotal_Premium(String total_Premium) {
        Total_Premium = total_Premium;
    }

    public String getDue_Amount() {
        return Due_Amount;
    }

    public void setDue_Amount(String due_Amount) {
        Due_Amount = due_Amount;
    }

    public String getRequestId() {
        return RequestId;
    }

    public void setRequestId(String requestId) {
        RequestId = requestId;
    }

    @SerializedName("RequestId")
    private String RequestId;

}
