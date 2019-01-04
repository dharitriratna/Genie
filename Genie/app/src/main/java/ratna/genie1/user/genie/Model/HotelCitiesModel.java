package ratna.genie1.user.genie.Model;

public class HotelCitiesModel {

    public HotelCitiesModel(String city_id, String city_name, String city_image) {
        this.city_id = city_id;
        this.city_name = city_name;
        this.city_image = city_image;
    }

    public String getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCity_image() {
        return city_image;
    }

    private String city_id;
    private String city_name;
    private String city_image;
}
