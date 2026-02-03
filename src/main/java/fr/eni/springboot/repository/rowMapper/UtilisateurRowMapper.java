package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setUser_id(rs.getInt("user_id"));
        user.setPassword(rs.getString("password"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setNumPhone(rs.getString("numPhone"));
        user.setCity(rs.getString("city"));
        user.setStreet((rs.getString("street")));
        user.setCredit(rs.getInt("credit"));
        user.setPostalCode(rs.getString("postalCode"));
        user.setAdmin(rs.getBoolean("admin"));
        user.setActive(rs.getBoolean("active"));
        user.setAvatar(rs.getString("avatar"));

        return user;


    }
}
