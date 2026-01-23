package fr.eni.springboot.repository;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.rowMapper.UtilisateurRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class UserRepositorySql implements UserRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //CRUD de l'utilisateur :

    @Override
    public void createUser(User user) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO USERS (username, lastName, firstName, email, numPhone, street, postalCode, city,password,credit,admin) values(:username,:lastName,:firstName,:email,:numPhone,:street,:postalCode,:city,:pasword,:credit,:admin)";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(user);

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        user.setUser_id(Objects.requireNonNull(keyHolder.getKey()).longValue());

    }

    @Override
    public List<User> readUser() {
        String sql = "SELECT username, lastname, firstname, email, numPhone, street, postalCode, city,password,credit,admin from USERS";

       return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
    public void updateUser(){

        String sql =" update user set ";

    }

    @Override
    public void deleteUser(long user_id){
        String sql = "delete from USERS where user_id=:user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("id", user_id);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
