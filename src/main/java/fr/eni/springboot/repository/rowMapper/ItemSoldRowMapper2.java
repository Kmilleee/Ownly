package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemSoldRowMapper2 implements RowMapper<ItemSold> {

    @Override
    public ItemSold mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemSold article = new ItemSold();
        article.setId(rs.getLong("article_id"));
        article.setArticleName(rs.getString("articleName"));
        article.setDescription(rs.getString("description"));
        article.setStartingPrice(rs.getLong("startingPrice"));
        article.setImage(rs.getString("image"));
        article.setPriceSale(rs.getLong("priceSale"));
        article.setAuctionEndDate(rs.getTimestamp("auctionEndDate").toLocalDateTime());
        article.setAuctionStartDate(rs.getTimestamp("auctionStartDate").toLocalDateTime());

        String rarityString = rs.getString("rarity");
        if (rarityString != null) {
            article.setRarity(Rarity.valueOf(rarityString));
        } else {
            article.setRarity(Rarity.COMMON);
        }


        Category cat = new Category();
        cat.setCategory_id(rs.getLong("category_id"));
        cat.setName(rs.getString("name"));

        User user = new User();
        user.setUsername(rs.getString("username"));;
        user.setUser_id(rs.getLong("user_id"));


        article.setSeller(user);
        article.setCategory(cat);

        return article;
    }
}