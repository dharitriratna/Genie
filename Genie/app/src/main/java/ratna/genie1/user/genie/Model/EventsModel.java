package ratna.genie1.user.genie.Model;

public class EventsModel {

    public EventsModel(String event_id, String event_name, String event_price, String event_image) {
        this.event_id = event_id;
        this.event_name = event_name;
        this.event_price = event_price;
        this.event_image = event_image;
    }

    public String getEvent_id() {
        return event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public String getEvent_price() {
        return event_price;
    }

    public String getEvent_image() {
        return event_image;
    }

    String event_id;
    String event_name;
    String event_price;
    String event_image;
}
