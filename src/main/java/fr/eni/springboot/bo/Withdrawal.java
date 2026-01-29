package fr.eni.springboot.bo;

public class Withdrawal {

    private long withdrawal_id;
    private String street;
    private String postalCode;
    private String city;
    private long article_id;

    public long getArticle_id() {
        return article_id;
    }

    public void setArticle_id(long article_id) {
        this.article_id = article_id;
    }

    public long getWithdrawal_id() {
        return withdrawal_id;
    }

    public void setWithdrawal_id(long withdrawal_id) {
        this.withdrawal_id = withdrawal_id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Withdrawal() {
    }

    public Withdrawal(String street, String postalCode, String city) {
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }
}
