package ratna.genie1.user.genie.Model;

public class AllAddressModel {

    public AllAddressModel(String add_id, String name, String address_line, String address_landmark, String add_city, String add_state, String add_country, String add_pin) {
        this.add_id = add_id;
        this.name = name;
        this.address_line = address_line;
        this.address_landmark = address_landmark;
        this.add_city = add_city;
        this.add_state = add_state;
        this.add_country = add_country;
        this.add_pin = add_pin;
    }

    public String getAdd_id() {
        return add_id;
    }

    public String getName() {
        return name;
    }

    public String getAddress_line() {
        return address_line;
    }

    public String getAddress_landmark() {
        return address_landmark;
    }

    public String getAdd_city() {
        return add_city;
    }

    public String getAdd_state() {
        return add_state;
    }

    public String getAdd_country() {
        return add_country;
    }

    public String getAdd_pin() {
        return add_pin;
    }

    String add_id;
    String name;
    String address_line;
    String address_landmark;
    String add_city;
    String add_state;
    String add_country;
    String add_pin;




}
