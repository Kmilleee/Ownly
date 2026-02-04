package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.Auction;
import fr.eni.springboot.bo.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuctionRowMapper implements RowMapper<Auction> {
    @Nullable
    @Override
    public Auction mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {

        Auction auction = new Auction();

        auction.setAuction_id(rs.getLong("auction_id"));
        auction.setAuctionDate(rs.getTimestamp("auctionDate").toLocalDateTime());
        auction.setAuctionAmount(rs.getLong("auctionAmount"));

        User bidder = new User();
        bidder.setUser_id(rs.getLong("user_id"));
        bidder.setUsername(rs.getString("username"));

        auction.setBidder(bidder);

        return auction;
    }
}
