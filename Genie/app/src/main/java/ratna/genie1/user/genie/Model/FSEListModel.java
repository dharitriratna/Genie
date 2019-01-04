package ratna.genie1.user.genie.Model;

public class FSEListModel  {
    public FSEListModel(String id, String username, String phone, String email, String first_name, String address_proof, String line1) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.email = email;
        this.first_name = first_name;
        this.address_proof = address_proof;
        this.line1 = line1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getAddress_proof() {
        return address_proof;
    }

    public void setAddress_proof(String address_proof) {
        this.address_proof = address_proof;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    String id;
    String username;
    String phone;
    String email;
    String first_name;
    String address_proof;
    String line1;

}
