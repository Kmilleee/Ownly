package fr.eni.springboot.repository;

import fr.eni.springboot.bo.Auction;
import fr.eni.springboot.repository.rowMapper.AuctionRowMapper;
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
public class AuctionRepositorySql implements AuctionRepository {

    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public AuctionRepositorySql(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    //CRUD

    @Transactional
    @Override
    public void createAuction(Auction auction) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        String sql = "INSERT INTO auction (auctionDate, auctionAmount, user_id, article_id) " +
                "VALUES (:date, :amount, :uId, :aId)";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("date", auction.getAuctionDate());
        map.addValue("amount", auction.getAuctionAmount());
        map.addValue("uId", auction.getBidder().getUser_id());
        map.addValue("aId", auction.getItem().getId());

        namedParameterJdbcTemplate.update(sql, map, keyHolder);

        auction.setAuction_id(Objects.requireNonNull(keyHolder.getKey()).longValue());
    }

    @Override
    public List<Auction> readAuction() {
        String sql = "SELECT a.auctionAmount, a.auctionDate,a.auction_id as auction_id, " +
                "u.username as username, u.user_id as user_id " +
                "FROM AUCTION a " +
                "left JOIN USERS u ON a.user_id = u.user_id " +
                "ORDER BY a.auctionAmount DESC";


        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Auction.class));
    }

    @Override
    public void updateAuction(Auction auction) {
        String sql = " UPDATE auction SET user_id =:user_id, article_id=:article_id,auctionDate=:auctionDate,auctionAmount=:auctionAmount where auction_id=:auction_id";

        BeanPropertySqlParameterSource map = new BeanPropertySqlParameterSource(auction);

        namedParameterJdbcTemplate.queryForObject(sql, map, new BeanPropertyRowMapper<>(Auction.class));

    }

    @Override
    public void deleteAuction(long auction_id) {
        String sql = "delete from auction where auction_id=:auction_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("auction_id", auction_id);

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public Auction findBestAuctionByItemId(long itemId) {
        String sql = "SELECT TOP 1 a.auctionAmount, a.auctionDate,a.auction_id, " +
                "u.username as username, u.user_id as user_id " +
                "FROM AUCTION a " +
                "left JOIN USERS u ON a.user_id = u.user_id " +
                " where a.article_id = :auction_id "+
                "ORDER BY a.auctionAmount DESC";
//        String sql = "SELECT TOP 1 a.auction_id, a.auctionDate, a.auctionAmount, a.user_id, a.article_id, u.username FROM auction a INNER JOIN USERS u ON a.user_id = u.user_id WHERE a.article_id = :itemId ORDER BY a.auctionAmount DESC";
        MapSqlParameterSource params = new MapSqlParameterSource("auction_id", itemId);

        try {
            return namedParameterJdbcTemplate.queryForObject(sql, params, new AuctionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Auction> readItemById(long auction_id) {
        String sql = "SELECT a.auctionAmount, a.auctionDate,a.auction_id, " +
                "u.username as username, u.user_id as user_id " +
                "FROM AUCTION a " +
                "left JOIN USERS u ON a.user_id = u.user_id " +
                " where a.article_id = :auction_id";

        MapSqlParameterSource map = new MapSqlParameterSource();
        map.addValue("auction_id", auction_id);

        return namedParameterJdbcTemplate.query(sql, map, new AuctionRowMapper());
    }


}
