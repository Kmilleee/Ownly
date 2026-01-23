package fr.eni.springboot.bo;

public class Withdrawal {

    private long id;
    private String street;
    private String Postcode;
    private String city;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostcode() {
        return Postcode;
    }

    public void setPostcode(String postcode) {
        Postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Withdrawal() {
    }

    public Withdrawal(String street, String postcode, String city) {
        this.street = street;
        Postcode = postcode;
        this.city = city;
    }
}
