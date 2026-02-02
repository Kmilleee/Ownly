package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemSoldRowMapper implements RowMapper<ItemSold> {

    @Override
    public ItemSold mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemSold item = new ItemSold();

        item.setId(rs.getLong("article_id"));
        item.setArticleName(rs.getString("articleName"));
        item.setDescription(rs.getString("description"));
        item.setStartingPrice(rs.getLong("startingPrice"));
        item.setImage(rs.getString("image"));
        item.setAuctionStartDate(rs.getDate("auctionStartDate").toLocalDate());
        item.setAuctionEndDate(rs.getDate("auctionEndDate").toLocalDate());

        return item;
    }
}