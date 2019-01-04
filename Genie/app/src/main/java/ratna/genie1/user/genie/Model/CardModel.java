package ratna.genie1.user.genie.Model;

/**
 * Created by user on 24-Feb-18.
 */

public class CardModel {

    public CardModel(String bank_id, String bank_name, String bank_url) {
        this.bank_id = bank_id;
        this.bank_name = bank_name;
        this.bank_url = bank_url;
    }

    public String getBank_id() {
        return bank_id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public String getBank_url() {
        return bank_url;
    }

    private String bank_id;
    private String bank_name;
    private String bank_url;







}
