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


        String sqlRole = "INSERT INTO ROLES (user_id, role) VALUES (:userId, :roleName)";
        String roleName = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        MapSqlParameterSource map2 = new MapSqlParameterSource();
        map2.addValue("userId", user.getUser_id());
        map2.addValue("roleName", roleName);
        namedParameterJdbcTemplate.update(sqlRole, map2);


    }

    @Override
    public List<User> readUser() {
        String sql = "SELECT user_id, username, lastname, firstname, email, numPhone, street, postalCode, city,password,credit,admin, active from USERS";

       return jdbcTemplate.query(sql, new UtilisateurRowMapper());
    }

    @Override
    public User readUserById(long user_id) {
        String sql = "SELECT user_id, username, lastname, firstname, email, numPhone, street, postalCode, city,password,credit,admin, active, avatar from USERS where user_id=:user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("user_id",user_id);



        return namedParameterJdbcTemplate.queryForObject(sql,map, new UtilisateurRowMapper());
    }

    @Override
    public void updateUser(User user){

        String sql =" update users set username=:username, lastName=:LastName, firstName=:firstName, email=:email, numPhone=:numPhone, street=:street, postalCode=:postalCode, city=:city,password=:password, active=:active where user_id=:user_id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(user);

        namedParameterJdbcTemplate.update(sql, map);


        String sqlDeleteRoles = "DELETE FROM ROLES WHERE user_id = :user_id";
        namedParameterJdbcTemplate.update(sqlDeleteRoles, map);

        String sqlInsertRole = "INSERT INTO ROLES (user_id, role) VALUES (:userId, :roleName)";

        String roleName = user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";

        MapSqlParameterSource map2 = new MapSqlParameterSource();
        map2.addValue("userId", user.getUser_id());
        map2.addValue("roleName", roleName);

        namedParameterJdbcTemplate.update(sqlInsertRole, map2);
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
        // le try catch permet de mettre des valeurs null au lieu de crash dans le cas d'un utilisateur google
        try {
            return namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

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

    @Override
    public void updateAvatar(long userId, String imageName) {
        String sql = "UPDATE USERS SET avatar = :avatar WHERE user_id = :user_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("avatar", imageName);
        map.addValue("user_id", userId);

        namedParameterJdbcTemplate.update(sql,map);
    }

    @Override
    public boolean claimDailyReward(long userId) {
        String sql = "UPDATE USERS " +
                "SET credit = credit + 500, last_daily_reward = GETDATE() " +
                "WHERE user_id = :userId " +
                "AND (last_daily_reward < CAST(GETDATE() AS DATE) OR last_daily_reward IS NULL)";

        MapSqlParameterSource map = new MapSqlParameterSource("userId", userId);

        // update renvoie le nombre de lignes modifiées.
        // Si renvoie 1 : Succès (c'était un nouveau jour).
        // Si renvoie 0 : Échec (déjà pris aujourd'hui).
        int rowsAffected = namedParameterJdbcTemplate.update(sql, map);

        return rowsAffected > 0;
    }
}
