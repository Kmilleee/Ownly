package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Auction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class AuctionRepositorySql implements AuctionRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuctionRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //CRUD

    public void createAuction(Auction auction) {
        //GeneratedKeyHolder keyHolder = new GeneratedKeyHolder(); auction n'a pas d'id ??

        String sql = "INSERT INTO auction (auctionDate,auctionAmount, user_id, article_id) VALUES (:auctionDate,:auctionAmount,:user_id,:article_id)";

        BeanPropertySqlParameterSource map  = new BeanPropertySqlParameterSource(auction);

        namedParameterJdbcTemplate.update(sql, map);
    }

    public void readAuction(Auction auction) {
        String sql = "SELECT * FROM auction WHERE auctionId = :auctionId";

        MapSqlParameterSource map = new MapSqlParameterSource();
        //map.addValue("");
    }


}
