package fr.eni.springboot.bo;

import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDate;
import java.util.Objects;

public class User {
    private long user_id;
    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private String numPhone;
    private String street;
    private String postalCode;
    private String city;
    private String password;
    private long credit = 100;
    @Column("admin")
    private boolean admin = false;
    private boolean active = true ;
    private String avatar;
    private LocalDate lastDailyReward;


    public User(long user_id, String username, String lastName, String firstName, String email, String numPhone, String street, String postalCode, String city, String password, long credit, boolean admin, boolean active, String avatar, LocalDate lastDailyReward) {
        this.user_id = user_id;
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.numPhone = numPhone;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.password = password;
        this.credit = credit;
        this.admin = admin;
        this.active = active;
        this.avatar = avatar;
        this.lastDailyReward = lastDailyReward;
    }

    public User(String username, String lastName, String firstName, String email, String numPhone, String street, String postalCode, String city, String password, long credit, boolean admin, boolean active, String avatar, LocalDate lastDailyReward) {
        this.username = username;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.numPhone = numPhone;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.password = password;
        this.credit = credit;
        this.admin = admin;
        this.active = active;
        this.avatar = avatar;
        this.lastDailyReward = lastDailyReward;
    }

    public User() {
    }

    public LocalDate getLastDailyReward() {
        return lastDailyReward;
    }

    public void setLastDailyReward(LocalDate lastDailyReward) {
        this.lastDailyReward = lastDailyReward;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", numPhone='").append(numPhone).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", postalCode=").append(postalCode);
        sb.append(", city='").append(city).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", admin=").append(admin);
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return user_id == that.user_id && postalCode == that.postalCode && credit == that.credit && admin == that.admin && Objects.equals(username, that.username) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(email, that.email) && Objects.equals(numPhone, that.numPhone) && Objects.equals(street, that.street) && Objects.equals(city, that.city) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, username, lastName, firstName, email, numPhone, street, postalCode, city, password, credit, admin);
    }
}
