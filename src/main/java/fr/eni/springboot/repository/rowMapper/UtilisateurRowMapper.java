package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getInt("id"));
        user.setPasword(rs.getString("pasword"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setFristName(rs.getString("fristName"));
        user.setLastName(rs.getString("lastName"));
        user.setNumPhone(rs.getString("numPhone"));
        user.setVille(rs.getString("ville"));
        user.setStreet((rs.getString("street")));
        user.setCredit(rs.getInt("credit"));
        user.setPostalCode(rs.getInt("postalCode"));

        return user;


    }
}
