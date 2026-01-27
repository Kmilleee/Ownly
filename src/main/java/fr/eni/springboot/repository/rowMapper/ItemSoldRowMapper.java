package fr.eni.springboot.repository.rowMapper;

import fr.eni.springboot.bo.ItemSold;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemSoldRowMapper implements RowMapper<ItemSold> {

    @Override
    public ItemSold mapRow(ResultSet rs, int rowNum) throws SQLException {
        ItemSold item = new ItemSold();

        item.setId(rs.getLong("no_article"));
        item.setArticleName(rs.getString("nom_article"));
        item.setDescription(rs.getString("description"));
        item.setStartingPrice(rs.getLong("prix_initial"));
        item.setImage(rs.getString("image"));

        return item;
    }
}