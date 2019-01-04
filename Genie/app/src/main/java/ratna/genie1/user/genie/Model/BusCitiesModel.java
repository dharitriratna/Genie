package ratna.genie1.user.genie.Model;

public class BusCitiesModel {

    public BusCitiesModel(String city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    String city_id;

    public String getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    String city_name;

}
