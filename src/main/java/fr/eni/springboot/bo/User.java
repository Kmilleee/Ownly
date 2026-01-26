package fr.eni.springboot.bo;

import java.util.Objects;

public class User {
    private long user_id;
    private String username;
    private String lastName;
    private String fristName;
    private String email;
    private String numPhone;
    private String street;
    private String postalCode;
    private String city;
    private String pasword;
    private long credit;
    private boolean admin;

    public User(long user_id, String username, String lastName, String fristName, String email, String numPhone, String street, String postalCode, String city, String pasword, long credit, boolean admin) {
        this.user_id = user_id;
        this.username = username;
        this.lastName = lastName;
        this.fristName = fristName;
        this.email = email;
        this.numPhone = numPhone;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.pasword = pasword;
        this.credit = credit;
        this.admin = admin;
    }

    public User(String username, String lastName, String fristName, String email, String numPhone, String street, String postalCode, String city, String pasword, long credit, boolean admin) {
        this.username = username;
        this.lastName = lastName;
        this.fristName = fristName;
        this.email = email;
        this.numPhone = numPhone;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.pasword = pasword;
        this.credit = credit;
        this.admin = admin;
    }

    public User() {
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFristName() {
        return fristName;
    }

    public void setFristName(String fristName) {
        this.fristName = fristName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumPhone() {
        return numPhone;
    }

    public void setNumPhone(String numPhone) {
        this.numPhone = numPhone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPasword() {
        return pasword;
    }

    public void setPasword(String pasword) {
        this.pasword = pasword;
    }

    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Utilisateur{");
        sb.append("id=").append(user_id);
        sb.append(", username='").append(username).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", fristName='").append(fristName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", numPhone='").append(numPhone).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", postalCode=").append(postalCode);
        sb.append(", city='").append(city).append('\'');
        sb.append(", pasword='").append(pasword).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", admin=").append(admin);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return user_id == that.user_id && postalCode == that.postalCode && credit == that.credit && admin == that.admin && Objects.equals(username, that.username) && Objects.equals(lastName, that.lastName) && Objects.equals(fristName, that.fristName) && Objects.equals(email, that.email) && Objects.equals(numPhone, that.numPhone) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(pasword, that.pasword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, lastName, fristName, email, numPhone, street, postalCode, city, pasword, credit, admin);
    }
}
