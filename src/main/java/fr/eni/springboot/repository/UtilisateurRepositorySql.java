package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Utilisateur;
import fr.eni.springboot.repository.rowMapper.UtilisateurRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UtilisateurRepositorySql implements UtilisateurRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UtilisateurRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //CRUD de l'utilisateur :

    @Override
    public void createUtilisateur(Utilisateur utilisateur) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO utilisateur (username, lastname, firstname, email, numPhone, street, postalCode, ville,password,credit,admin) values(:username,:lastName,:firstName,:email,:numPhone,:street,:postalCode,:ville,:pasword,:credit,:admin)";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(utilisateur);

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        utilisateur.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

    }

    @Override
    public List<Utilisateur> readUtilisateur() {
        String sql = "SELECT username, lastname, firstname, email, numPhone, street, postalCode, ville,password,credit,admin from utilisateur";

       return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
    public void updateUtilisateur(){

        String sql =" update utilsateur set "

    }
}
