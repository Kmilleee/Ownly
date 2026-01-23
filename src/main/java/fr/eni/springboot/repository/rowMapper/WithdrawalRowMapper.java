package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.Withdrawal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WithdrawalRowMapper implements RowMapper<Withdrawal> {

    @Override
    public Withdrawal mapRow(ResultSet rs, int rowNum) throws SQLException {
        Withdrawal withdrawal = new Withdrawal();

        withdrawal.setWithdrawal_id(rs.getLong("withdrawal_id"));
        withdrawal.setStreet(rs.getString("street"));
        withdrawal.setCity(rs.getString("city"));
        withdrawal.setPostalCode(rs.getString("postalCode"));

        return withdrawal;
    }
}
