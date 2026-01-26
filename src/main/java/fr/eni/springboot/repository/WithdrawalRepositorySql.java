package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Withdrawal;
import fr.eni.springboot.repository.rowMapper.WithdrawalRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class WithdrawalRepositorySql implements WithdrawalRepository{

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WithdrawalRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //CRUD
    @Override
    public void createWithdrawal(Withdrawal withdrawal){

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO Withdrawal (street, postalCode,city) values(:street, :postalCode, :city)";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(withdrawal);

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        withdrawal.setWithdrawal_id(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public List<Withdrawal> readWithdrawal(){

        String sql= "select withdrawal_id,street, postalCode, city from withdrawal";

        return jdbcTemplate.query(sql, new WithdrawalRowMapper());
    }

    @Override
    public void updateWithdrawal(Withdrawal withdrawal){

        String sql = "UPDATE withdrawal SET street, postalCode, city where withdrawal_id=:withdrawal_id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(withdrawal);

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void deleteWithdrawal(long withdrawal_id){

        String sql = "delete from withdrawal where withdrawal_id=:withdrawal_id ";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("withdrawal_id", withdrawal_id);

        namedParameterJdbcTemplate.update(sql, map);

    }



}
