package ratna.genie1.user.genie.Model;

public class InsurerModel {

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    private String operatorName;
    private String operatorCode;

    public InsurerModel(String operatorName, String operatorCode) {
      //  this.electricity_board_id = electricity_board_id;
        this.operatorName = operatorName;
        this.operatorCode = operatorCode;
     //   this.electricity_board_type = electricity_board_type;
    }

 /*   public String getElectricity_board_id() {
        return electricity_board_id;
    }*/



  //  private String electricity_board_id;

  //  private String electricity_board_type;

}
