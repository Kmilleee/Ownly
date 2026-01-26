package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Auction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class AuctionRepositorySql implements AuctionRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuctionRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //CRUD

    @Override
    public void createAuction(Auction auction) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO auction (auctionDate,auctionAmount, user_id, article_id) VALUES (:auctionDate,:auctionAmount,:user_id,:article_id)";

        BeanPropertySqlParameterSource map  = new BeanPropertySqlParameterSource(auction);

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        auction.setAuction_id(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public List<Auction> readAuction() {
        String sql = "SELECT user_id, article_id, auctionDate, auctionAmount FROM auction";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Auction.class));
    }

    @Override
    public void updateAuction(Auction auction){
        String sql =" UPDATE auction SET user_id =:user_id, article_id=:article_id,auctionDate=:auctionDate,auctionAmount=:auctionAmount where auction_id=:auction_id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(auction);

        namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(Auction.class));

    }

    @Override
    public void deleteAuction(long auction_id){
        String sql ="delete from auction where auction_id=:auction_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("auction_id", auction_id);

        namedParameterJdbcTemplate.update(sql, map);
    }


}
