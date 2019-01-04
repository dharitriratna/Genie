package ratna.genie1.user.genie.Model;

public class SelectPinModel {

    public SelectPinModel(String city_id, String code, String area_name) {
        this.city_id = city_id;
        this.code = code;
        this.area_name = area_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getCode() {
        return code;
    }

    public String getArea_name() {
        return area_name;
    }

    String city_id;
    String code;
    String area_name;
}
