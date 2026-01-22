package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.Utilisateur;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtilisateurRowMapper implements RowMapper<Utilisateur> {

    @Override
    public Utilisateur mapRow(ResultSet rs, int rowNum) throws SQLException {
        Utilisateur utilisateur = new Utilisateur();

        utilisateur.setId(rs.getInt("id"));
        utilisateur.setPasword(rs.getString("pasword"));
        utilisateur.setUsername(rs.getString("username"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setFristName(rs.getString("fristName"));
        utilisateur.setLastName(rs.getString("lastName"));
        utilisateur.setNumPhone(rs.getString("numPhone"));
        utilisateur.setVille(rs.getString("ville"));
        utilisateur.setStreet((rs.getString("street")));
        utilisateur.setCredit(rs.getInt("credit"));
        utilisateur.setPostalCode(rs.getInt("postalCode"));

        return utilisateur;


    }
}
