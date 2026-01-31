package fr.eni.springboot.repository;

import fr.eni.springboot.bo.User;
import fr.eni.springboot.repository.exception.TestException;
import fr.eni.springboot.repository.rowMapper.UtilisateurRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    @Override
    public void createUser(User user) {

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO USERS (username, lastName, firstName, email, numPhone, street, postalCode, city,password,credit,admin, active) values(:username,:lastName,:firstName,:email,:numPhone,:street,:postalCode,:city,:password,:credit,:admin,:active)";

        if("jermie".equalsIgnoreCase(user.getFirstName())){
            throw new TestException("impossible que le nom soit jeremie");
        }


        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(user);


        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        user.setUser_id(Objects.requireNonNull(keyHolder.getKey()).longValue());


    }

    @Override
    public List<User> readUser() {
        String sql = "SELECT user_id, username, lastname, firstname, email, numPhone, street, postalCode, city,password,credit,admin from USERS";

       return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
    public User readUserById(long user_id) {
        String sql = "SELECT user_id, username, lastname, firstname, email, numPhone, street, postalCode, city,password,credit,admin from USERS where user_id=:user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id",user_id);



        return namedParameterJdbcTemplate.queryForObject(sql,map, new UtilisateurRowMapper());
    }

    @Override
    public void updateUser(User user){

        String sql =" update users set username=:username, lastName=:LastName, firstName=:firstName, email=:email, numPhone=:numPhone, street=:street, postalCode=:postalCode, city=:city,password=:password where user_id=:user_id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(user);

        namedParameterJdbcTemplate.update(sql, map);


    }

    @Override
    @Transactional
    public void deleteUser(long user_id){
        String sql = "delete from USERS where user_id=:user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id", user_id);

        String sqlDeleteRoles = "DELETE FROM ROLES WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(sqlDeleteRoles, map);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public User readUserByUsername(String username) {
        String sql = "SELECT * FROM USERS where username=:username";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("username", username);
        return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User findByEmail(String email) {

            String sql = "SELECT * FROM USERS WHERE email = :email";

            MapSqlParameterSource map = new MapSqlParameterSource();
            map.addValue("email", email);

            try {
                return namedParameterJdbcTemplate.queryForObject(sql,map, new BeanPropertyRowMapper<>(User.class));
            } catch (EmptyResultDataAccessException e) {
                return null;
            }

    }
    @Override
    public void updatePassword(String email, String encodedPassword) {
        String sql = "UPDATE USERS SET password = :password WHERE email = :email";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("password", encodedPassword);
        map.addValue("email", email);


        namedParameterJdbcTemplate.update(sql,map);
    }
}
